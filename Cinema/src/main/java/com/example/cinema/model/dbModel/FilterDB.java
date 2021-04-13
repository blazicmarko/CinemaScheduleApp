package com.example.cinema.model.dbModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class FilterDB {
    @NotNull
    private String movieName;
    private LocalDate date;

    public FilterDB(@NotNull String movieName, LocalDate date) {
        this.movieName = movieName;
        this.date = date;
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
