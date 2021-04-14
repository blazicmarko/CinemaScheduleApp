package com.example.cinema.model.responseModel;

import java.sql.Date;
import java.sql.Time;

public class ProjectionViewResposne {
    private String movieName;
    private Date date;
    private Time time;
    private String hallName;

    public ProjectionViewResposne() {
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
