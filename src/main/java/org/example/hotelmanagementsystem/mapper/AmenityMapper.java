package org.example.hotelmanagementsystem.mapper;

import org.example.hotelmanagementsystem.dto.amenity.AmenityCreateDto;
import org.example.hotelmanagementsystem.dto.amenity.AmenityResponseDto;
import org.example.hotelmanagementsystem.entity.Amenity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AmenityMapper {

    AmenityMapper INSTANCE = Mappers.getMapper(AmenityMapper.class);

    Amenity toEntity(AmenityCreateDto dto);

    //@Mapping(target = "roomTypes", ignore = true)
    AmenityResponseDto toResponseDto(Amenity amenity);

    List<AmenityResponseDto> toResponseDtos(List<Amenity> amenities);
}