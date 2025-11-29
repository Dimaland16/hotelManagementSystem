package org.example.hotelmanagementsystem.config;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.service.ApiKeyAuthFilter;
import org.example.hotelmanagementsystem.service.CustomAuthenticationEntryPoint;
import org.example.hotelmanagementsystem.service.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jmx.export.annotation.ManagedNotifications;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final ApiKeyAuthFilter apiKeyAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                    .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(apiKeyAuthFilter, JwtAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings( CorsRegistry registry) {
                registry.addMapping("/api/**") // Применяем ко всем путям, начинающимся с /api/
                        .allowedOrigins("http://localhost:5173") // Разрешаем запросы с этого origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешаем все нужные методы
                        .allowedHeaders("*") // Разрешаем все заголовки
                        .allowCredentials(true); // 👈 САМОЕ ГЛАВНОЕ: разрешаем учетные данные
            }
        };
    }
}