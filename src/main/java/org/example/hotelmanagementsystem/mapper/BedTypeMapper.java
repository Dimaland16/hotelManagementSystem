package org.example.hotelmanagementsystem.mapper;

import org.example.hotelmanagementsystem.dto.bed.BedTypeCreateDto;
import org.example.hotelmanagementsystem.dto.bed.BedTypeResponseDto;
import org.example.hotelmanagementsystem.entity.BedType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BedTypeMapper {

    BedTypeMapper INSTANCE = Mappers.getMapper(BedTypeMapper.class);

    BedType toEntity(BedTypeCreateDto dto);

    //@Mapping(target = "roomTypes", ignore = true)
    BedTypeResponseDto toResponseDto(BedType bedType);

    List<BedTypeResponseDto> toResponseDtos(List<BedType> bedTypes);
}