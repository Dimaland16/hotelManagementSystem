package org.example.hotelmanagementsystem.repository;

import org.example.hotelmanagementsystem.entity.BedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedTypeRepository extends JpaRepository<BedType, Long> {
}
