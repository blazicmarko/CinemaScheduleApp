package com.example.cinema.model.responseModel;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProjectionRespone {

    @Schema(description = "Unique identifier of the Projection.",
            example = "1")
    private Integer id;
    @Schema(description = "Identifier of the Movie for Projection you are inserting.",
            example = "1")
    private Integer idMovie;
    @Schema(description = "Identifier of the Hall in witch you are projecting.",
            example = "1")
    private Integer idHall;
    @Schema(description = "Date of the projection must be in present or future.")
    private LocalDate date;
    @Schema(description = "Time when the projection start.",
            example = "14:00:00", required = true, implementation = String.class)
    private LocalTime startTime;
    @Schema(implementation = String.class, description = "Time when projection ends", example = "16:20:00")
    private LocalTime endTime;

    public ProjectionRespone() {
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
