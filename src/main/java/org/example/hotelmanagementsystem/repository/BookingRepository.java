package org.example.hotelmanagementsystem.repository;

import org.example.hotelmanagementsystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomIdAndCheckOutDateAfterAndCheckInDateBefore(Long id, LocalDate checkIn, LocalDate checkOut);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND " +
            "(b.checkInDate < :checkOut AND b.checkOutDate > :checkIn) AND b.id != :bookingId")
    List<Booking> findOverlappingBookingsExceptCurrent(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("bookingId") Long bookingId
    );}
