package org.example.hotelmanagementsystem.dto.user;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserCreateDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private String email;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    private String passportNumber;

}