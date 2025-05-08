package org.example.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bed_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BedType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "bedType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomTypeBed> roomTypes = new HashSet<>();

}