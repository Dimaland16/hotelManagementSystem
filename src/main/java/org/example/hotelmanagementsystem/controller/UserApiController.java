package org.example.hotelmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.user.UserCreateDto;
import org.example.hotelmanagementsystem.dto.user.UserResponseDto;
import org.example.hotelmanagementsystem.dto.user.UserUpdateDto;
import org.example.hotelmanagementsystem.entity.User;
import org.example.hotelmanagementsystem.repository.UserRepository;
import org.example.hotelmanagementsystem.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserRepository userRepository;
    private final UserService userService;

    // GET /api/users - Получить всех пользователей
    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    // GET /api/users/{id} - Получить одного пользователя по ID
    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    // POST /api/users - Создать нового пользователя
    @PostMapping
    // 👈 3. Возвращаем 201 Created, как принято в REST
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto dto) {
        UserResponseDto createdUser = userService.create(dto);
        // Возвращаем созданного пользователя и статус 201
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // PUT /api/users/{id} - Обновить существующего пользователя
    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id, @Valid @RequestBody UserUpdateDto dto) {
        // Сервис уже обрабатывает логику обновления по ID
        return userService.update(id, dto);
    }

    // DELETE /api/users/{id} - Удалить пользователя
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 👈 4. Возвращаем 204 No Content - стандарт для успешного удаления
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/search")
    public List<Map<String, Object>> search(@RequestParam String term) {
        List<User> users = userRepository.findByPassportNumberContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(term, term, term);

        return users.stream()
                .map(user -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", user.getId());
                    map.put("text", user.getLastName() + " " + user.getFirstName() + " (" + user.getPassportNumber() + ")");
                    return map;
                })
                .toList();
    }
}