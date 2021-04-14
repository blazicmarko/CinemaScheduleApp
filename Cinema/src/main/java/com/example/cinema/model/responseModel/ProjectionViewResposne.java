package com.example.cinema.model.responseModel;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProjectionViewResposne {
    private String movieName;
    private LocalDate date;
    private LocalTime time;
    private String hallName;

    public ProjectionViewResposne() {
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
