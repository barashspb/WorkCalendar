package com.example.testauth.repositories;

import com.example.testauth.domains.LabourShift;
import com.example.testauth.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LabourShiftRepository extends JpaRepository<LabourShift, Long> {
    LabourShift findById (long Id);

    List<LabourShift> findAllByUsername (String username);

    List<LabourShift> findAllByDate (LocalDate date);
}