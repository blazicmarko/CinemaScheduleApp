package com.example.cinema.model.requestModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class FilterReq {
    @NotNull
    private String movieName;
    private LocalDate date;

    public FilterReq() {
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
}
