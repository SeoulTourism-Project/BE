package github.tourism.config.security;


import github.tourism.web.dto.user.UserInfoDTO;
import github.tourism.web.dto.user.sign.Authority;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret-key-source}")
    private String secretKeySource;
    private Key secretKey ;
    private final long tokenValidMillisecond=1000L*30*30;
    private final long refreshValidMillisecond=1000L*30*360;
    private final UserDetailsService userDetailsService;

    @PostConstruct
    public void setUp() {
        byte[] decodedKey = Base64.getUrlDecoder().decode(secretKeySource);
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }

    public String createToken(String category,Integer userId,String email, String username, List<String> roles) {
        Claims claims = Jwts.claims()
                .setSubject(email);
        claims.put("category",category);
        claims.put("userId",userId);
        claims.put("username", username);
        claims.put("roles", roles);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String category,String email,List<String> roles ){
        Claims claims = Jwts.claims()
                .setSubject(email);
        claims.put("category",category);
        claims.put("roles", roles);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshValidMillisecond))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    public boolean validateToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            log.info("JWT 토큰이 null이거나 비어 있습니다.");
            return false;
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            Date now = new Date();
            return claims.getExpiration().after(now);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다: {}", e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다: {}", e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다: {}", e.getMessage());
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        String email = getUserEmail(jwtToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmail(final String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    public List<String> getRoles(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);
    }
    public String getCategory(String token) {

        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("category", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public Long getExpirationTime(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Date expiration = claims.getExpiration();
        return expiration.getTime() - System.currentTimeMillis();
    }
    public UserInfoDTO getClaim(String token) {
        if (token == null) {
            throw new IllegalArgumentException("JWT 토큰이 null이거나 비어 있습니다.");
        }
        Claims claimsBody;
        try {
            claimsBody = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JWT 클레임을 파싱하는 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("JWT 클레임을 파싱하는 중 오류 발생", e);
        }

        return UserInfoDTO.builder()
                .email(claimsBody.getSubject())
                .category(claimsBody.getOrDefault("category", "").toString())
                .roles(String.join(",", (List<String>) claimsBody.getOrDefault("roles", new ArrayList<>())))
                .build();
    }

}
