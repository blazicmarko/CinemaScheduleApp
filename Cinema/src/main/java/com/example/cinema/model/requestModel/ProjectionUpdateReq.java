package com.example.cinema.model.requestModel;

import com.example.cinema.validator.ValidIdHall;
import com.example.cinema.validator.ValidIdMovie;
import com.example.cinema.validator.ValidTime;
import com.example.cinema.validator.groups.SecondPriorGroup;
import com.example.cinema.validator.groups.ThirdPriorGroup;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

public class ProjectionUpdateReq {

    @Schema(description = "Unique identifier of the Projection.",
            example = "1")
    private Integer id;
    @ValidIdMovie(groups = SecondPriorGroup.class)
    @Schema(description = "Unique identifier of the Movie for Projection you are inserting.",
            example = "1", required = true)
    private Integer idMovie;
    @ValidIdHall(groups = SecondPriorGroup.class)
    @Schema(description = "Unique identifier of the Hall in witch you are projecting.",
            example = "1", required = true)
    private Integer idHall;
    @FutureOrPresent(groups = SecondPriorGroup.class)
    @Schema(description = "Date of the projection must be in present or future.", required = true)
    private LocalDate date;
    @ValidTime(groups = ThirdPriorGroup.class)
    @Schema(description = "Time of the projection must be between 8 and 20h because of working hours of Cinema.",
            example = "14:00:00", required = true, implementation = String.class)
    private LocalTime startTime;
    @Schema(implementation = String.class, description = "Program implements end time", example = "Program implements end time")
    private LocalTime endTime;

    public ProjectionUpdateReq() {
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
