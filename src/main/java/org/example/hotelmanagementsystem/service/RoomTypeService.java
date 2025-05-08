package org.example.hotelmanagementsystem.service;

import org.example.hotelmanagementsystem.dto.roomType.RoomTypeCreateDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeResponseDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeUpdateDto;

import java.util.List;

public interface RoomTypeService {
    List<RoomTypeResponseDto> getAll();
    RoomTypeResponseDto getById(Long id);
    RoomTypeResponseDto create(RoomTypeCreateDto dto);
    RoomTypeResponseDto update(RoomTypeUpdateDto dto);
    void deleteById(Long id);
    RoomTypeUpdateDto getUpdateDtoById(Long id);
}