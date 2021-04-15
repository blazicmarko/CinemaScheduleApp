package com.example.cinema.model.requestModel;

import com.example.cinema.validator.ValidIdGenre;
import com.example.cinema.validator.ValidMovieName;
import com.example.cinema.validator.ValidTime;
import com.example.cinema.validator.groups.FirstPriorGroup;
import com.example.cinema.validator.groups.SecondPriorGroup;
import com.example.cinema.validator.groups.ThirdPriorGroup;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Valid
public class MovieReq {

    @Schema(description = "Unique identifier of the Movie.",
            example = "1")
    private Integer id;
    @NotNull(groups = FirstPriorGroup.class)
    @ValidMovieName(groups = ThirdPriorGroup.class)
    @Schema(description = "Unique name of the Movie.",
            example = "Matrix")
    private String name;
    @NotNull(groups = FirstPriorGroup.class)
    @Min(value = 1, groups = SecondPriorGroup.class)
    @Max(value = 10, groups = SecondPriorGroup.class)
    @Schema(description = "Grade of the Movie.",
            example = "5.5")
    private Double grade;
    @NotNull(groups = FirstPriorGroup.class)
    @Min(value = 1900, groups = SecondPriorGroup.class)
    @Max(value = 2021, groups = SecondPriorGroup.class)
    @Schema(description = "Year whe the Movie was created.",
            example = "2005")
    private Integer year;
    @NotNull(groups = FirstPriorGroup.class)
    @ValidIdGenre(groups = SecondPriorGroup.class)
    @Schema(description = "Identifier of the Genre of Movie.",
            example = "1")
    private Integer idGenre;
    @NotNull(groups = FirstPriorGroup.class)
    @ValidTime(groups = ThirdPriorGroup.class)
    @Schema(description = "How much does Movie lasts.",
            example = "01:50:00", implementation = String.class)
    private LocalTime time;

    public MovieReq() {

    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Integer idGenre) {
        this.idGenre = idGenre;
    }
}
