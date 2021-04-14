package com.example.cinema.model.requestModel;

import com.example.cinema.validator.ValidIdHall;
import com.example.cinema.validator.ValidIdMovie;
import com.example.cinema.validator.ValidTime;
import com.example.cinema.validator.groups.FirstPriorGroup;
import com.example.cinema.validator.groups.SecondPriorGroup;
import com.example.cinema.validator.groups.ThirdPriorGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Valid
@ApiModel(description = "Details about projection. Model for database table.")
public class ProjectionReq {

    @ApiModelProperty(dataType = "Integer.class", notes = "Unique identifier of projection", value = "2", example = "1")
    private Integer id;
    @NotNull(groups = FirstPriorGroup.class)
    @ValidIdMovie(groups = SecondPriorGroup.class)
    private Integer idMovie;
    @NotNull(groups = FirstPriorGroup.class)
    @ValidIdHall(groups = SecondPriorGroup.class)
    private Integer idHall;
    @NotNull(groups = FirstPriorGroup.class)
    @FutureOrPresent(groups = SecondPriorGroup.class)
    private LocalDate date;
    @NotNull(groups = FirstPriorGroup.class)
    @ValidTime(groups = ThirdPriorGroup.class)
    private LocalTime startTime;
    private LocalTime endTime;

    public ProjectionReq() {
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
