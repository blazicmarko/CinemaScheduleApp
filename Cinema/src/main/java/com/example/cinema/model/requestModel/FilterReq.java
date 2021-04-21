package com.example.cinema.model.requestModel;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class FilterReq {
    @NotNull
    @Schema(description = "Name of the Movie.",
            example = "Matrix")
    private String movieName;
    @Schema(description = "Date of projection",
            example = "2022-04-16", implementation = String.class)
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
