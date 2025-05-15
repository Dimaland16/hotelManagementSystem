package org.example.hotelmanagementsystem.dto;

import lombok.Data;

@Data
public class RoomTypeAvailabilityDto {
    private Long id;
    private String name;
    private int availableRoomsCount;

}