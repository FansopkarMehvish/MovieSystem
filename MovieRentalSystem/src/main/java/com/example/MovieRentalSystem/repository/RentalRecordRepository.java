package com.example.MovieRentalSystem.repository;

import com.example.MovieRentalSystem.model.RentalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRecordRepository extends JpaRepository<RentalRecord, Long> {

}
