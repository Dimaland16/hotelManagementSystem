package org.example.hotelmanagementsystem.dto.room;

import lombok.Data;

@Data
public class RoomCreateDto {
    private String roomNumber;
    private int floor;
    private String status;
    private Long roomTypeId;
}