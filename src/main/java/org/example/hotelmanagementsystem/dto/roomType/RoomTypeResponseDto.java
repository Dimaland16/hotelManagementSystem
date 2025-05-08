package org.example.hotelmanagementsystem.dto;

public class RoomTypeResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private int maxAdults;
    private int maxChildren;
    private int squareMeters;
    private Set<BedInRoomTypeDto> beds;
    private Set<AmenityDto> amenities;

    // getters / setters
}