package org.example.hotelmanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeBedId implements Serializable {

    @Column(name = "room_type_id")
    private Long roomTypeId;

    @Column(name = "bed_type_id")
    private Long bedTypeId;
}