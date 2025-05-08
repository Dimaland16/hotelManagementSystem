package org.example.hotelmanagementsystem.dto.roomType;

import lombok.Data;
import org.example.hotelmanagementsystem.dto.BedInRoomTypeDto;
import org.example.hotelmanagementsystem.dto.amenity.AmenityResponseDto;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class RoomTypeResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private int maxAdults;
    private int maxChildren;
    private int squareMeters;
    private Set<BedInRoomTypeDto> beds;
    private Set<AmenityResponseDto> amenities;

}