package com.example.Cinema.model;

import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.sql.Date;

public class Projections {
    private Integer id;
    private Integer idMovie;
    private Integer idHall;
    private Date date;
    private Time startTime;

    private Time endTime;

    public Projections() {
    }

    public Projections(Integer idMovie, Integer idHall, Date date, Time time, Time endTime) {
        this.idMovie = idMovie;
        this.idHall = idHall;
        this.date = date;
        this.startTime = time;
        this.endTime = endTime;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }


}
