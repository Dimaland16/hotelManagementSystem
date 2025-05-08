package org.example.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.BedInRoomTypeDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeCreateDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeResponseDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeUpdateDto;
import org.example.hotelmanagementsystem.entity.Amenity;
import org.example.hotelmanagementsystem.entity.BedType;
import org.example.hotelmanagementsystem.entity.RoomType;
import org.example.hotelmanagementsystem.entity.RoomTypeBed;
import org.example.hotelmanagementsystem.mapper.RoomTypeMapper;
import org.example.hotelmanagementsystem.repository.AmenityRepository;
import org.example.hotelmanagementsystem.repository.BedTypeRepository;
import org.example.hotelmanagementsystem.repository.RoomTypeRepository;
import org.example.hotelmanagementsystem.service.RoomTypeService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final BedTypeRepository bedTypeRepository;
    private final AmenityRepository amenityRepository;
    private final RoomTypeMapper roomTypeMapper;


    @Override
    public List<RoomTypeResponseDto> getAll() {
        return roomTypeMapper.toResponseDtos(roomTypeRepository.findAll());
    }

    @Override
    public RoomTypeResponseDto getById(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room type not found"));
        return roomTypeMapper.toResponseDto(roomType);
    }

    @Override
    public RoomTypeResponseDto create(RoomTypeCreateDto dto) {
        RoomType roomType = roomTypeMapper.toEntity(dto);

        // Устанавливаем кровати
        Set<RoomTypeBed> beds = new HashSet<>();
        for (Map.Entry<Long, Integer> entry : dto.getBeds().entrySet()) {
            BedType bedType = bedTypeRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Тип кровати не найден"));
            Integer quantity = entry.getValue();
            if (quantity != null && quantity > 0) {
                beds.add(new RoomTypeBed(roomType, bedType, quantity));
            }
        }
        roomType.setBeds(beds);

        // Устанавливаем удобства
        Set<Amenity> amenities = new HashSet<>(amenityRepository.findAllById(dto.getAmenities()));
        roomType.setAmenities(amenities);

        RoomType saved = roomTypeRepository.save(roomType);
        return roomTypeMapper.toResponseDto(saved);
    }

    @Override
    public RoomTypeResponseDto update(RoomTypeUpdateDto dto) {
        RoomType existing = roomTypeRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setBasePrice(dto.getBasePrice());
        existing.setMaxAdults(dto.getMaxAdults());
        existing.setMaxChildren(dto.getMaxChildren());
        existing.setSquareMeters(dto.getSquareMeters());

        // Обновляем кровати
        existing.getBeds().clear();
        for (Map.Entry<Long, Integer> entry : dto.getBeds().entrySet()) {
            BedType bedType = bedTypeRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Bed type not found"));

            Integer quantity = entry.getValue();
            if (quantity != null && quantity > 0) {
                existing.getBeds().add(new RoomTypeBed(existing, bedType, quantity));
            }
        }

        // Обновляем удобства
        Set<Amenity> amenities = new HashSet<>(amenityRepository.findAllById(dto.getAmenities()));
        existing.setAmenities(amenities);

        RoomType updated = roomTypeRepository.save(existing);
        return roomTypeMapper.toResponseDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        roomTypeRepository.deleteById(id);
    }

    @Override
    public RoomTypeUpdateDto getUpdateDtoById(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room type not found"));
        return roomTypeMapper.toUpdateDto(roomType);
    }
}