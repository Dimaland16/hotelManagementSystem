package org.example.hotelmanagementsystem.repository;

import org.example.hotelmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPassportNumber(String passportNumber);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    List<User> findByPassportNumberContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String passport, String firstName, String lastName
    );
}
