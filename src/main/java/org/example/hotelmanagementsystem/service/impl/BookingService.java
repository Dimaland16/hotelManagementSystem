package org.example.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.booking.BookingCreateDto;
import org.example.hotelmanagementsystem.dto.booking.BookingResponseDto;
import org.example.hotelmanagementsystem.dto.booking.BookingUpdateDto;
import org.example.hotelmanagementsystem.entity.Booking;
import org.example.hotelmanagementsystem.entity.Room;
import org.example.hotelmanagementsystem.entity.User;
import org.example.hotelmanagementsystem.mapper.BookingMapper;
import org.example.hotelmanagementsystem.repository.BookingRepository;
import org.example.hotelmanagementsystem.repository.RoomRepository;
import org.example.hotelmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository guestRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;

    public List<BookingResponseDto> getAll() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponseDto)
                .toList();
    }

    public BookingResponseDto getById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Бронь не найдена"));
        return bookingMapper.toResponseDto(booking);
    }

    public BookingResponseDto create(BookingCreateDto dto) {
        User guest = guestRepository.findById(dto.getGuestId())
                .orElseThrow(() -> new RuntimeException("Гость не найден"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Номер не найден"));

        // Рассчитываем цену на основе типа комнаты и количества ночей
        long days = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        BigDecimal pricePerNight = room.getRoomType().getBasePrice();
        BigDecimal total = pricePerNight.multiply(BigDecimal.valueOf(days));

        Booking booking = bookingMapper.toEntity(dto);
        booking.setGuest(guest);
        booking.setRoom(room);
        booking.setTotalPrice(total);
        booking.setStatus(dto.getStatus() != null ? dto.getStatus() : "Ожидание");

        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toResponseDto(saved);
    }

    public BookingResponseDto update(Long id, BookingUpdateDto dto) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Бронь не найдена"));

        User guest = guestRepository.findById(dto.getGuestId())
                .orElseThrow(() -> new RuntimeException("Гость не найден"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Номер не найден"));

        // Обновляем простые поля
        existing.setCheckInDate(dto.getCheckInDate());
        existing.setCheckOutDate(dto.getCheckOutDate());
        existing.setPaidAmount(dto.getPaidAmount());
        existing.setStatus(dto.getStatus());

        // Обновляем связи
        existing.setGuest(guest);
        existing.setRoom(room);

        Booking updated = bookingRepository.save(existing);
        return bookingMapper.toResponseDto(updated);
    }

    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }
}
