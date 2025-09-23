package org.example.hotelmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.bed.BedTypeCreateDto;
import org.example.hotelmanagementsystem.dto.bed.BedTypeResponseDto;
import org.example.hotelmanagementsystem.dto.bed.BedTypeUpdateDto;
import org.example.hotelmanagementsystem.service.BedTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bed-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class BedTypeController {

    private final BedTypeService bedTypeService;

    @GetMapping
    public List<BedTypeResponseDto> getAll() {
        return bedTypeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BedTypeResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bedTypeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BedTypeResponseDto> create(@RequestBody BedTypeCreateDto dto) {
        BedTypeResponseDto created = bedTypeService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BedTypeResponseDto> update(@PathVariable Long id, @RequestBody BedTypeUpdateDto dto) {
        BedTypeResponseDto updated = bedTypeService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        bedTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}