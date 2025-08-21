package com.kyj.fmk.member.controller;

import com.kyj.fmk.core.util.CookieUtil;
import com.kyj.fmk.sec.annotation.PublicEndpoint;
import com.kyj.fmk.sec.jwt.JWTUtil;
import com.kyj.fmk.sec.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 2025-08-10
 * @author 김용준
 * 회원정보를 관리하는 개발기용 mock 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/member/")
@RequiredArgsConstructor
@Profile("dev")
public class MemberMockController {
    /**
     * 추가정보 입력후 리다이렉트 될 페이지
     */

    private final JWTUtil jwtUtil;
    private final TokenService tokenRedisService;
    @PostMapping("mock/login")
    @PublicEndpoint
    public void mockLogin(HttpServletResponse response) throws IOException {
        String usrId = "admin";
        String usrSeqId = "99999999999";
        String email = "admintest@test.com";
        String roles = "ROLE_USER";


        //데이터베이스 수정 작업


        //새로운 jwt 토큰 발급
        String access = jwtUtil.createJwt("access", usrId, usrSeqId,
                email,roles,300000L);//엑세스 토큰
        String refresh = jwtUtil.createJwt("refresh", usrId, usrSeqId,
                email,roles,86400000L); //리프레시 토큰

        tokenRedisService.addRefresh(usrId,refresh);

        ResponseCookie responseAccessCookie= CookieUtil.createCookie("Authorization",access, 5 * 60,"/");
        ResponseCookie responseRefreshCookie= CookieUtil.createCookie("refresh",refresh,604800,"/");

        //성공시 응답
        response.addHeader(HttpHeaders.SET_COOKIE, responseAccessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, responseRefreshCookie.toString());
        response.sendRedirect("/");
    }



}
