package org.example.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.bed.BedTypeCreateDto;
import org.example.hotelmanagementsystem.dto.bed.BedTypeResponseDto;
import org.example.hotelmanagementsystem.dto.bed.BedTypeUpdateDto;
import org.example.hotelmanagementsystem.entity.BedType;
import org.example.hotelmanagementsystem.mapper.BedTypeMapper;
import org.example.hotelmanagementsystem.repository.BedTypeRepository;
import org.example.hotelmanagementsystem.service.BedTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BedTypeServiceImpl implements BedTypeService {

    private final BedTypeRepository bedTypeRepository;
    private final BedTypeMapper bedTypeMapper;

    @Override
    public List<BedTypeResponseDto> getAll() {
        return bedTypeMapper.toResponseDtos(bedTypeRepository.findAll());
    }

    @Override
    public BedTypeResponseDto getById(Long id) {
        BedType bedType = bedTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed type not found"));
        return bedTypeMapper.toResponseDto(bedType);
    }

    @Override
    public BedTypeResponseDto create(BedTypeCreateDto dto) {
        BedType bedType = bedTypeMapper.toEntity(dto);
        BedType saved = bedTypeRepository.save(bedType);
        return bedTypeMapper.toResponseDto(saved);
    }

    @Override
    public BedTypeResponseDto update(Long id, BedTypeUpdateDto dto) {
        BedType existing = bedTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed type not found"));
        existing.setName(dto.getName());
        BedType saved = bedTypeRepository.save(existing);
        return bedTypeMapper.toResponseDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        bedTypeRepository.deleteById(id);
    }
}