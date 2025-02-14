package github.tourism.web.controller.user;

import github.tourism.config.security.JwtTokenProvider;
import github.tourism.data.entity.user.RefreshToken;
import github.tourism.data.repository.user.RefreshRepository;
import github.tourism.web.advice.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@ResponseBody
@Slf4j
public class ReissueController {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshRepository refreshRepository;

    @PostMapping("/reissue")
    public ResponseEntity<Map<String, String>> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refresh = getRefreshTokenFromCookies(request);

        if (refresh == null) {
            return createErrorResponse(ErrorCode.UNAUTHORIZED_REFRESH);
        }

        if (jwtTokenProvider.isExpired(refresh)) {
            return createErrorResponse(ErrorCode.EXPIRED_REFRESH);
        }

        String category = jwtTokenProvider.getCategory(refresh);
        if (!"refresh".equals(category)) {
            return createErrorResponse(ErrorCode.UNAUTHORIZED_REFRESH2);
        }

        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            return createErrorResponse(ErrorCode.UNAUTHORIZED_REFRESH2);
        }

        String email = jwtTokenProvider.getUserEmail(refresh);
        String username = jwtTokenProvider.getUsername(refresh);
        List<String> roles = jwtTokenProvider.getRoles(refresh);
        Long expiredMs = jwtTokenProvider.getExpirationTime(refresh);

        String newAccess = jwtTokenProvider.createToken("access", email, username, roles);
        String newRefresh = jwtTokenProvider.createRefreshToken("refresh", email, username, roles);

        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(email, newRefresh, expiredMs);

        response.setHeader("Authorization", "Bearer " + newAccess);
        Cookie refreshCookie = createCookie("refresh", newRefresh);
        response.addCookie(refreshCookie);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("Authorization","Bearer " + newAccess);
        tokens.put("refresh", newRefresh);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    private ResponseEntity<Map<String, String>> createErrorResponse(ErrorCode errorCode) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorCode.getErrorMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    private String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    private void addRefreshEntity(String email, String refresh, Long expiredMs) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshEntity = new RefreshToken();
        refreshEntity.setEmail(email);
        refreshEntity.setRefresh(refresh);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String formattedExpirationDate = sdf.format(expirationDate);
        refreshEntity.setExpiration(formattedExpirationDate);

        refreshRepository.save(refreshEntity);
    }
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(3*60*60); // 쿠키의 유효 기간을 설정
        cookie.setSecure(true); // 쿠키가 HTTPS 연결을 통해서만 전송되도록 설정
        cookie.setPath("/"); // 쿠키가 유효한 경로를 설정
        cookie.setHttpOnly(true); //쿠키를 HTTP 전용으로 설정 -> JavaScript와 같은 클라이언트 측 스크립트에서 이 쿠키에 접근할 수 없게 됩니다.

        return cookie;
    }
}