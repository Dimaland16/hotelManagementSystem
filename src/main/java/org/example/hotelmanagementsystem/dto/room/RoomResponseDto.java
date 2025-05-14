package org.example.hotelmanagementsystem.dto.room;

import lombok.Data;

@Data
public class RoomResponseDto {
    private Long id;
    private String roomNumber;
    private int floor;
    private String status;
    private Long roomTypeId;
    private String roomTypeName;
}