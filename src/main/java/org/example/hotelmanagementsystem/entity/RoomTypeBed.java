package org.example.hotelmanagementsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "room_bed")
public class RoomBed {

    @EmbeddedId
    private RoomBedId id;

    @ManyToOne
    @MapsId("roomTypeId")
    private RoomType roomType;

    @ManyToOne
    @MapsId("bedTypeId")
    private BedType bedType;

    private int quantity = 1;

    public RoomBed() {}

    public RoomBed(RoomType roomType, BedType bedType, int quantity) {
        this.roomType = roomType;
        this.bedType = bedType;
        this.id = new RoomBedId(roomType.getId(), bedType.getId());
        this.quantity = quantity;
    }

    // Геттеры, сеттеры
}