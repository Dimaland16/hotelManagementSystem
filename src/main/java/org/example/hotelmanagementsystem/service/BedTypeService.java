package org.example.hotelmanagementsystem.service;

import org.example.hotelmanagementsystem.dto.bed.BedTypeCreateDto;
import org.example.hotelmanagementsystem.dto.bed.BedTypeResponseDto;
import org.example.hotelmanagementsystem.dto.bed.BedTypeUpdateDto;

import java.util.List;

public interface BedTypeService {
    List<BedTypeResponseDto> getAll();
    BedTypeResponseDto getById(Long id);
    BedTypeResponseDto create(BedTypeCreateDto dto);
    BedTypeResponseDto update(Long id, BedTypeUpdateDto dto);
    void deleteById(Long id);
}