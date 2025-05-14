package org.example.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.user.UserCreateDto;
import org.example.hotelmanagementsystem.dto.user.UserResponseDto;
import org.example.hotelmanagementsystem.dto.user.UserUpdateDto;
import org.example.hotelmanagementsystem.entity.User;
import org.example.hotelmanagementsystem.mapper.UserMapper;
import org.example.hotelmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    public UserResponseDto getById(Long id) {
        return userMapper.toResponseDto(getUser(id));
    }

    public UserResponseDto create(UserCreateDto dto) {
        if (userRepository.existsByPassportNumber(dto.getPassportNumber())) {
            throw new RuntimeException("Пользователь с таким номером паспорта уже существует");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email уже используется");
        }
        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new RuntimeException("Номер телефона уже используется");
        }

        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }

    public UserResponseDto update(Long id, UserUpdateDto dto) {
        User existing = getUser(id);
        User updatedFields = userMapper.toEntity(dto);

        existing.setFirstName(updatedFields.getFirstName());
        existing.setLastName(updatedFields.getLastName());
        existing.setMiddleName(updatedFields.getMiddleName());
        existing.setPhoneNumber(updatedFields.getPhoneNumber());
        existing.setEmail(updatedFields.getEmail());
        existing.setDateOfBirth(updatedFields.getDateOfBirth());
        existing.setPassportNumber(updatedFields.getPassportNumber());

        User saved = userRepository.save(existing);
        return userMapper.toResponseDto(saved);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }
}