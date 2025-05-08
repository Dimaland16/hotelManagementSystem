package org.example.hotelmanagementsystem.dto.roomType;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class RoomTypeCreateDto {
    private String name;
    private String description;
    private BigDecimal basePrice;
    private int maxAdults;
    private int maxChildren;
    private int squareMeters;
    private Map<Long, Integer> beds = new HashMap<>();
    private Set<Long> amenities = new HashSet<>();

}