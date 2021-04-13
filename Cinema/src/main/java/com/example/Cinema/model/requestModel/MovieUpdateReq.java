package com.example.Cinema.model.requestModel;

import com.example.Cinema.validator.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalTime;

public class MovieUpdateReq {

    private Integer id;
    @ValidMovieName(groups = ThirdPriorInfo.class)
    private String name;
    @Min(value = 1, groups = RequestValidationSequence.class)
    @Max(value = 10, groups = RequestValidationSequence.class)
    private Double grade;
    @Min(value = 1900, groups = RequestValidationSequence.class)
    @Max(value = 2021, groups = RequestValidationSequence.class)
    private Integer year;
    @ValidIdGenre(groups = RequestValidationSequence.class)
    private Integer idGenre;
    @ValidTime(groups = ThirdPriorInfo.class)
    private LocalTime time;

    public MovieUpdateReq() {

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
