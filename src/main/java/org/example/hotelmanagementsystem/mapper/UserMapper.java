package org.example.hotelmanagementsystem.mapper;

import org.example.hotelmanagementsystem.dto.user.UserCreateDto;
import org.example.hotelmanagementsystem.dto.user.UserResponseDto;
import org.example.hotelmanagementsystem.dto.user.UserUpdateDto;
import org.example.hotelmanagementsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //@Mapping(target = "bookings", ignore = true)
    User toEntity(UserCreateDto dto);

    //@Mapping(target = "bookings", ignore = true)
    User toEntity(UserUpdateDto dto);

    UserResponseDto toResponseDto(User user);
}