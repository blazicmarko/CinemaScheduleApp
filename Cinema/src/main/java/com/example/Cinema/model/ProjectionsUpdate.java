package com.example.Cinema.model;

import com.example.Cinema.validator.*;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

public class ProjectionsUpdate {

    private Integer id;
    @ValidIdMovie(groups = ThirdPriorInfo.class)
    private Integer idMovie;
    @ValidIdHall(groups = ThirdPriorInfo.class)
    private Integer idHall;
    @FutureOrPresent(groups = SecondPriorInfo.class)
    private LocalDate date;
    @ValidTime(groups = ThirdPriorInfo.class)
    private LocalTime startTime;
    private LocalTime endTime;

    public ProjectionsUpdate() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Integer idMovie) {
        this.idMovie = idMovie;
    }

    public Integer getIdHall() {
        return idHall;
    }

    public void setIdHall(Integer idHall) {
        this.idHall = idHall;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}
