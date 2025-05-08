package org.example.hotelmanagementsystem.dto.roomType;

import lombok.Data;
import org.example.hotelmanagementsystem.dto.BedInRoomTypeDto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class RoomTypeUpdateDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private int maxAdults;
    private int maxChildren;
    private int squareMeters;
    private Map<Long, Integer> beds = new HashMap<>();
    private Set<Long> amenities = new HashSet<>();

}