package github.tourism.web.controller.user;

import github.tourism.config.security.JwtTokenProvider;
import github.tourism.data.entity.user.RefreshToken;
import github.tourism.data.repository.user.RefreshRepository;
import github.tourism.service.user.RefreshTokenService;
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
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/reissue")
    public ResponseEntity<Map<String, String>> reissue(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> tokens = refreshTokenService.reissueTokens(request, response);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        } catch (Exception e) {
            return createErrorResponse(e);
        }
    }

    private ResponseEntity<Map<String, String>> createErrorResponse(Exception e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}

