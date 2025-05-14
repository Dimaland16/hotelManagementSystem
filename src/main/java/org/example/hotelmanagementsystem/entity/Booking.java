package org.example.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_date", nullable = false, updatable = false)
    private LocalDate bookingDate; // Дата бронирования

    @Column(name = "checkin_date", nullable = false)
    private LocalDate checkInDate; // Дата заезда

    @Column(name = "checkout_date", nullable = false)
    private LocalDate checkOutDate; // Дата выезда

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice; // Общая стоимость брони

    @Column(name = "paid_amount", nullable = false)
    private BigDecimal paidAmount; // Оплачено

    @Column(name = "status", nullable = false)
    private String status; // например: Подтверждена, Ожидание, Отмена, Завершена

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private User guest;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

}