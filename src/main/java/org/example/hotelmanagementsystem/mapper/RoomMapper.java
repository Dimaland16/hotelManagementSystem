package org.example.hotelmanagementsystem.mapper;

import org.example.hotelmanagementsystem.dto.room.RoomCreateDto;
import org.example.hotelmanagementsystem.dto.room.RoomResponseDto;
import org.example.hotelmanagementsystem.dto.room.RoomUpdateDto;
import org.example.hotelmanagementsystem.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { RoomTypeMapper.class })
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(source = "roomTypeId", target = "roomType.id")
    Room toEntity(RoomCreateDto dto);

    @Mapping(source = "roomTypeId", target = "roomType.id")
    Room toEntity(RoomUpdateDto dto);

    @Mapping(source = "roomType.id", target = "roomTypeId")
    @Mapping(source = "roomType.name", target = "roomTypeName")
    RoomResponseDto toResponseDto(Room room);
}