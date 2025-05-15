package org.example.hotelmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.RoomTypeAvailabilityDto;
import org.example.hotelmanagementsystem.entity.Room;
import org.example.hotelmanagementsystem.repository.RoomRepository;
import org.example.hotelmanagementsystem.repository.RoomTypeRepository;
import org.example.hotelmanagementsystem.service.RoomTypeService;
import org.example.hotelmanagementsystem.service.impl.RoomService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomApiController {

    private final RoomService roomService;
    private final RoomTypeService roomTypeService;

    @GetMapping("/by-type")
    public List<Map<String, Object>> getAvailableRoomsByTypeAndDates(
            @RequestParam("typeId") Long roomTypeId,
            @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam(name = "bookingId", required = false) Long bookingId) {

        List<Room> availableRooms = roomService.findAvailableRooms(roomTypeId, checkIn, checkOut, bookingId);

        return availableRooms.stream()
                .map(room -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", room.getId());
                    map.put("roomNumber", room.getRoomNumber());
                    return map;
                })
                .toList();
    }

    @GetMapping("/types/by-dates")
    public List<RoomTypeAvailabilityDto> getAvailableTypes(
            @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        return roomTypeService.findAvailableTypes(checkIn, checkOut);
    }
}