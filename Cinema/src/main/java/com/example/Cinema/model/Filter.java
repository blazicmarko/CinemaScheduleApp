package com.example.Cinema.model;

import java.sql.Date;

public class Filter {
    private String movieName;
    private Date date;

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
