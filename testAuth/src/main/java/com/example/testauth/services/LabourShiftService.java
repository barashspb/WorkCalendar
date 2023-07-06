package com.example.testauth.services;

import com.example.testauth.domains.LabourShift;
import com.example.testauth.domains.User;
import com.example.testauth.repositories.LabourShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class LabourShiftService {

    @Autowired
    private LabourShiftRepository labourShiftRepository;

    //создание смены
    public void newLabourShift(LocalDate date, LocalTime time_of_begin, LocalTime time_of_end,
                               String comment,
                               User username) {

        LabourShift newLabour = new LabourShift();

        newLabour.setDate(date);
        newLabour.setTime_of_begin(time_of_begin);
        newLabour.setTime_of_end(time_of_end);
        newLabour.setComment(comment);

        newLabour.setUsername(username);

        System.out.println(newLabour);
        labourShiftRepository.save(newLabour);
    }
}