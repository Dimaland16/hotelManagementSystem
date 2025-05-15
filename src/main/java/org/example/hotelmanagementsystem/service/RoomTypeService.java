package org.example.hotelmanagementsystem.service;

import org.example.hotelmanagementsystem.dto.RoomTypeAvailabilityDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeCreateDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeResponseDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeUpdateDto;

import java.time.LocalDate;
import java.util.List;

public interface RoomTypeService {
    List<RoomTypeResponseDto> getAll();
    RoomTypeResponseDto getById(Long id);
    RoomTypeResponseDto create(RoomTypeCreateDto dto);
    RoomTypeResponseDto update(RoomTypeUpdateDto dto);
    void deleteById(Long id);
    RoomTypeUpdateDto getUpdateDtoById(Long id);

    List<RoomTypeAvailabilityDto> findAvailableTypes(LocalDate checkIn, LocalDate checkOut);
}