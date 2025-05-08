package org.example.hotelmanagementsystem.dto;

public class RoomTypeUpdateDto {
    private String name;
    private String description;
    private BigDecimal basePrice;
    private int maxAdults;
    private int maxChildren;
    private int squareMeters;
    private Set<BedInRoomTypeDto> beds;
    private Set<Long> amenities;

    // getters / setters
}