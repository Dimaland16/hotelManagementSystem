package org.example.hotelmanagementsystem.mapper;

import org.example.hotelmanagementsystem.dto.booking.BookingCreateDto;
import org.example.hotelmanagementsystem.dto.booking.BookingResponseDto;
import org.example.hotelmanagementsystem.dto.booking.BookingUpdateDto;
import org.example.hotelmanagementsystem.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class, RoomMapper.class})
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "guestId", target = "guest.id")
    @Mapping(source = "roomId", target = "room.id")
    @Mapping(target = "totalPrice", ignore = true) // будет рассчитываться в сервисе
    @Mapping(target = "bookingDate", expression = "java(java.time.LocalDate.now())") // автоматически ставим дату брони при создании
    Booking toEntity(BookingCreateDto dto);

    @Mapping(source = "guest.id", target = "guestId")
    @Mapping(source = "room.id", target = "roomId")
    BookingUpdateDto toUpdateDto(Booking booking);

    @Mapping(source = "guest.id", target = "guestId")
    @Mapping(source = "guest.firstName", target = "guestName")
    @Mapping(source = "guest.lastName", target = "guestLastName")
    @Mapping(source = "guest.middleName", target = "guestMiddleName")
    @Mapping(source = "guest.passportNumber", target = "guestPassportNumber")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.roomNumber", target = "roomNumber")
    @Mapping(source = "room.roomType.id", target = "roomTypeId")
    @Mapping(source = "room.roomType.name", target = "roomTypeName")
    BookingResponseDto toResponseDto(Booking booking);
}