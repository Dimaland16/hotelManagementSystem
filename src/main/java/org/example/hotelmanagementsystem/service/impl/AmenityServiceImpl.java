package org.example.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.amenity.AmenityCreateDto;
import org.example.hotelmanagementsystem.dto.amenity.AmenityResponseDto;
import org.example.hotelmanagementsystem.dto.amenity.AmenityUpdateDto;
import org.example.hotelmanagementsystem.entity.Amenity;
import org.example.hotelmanagementsystem.mapper.AmenityMapper;
import org.example.hotelmanagementsystem.repository.AmenityRepository;
import org.example.hotelmanagementsystem.service.AmenityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepository;
    private final AmenityMapper amenityMapper;

    @Override
    public List<AmenityResponseDto> getAll() {
        return amenityMapper.toResponseDtos(amenityRepository.findAll());
    }

    @Override
    public AmenityResponseDto getById(Long id) {
        Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Amenity not found"));
        return amenityMapper.toResponseDto(amenity);
    }

    @Override
    public AmenityResponseDto create(AmenityCreateDto dto) {
        Amenity amenity = amenityMapper.toEntity(dto);
        Amenity saved = amenityRepository.save(amenity);
        return amenityMapper.toResponseDto(saved);
    }

    @Override
    public AmenityResponseDto update(Long id, AmenityUpdateDto dto) {
        Amenity existing = amenityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Amenity not found"));
        existing.setName(dto.getName());
        Amenity saved = amenityRepository.save(existing);
        return amenityMapper.toResponseDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        amenityRepository.deleteById(id);
    }
}