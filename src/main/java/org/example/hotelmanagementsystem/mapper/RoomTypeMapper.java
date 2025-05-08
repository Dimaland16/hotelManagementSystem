package org.example.hotelmanagementsystem.mapper;

import org.example.hotelmanagementsystem.dto.BedInRoomTypeDto;
import org.example.hotelmanagementsystem.dto.amenity.AmenityResponseDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeCreateDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeResponseDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeUpdateDto;
import org.example.hotelmanagementsystem.entity.Amenity;
import org.example.hotelmanagementsystem.entity.RoomType;
import org.example.hotelmanagementsystem.entity.RoomTypeBed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { BedTypeMapper.class, AmenityMapper.class })
public interface RoomTypeMapper {

    RoomTypeMapper INSTANCE = Mappers.getMapper(RoomTypeMapper.class);

    @Mapping(target = "beds", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    RoomType toEntity(RoomTypeCreateDto dto);

    @Mapping(target = "beds", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    RoomType toEntity(RoomTypeUpdateDto dto);

    @Mapping(target = "beds", source = "beds")
    RoomTypeResponseDto toResponseDto(RoomType roomType);

    default Set<BedInRoomTypeDto> mapBeds(Set<RoomTypeBed> beds) {
        if (beds == null) return Collections.emptySet();

        return beds.stream()
                .map(bed -> {
                    BedInRoomTypeDto dto = new BedInRoomTypeDto();
                    dto.setBedTypeId(bed.getBedType().getId());
                    dto.setBedTypeName(bed.getBedType().getName());
                    dto.setQuantity(bed.getQuantity());
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    List<RoomTypeResponseDto> toResponseDtos(List<RoomType> roomTypes);


    @Mapping(target = "beds", source = "beds", qualifiedByName = "mapBedsToMap")
    @Mapping(target = "amenities", source = "amenities", qualifiedByName = "mapAmenitiesToIds")
    RoomTypeUpdateDto toUpdateDto(RoomType roomType);

    // Метод для кастомного маппинга
    @Named("mapBedsToMap")
    default Map<Long, Integer> mapBedsToMap(Set<RoomTypeBed> roomTypeBeds) {
        if (roomTypeBeds == null) return new HashMap<>();

        return roomTypeBeds.stream()
                .collect(Collectors.toMap(
                        bed -> bed.getBedType().getId(),
                        RoomTypeBed::getQuantity
                ));
    }

    // То же самое для удобств
    @Named("mapAmenitiesToIds")
    default Set<Long> mapAmenitiesToIds(Set<Amenity> amenities) {
        if (amenities == null) return new HashSet<>();
        return amenities.stream()
                .map(Amenity::getId)
                .collect(Collectors.toSet());
    }

}