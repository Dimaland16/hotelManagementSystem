package org.example.hotelmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.amenity.AmenityCreateDto;
import org.example.hotelmanagementsystem.dto.amenity.AmenityResponseDto;
import org.example.hotelmanagementsystem.dto.amenity.AmenityUpdateDto;
import org.example.hotelmanagementsystem.service.AmenityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenities")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AmenityController {

    private final AmenityService amenityService;

    @GetMapping
    public List<AmenityResponseDto> getAll() {
        return amenityService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(amenityService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AmenityResponseDto> create(@RequestBody AmenityCreateDto dto) {
        AmenityResponseDto created = amenityService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AmenityResponseDto> update(@PathVariable Long id, @RequestBody AmenityUpdateDto dto) {
        AmenityResponseDto updated = amenityService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        amenityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
