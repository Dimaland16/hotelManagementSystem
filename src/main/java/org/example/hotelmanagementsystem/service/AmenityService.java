package org.example.hotelmanagementsystem.service;

import org.example.hotelmanagementsystem.dto.amenity.AmenityCreateDto;
import org.example.hotelmanagementsystem.dto.amenity.AmenityResponseDto;
import org.example.hotelmanagementsystem.dto.amenity.AmenityUpdateDto;

import java.util.List;

public interface AmenityService {
    List<AmenityResponseDto> getAll();
    AmenityResponseDto getById(Long id);
    AmenityResponseDto create(AmenityCreateDto dto);
    AmenityResponseDto update(Long id, AmenityUpdateDto dto);
    void deleteById(Long id);
}