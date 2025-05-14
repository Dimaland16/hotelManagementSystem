package org.example.hotelmanagementsystem.dto.booking;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookingResponseDto {
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate bookingDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkInDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOutDate;
    private BigDecimal totalPrice;
    private BigDecimal paidAmount;
    private String status;

    private Long guestId;
    private String guestName;
    private String guestLastName;
    private String guestMiddleName;
    private String guestPassportNumber;

    private Long roomId;
    private String roomNumber;
    private String roomTypeName;

}
