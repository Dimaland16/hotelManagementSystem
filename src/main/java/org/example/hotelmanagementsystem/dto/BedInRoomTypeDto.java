package org.example.hotelmanagementsystem.dto;

import lombok.Data;

@Data
public class BedInRoomTypeDto {
    private Long bedTypeId;
    private String bedTypeName;
    private int quantity;

}