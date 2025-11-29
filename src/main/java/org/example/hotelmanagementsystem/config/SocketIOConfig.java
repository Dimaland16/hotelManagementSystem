package org.example.hotelmanagementsystem.config;

import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.example.hotelmanagementsystem.service.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SocketIOConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(19092);

        // Настройки CORS
        config.setOrigin("http://localhost:5173");
        config.setAllowCustomRequests(true);

        // ЛОГИКА АВТОРИЗАЦИИ С ЛОГАМИ
        config.setAuthorizationListener(handshakeData -> {
            System.out.println("--- [SocketIO] Попытка подключения ---");

            // 1. Проверяем наличие заголовка Cookie
            String cookieHeader = handshakeData.getHttpHeaders().get("Cookie");
            if (cookieHeader == null) {
                System.out.println("❌ [SocketIO] Отказ: Заголовок Cookie отсутствует");
                return AuthorizationResult.FAILED_AUTHORIZATION; // 👈 2. ИЗМЕНЕНИЕ
            }
            System.out.println("✅ [SocketIO] Cookie найдены");

            // 2. Ищем access_token
            String accessToken = null;
            String[] cookies = cookieHeader.split(";");
            for (String cookie : cookies) {
                if (cookie.trim().startsWith("access_token=")) {
                    accessToken = cookie.trim().substring("access_token=".length());
                    break; // Нашли - выходим из цикла
                }
            }

            if (accessToken == null) {
                System.out.println("❌ [SocketIO] Отказ: Cookie 'access_token' не найден");
                return AuthorizationResult.FAILED_AUTHORIZATION; // 👈 2. ИЗМЕНЕНИЕ
            }
            System.out.println("✅ [SocketIO] Токен найден (начинается с " + accessToken.substring(0, Math.min(10, accessToken.length())) + "...)");

            // 3. Валидируем токен
            try {
                 if (!jwtTokenProvider.validateAccessToken(accessToken)) {
                     System.out.println("❌ [SocketIO] Отказ: Токен невалиден (метод вернул false)");
                     return AuthorizationResult.FAILED_AUTHORIZATION; // 👈 2. ИЗМЕНЕНИЕ
                 }

                System.out.println("✅ [SocketIO] Токен валиден. ДОСТУП РАЗРЕШЕН.");
                return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
            } catch (Exception e) {
                System.out.println("❌ [SocketIO] Отказ: Ошибка валидации токена: " + e.getMessage());
                return AuthorizationResult.FAILED_AUTHORIZATION; // 👈 2. ИЗМЕНЕНИЕ
            }
        });

        return new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }
}