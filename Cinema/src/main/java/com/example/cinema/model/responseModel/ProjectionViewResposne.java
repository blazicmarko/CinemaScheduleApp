package com.example.cinema.model.responseModel;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProjectionViewResposne {
    @Schema(description = "Movie name of Projection.",
            example = "Matrix")
    private String movieName;
    @Schema(description = "Date of Projection.",
            example = "2022-04-20", implementation = String.class)
    private LocalDate date;
    @Schema(description = "Time of Projection.",
            example = "2:05:00", implementation = String.class)
    private LocalTime time;
    @Schema(description = "Hall where movie is projecting",
            example = "Sala 2")
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
