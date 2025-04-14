package com.mypaylist.backend.global.jwt;

import com.mypaylist.backend.domain.user.User;
import com.mypaylist.backend.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
                System.out.println("JwtAuthFilter 작동: " + request.getRequestURI());
        String token = jwtUtil.getTokenFromRequest(request);

        System.out.println("추출된 토큰: " + token);

        if (token != null && jwtUtil.validateToken(token)) {
            System.out.println("토큰 유효함");
            String email = jwtUtil.getUserEmailFromToken(token);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

                    UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("인증 완료");
        } else {
            System.out.println("토큰이 없거나 유효하지 않음");
        }
        
        filterChain.doFilter(request, response);
    }
}
