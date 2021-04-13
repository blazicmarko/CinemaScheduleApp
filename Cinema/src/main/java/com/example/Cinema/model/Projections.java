package com.example.Cinema.model;

import com.example.Cinema.validator.ValidIdHall;
import com.example.Cinema.validator.ValidIdMovie;
import com.example.Cinema.validator.ValidTime;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Valid
public class Projections {

    private Integer id;
    @NotNull
    @ValidIdMovie
    private Integer idMovie;
    @NotNull
    @ValidIdHall
    private Integer idHall;
    @NotNull
    @FutureOrPresent
    private LocalDate date;
    @NotNull
    @ValidTime
    private LocalTime startTime;
    private LocalTime endTime;


    public Projections(Integer id, @NotNull Integer idMovie, @NotNull Integer idHall, @NotNull @FutureOrPresent LocalDate date,
                       @NotNull LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.idMovie = idMovie;
        this.idHall = idHall;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Projections(Integer id, @NotNull Integer idMovie, @NotNull Integer idHall) {
        this.id = id;
        this.idMovie = idMovie;
        this.idHall = idHall;
    }


    public Projections() {
    }

    public Projections(Integer id) {
        this.id = id;
    }

    public Projections(@Valid Projections projections){
        this.id = projections.getId();
        this.date = projections.getDate();
        this.startTime = projections.getStartTime();
        this.idHall = projections.getIdHall();
        this.idMovie = projections.getIdMovie();
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
