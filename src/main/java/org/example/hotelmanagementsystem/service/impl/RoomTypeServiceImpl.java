package org.example.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.BedInRoomTypeDto;
import org.example.hotelmanagementsystem.dto.RoomTypeAvailabilityDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeCreateDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeResponseDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeUpdateDto;
import org.example.hotelmanagementsystem.entity.*;
import org.example.hotelmanagementsystem.mapper.RoomTypeMapper;
import org.example.hotelmanagementsystem.repository.*;
import org.example.hotelmanagementsystem.service.RoomTypeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
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

    @Override
    public List<RoomTypeAvailabilityDto> findAvailableTypes(LocalDate checkIn, LocalDate checkOut) {
        List<RoomType> allTypes = roomTypeRepository.findAll();

        return allTypes.stream()
                .map(type -> {
                    long availableCount = countAvailableRooms(type.getId(), checkIn, checkOut);

                    if (availableCount > 0) {
                        RoomTypeAvailabilityDto dto = new RoomTypeAvailabilityDto();
                        dto.setId(type.getId());
                        dto.setName(type.getName());
                        dto.setAvailableRoomsCount((int) availableCount);
                        return dto;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private long countAvailableRooms(Long roomTypeId, LocalDate checkIn, LocalDate checkOut) {
        List<Room> rooms = roomRepository.findByRoomType_Id(roomTypeId);

        return rooms.stream()
                .filter(room -> isRoomAvailable(room, checkIn, checkOut))
                .count();
    }

    private boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> bookings = bookingRepository.findByRoomIdAndCheckOutDateAfterAndCheckInDateBefore(room.getId(), checkIn, checkOut);
        return bookings.isEmpty();
    }
}