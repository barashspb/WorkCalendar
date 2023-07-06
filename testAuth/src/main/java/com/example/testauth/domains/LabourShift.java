package com.example.testauth.domains;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "labour_shift")
public class LabourShift {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time_of_begin;

    @Column(nullable = false)
    private LocalTime time_of_end;

    @Column
    private String comment;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User username;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public LocalTime getTime_of_begin() {
        return time_of_begin;
    }

    public void setTime_of_begin(LocalTime time_of_begin) {
        this.time_of_begin = time_of_begin;
    }

    public LocalTime getTime_of_end() {
        return time_of_end;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setTime_of_end(LocalTime time_of_end) {
        this.time_of_end = time_of_end;
    }

    public LabourShift() {}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}