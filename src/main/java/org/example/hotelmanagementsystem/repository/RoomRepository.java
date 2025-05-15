package org.example.hotelmanagementsystem.repository;

import org.example.hotelmanagementsystem.entity.Room;
import org.example.hotelmanagementsystem.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByRoomType_Id(Long type);
}