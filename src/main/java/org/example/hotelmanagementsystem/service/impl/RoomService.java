package org.example.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.room.RoomCreateDto;
import org.example.hotelmanagementsystem.dto.room.RoomResponseDto;
import org.example.hotelmanagementsystem.dto.room.RoomUpdateDto;
import org.example.hotelmanagementsystem.entity.Booking;
import org.example.hotelmanagementsystem.entity.Room;
import org.example.hotelmanagementsystem.entity.RoomType;
import org.example.hotelmanagementsystem.mapper.RoomMapper;
import org.example.hotelmanagementsystem.repository.BookingRepository;
import org.example.hotelmanagementsystem.repository.RoomRepository;
import org.example.hotelmanagementsystem.repository.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomMapper roomMapper;
    private final BookingRepository bookingRepository;

    public List<RoomResponseDto> getAll() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toResponseDto)
                .toList();
    }

    public RoomResponseDto getById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Номер не найден"));
        return roomMapper.toResponseDto(room);
    }

    public RoomResponseDto create(RoomCreateDto dto) {
        RoomType roomType = roomTypeRepository.findById(dto.getRoomTypeId())
                .orElseThrow(() -> new RuntimeException("Тип комнаты не найден"));

        Room room = roomMapper.toEntity(dto);
        room.setRoomType(roomType);

        Room saved = roomRepository.save(room);
        return roomMapper.toResponseDto(saved);
    }

    public RoomResponseDto update(Long id, RoomUpdateDto dto) {
        Room existing = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Номер не найден"));

        RoomType roomType = roomTypeRepository.findById(dto.getRoomTypeId())
                .orElseThrow(() -> new RuntimeException("Тип комнаты не найден"));

        existing.setRoomNumber(dto.getRoomNumber());
        existing.setFloor(dto.getFloor());
        existing.setStatus(dto.getStatus());
        existing.setRoomType(roomType);

        Room updated = roomRepository.save(existing);
        return roomMapper.toResponseDto(updated);
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    public List<Room> findAvailableRooms(Long roomTypeId, LocalDate checkIn, LocalDate checkOut, Long bookingId) {
        List<Room> rooms = roomRepository.findByRoomType_Id(roomTypeId);

        return rooms.stream()
                .filter(room -> isRoomAvailable(room, checkIn, checkOut, bookingId))
                .toList();
    }

    private boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut, Long bookingId) {
        List<Booking> overlapping;

        if (bookingId != null) {
            overlapping = bookingRepository.findOverlappingBookingsExceptCurrent(room.getId(), checkIn, checkOut, bookingId);
        }
        else {
            overlapping = bookingRepository.findByRoomIdAndCheckOutDateAfterAndCheckInDateBefore(room.getId(), checkIn, checkOut);
        }
        return overlapping.isEmpty();
    }
}