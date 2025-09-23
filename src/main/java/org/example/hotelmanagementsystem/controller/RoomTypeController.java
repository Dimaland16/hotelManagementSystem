package org.example.hotelmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeCreateDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeResponseDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeUpdateDto;
import org.example.hotelmanagementsystem.service.RoomTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @GetMapping
    public List<RoomTypeResponseDto> getAll() {
        return roomTypeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roomTypeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<RoomTypeResponseDto> create(@RequestBody RoomTypeCreateDto dto) {
        RoomTypeResponseDto created = roomTypeService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeResponseDto> update(@PathVariable Long id, @RequestBody RoomTypeUpdateDto dto) {
        RoomTypeResponseDto updated = roomTypeService.update(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        roomTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
