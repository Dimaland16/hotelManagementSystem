package org.example.hotelmanagementsystem.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import jakarta.servlet.http.Cookie;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider; // У этого сервиса тоже должен быть свой JwtTokenProvider

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                }
            }
        }

        if (accessToken != null && jwtTokenProvider.validateAccessToken(accessToken)) {
            Long userId = jwtTokenProvider.getUserIdFromAccessToken(accessToken);
            // Создаем объект аутентификации для Spring Security
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userId, // Principal - теперь это просто ID пользователя
                    null,
                    Collections.emptyList() // Роли, если они есть
            );
            // Сохраняем аутентификацию в контексте безопасности
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Пропускаем запрос дальше по цепочке
        filterChain.doFilter(request, response);
    }
}