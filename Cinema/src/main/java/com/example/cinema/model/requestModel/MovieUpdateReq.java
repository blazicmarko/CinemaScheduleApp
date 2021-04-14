package com.example.cinema.model.requestModel;

import com.example.cinema.validator.ValidIdGenre;
import com.example.cinema.validator.ValidMovieName;
import com.example.cinema.validator.ValidTime;
import com.example.cinema.validator.groups.SecondPriorGroup;
import com.example.cinema.validator.groups.ThirdPriorGroup;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalTime;

public class MovieUpdateReq {

    private Integer id;
    @ValidMovieName(groups = ThirdPriorGroup.class)
    private String name;
    @Min(value = 1, groups = SecondPriorGroup.class)
    @Max(value = 10, groups = SecondPriorGroup.class)
    private Double grade;
    @Min(value = 1900, groups = SecondPriorGroup.class)
    @Max(value = 2021, groups = SecondPriorGroup.class)
    private Integer year;
    @ValidIdGenre(groups = SecondPriorGroup.class)
    private Integer idGenre;
    @ValidTime(groups = ThirdPriorGroup.class)
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
