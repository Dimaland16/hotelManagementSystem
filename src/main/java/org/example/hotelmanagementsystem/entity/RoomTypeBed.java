package org.example.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room_type_beds")
@Getter
@Setter
@AllArgsConstructor
public class RoomTypeBed {

    @EmbeddedId
    private RoomTypeBedId id;

    @ManyToOne
    @MapsId("roomTypeId")
    private RoomType roomType;

    @ManyToOne
    @MapsId("bedTypeId")
    private BedType bedType;

    private int quantity = 1;

    public RoomTypeBed() {}

    public RoomTypeBed(RoomType roomType, BedType bedType, int quantity) {
        this.roomType = roomType;
        this.bedType = bedType;
        this.id = new RoomTypeBedId(roomType.getId(), bedType.getId());
        this.quantity = quantity;
    }

}