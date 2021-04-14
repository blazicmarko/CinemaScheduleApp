package com.example.cinema.model.requestModel;

import com.example.cinema.validator.ValidIdHall;
import com.example.cinema.validator.ValidIdMovie;
import com.example.cinema.validator.ValidTime;
import com.example.cinema.validator.groups.SecondPriorGroup;
import com.example.cinema.validator.groups.ThirdPriorGroup;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

public class ProjectionUpdateReq {

    private Integer id;
    @ValidIdMovie(groups = SecondPriorGroup.class)
    private Integer idMovie;
    @ValidIdHall(groups = SecondPriorGroup.class)
    private Integer idHall;
    @FutureOrPresent(groups = SecondPriorGroup.class)
    private LocalDate date;
    @ValidTime(groups = ThirdPriorGroup.class)
    private LocalTime startTime;
    private LocalTime endTime;

    public ProjectionUpdateReq() {
    }

    public ProjectionUpdateReq(Integer id, Integer idMovie, Integer idHall, @FutureOrPresent(groups = SecondPriorGroup.class) LocalDate date, LocalTime startTime) {
        this.id = id;
        this.idMovie = idMovie;
        this.idHall = idHall;
        this.date = date;
        this.startTime = startTime;
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
