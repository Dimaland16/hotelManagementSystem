package org.example.hotelmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.entity.User;
import org.example.hotelmanagementsystem.repository.UserRepository;
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