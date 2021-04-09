package com.example.Cinema.model;

import com.example.Cinema.validator.ValidIdGenre;
import com.example.Cinema.validator.ValidMovieName;
import com.example.Cinema.validator.ValidTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class Movies {

    private Integer id;
    @NotNull
    @ValidMovieName
    private String name;
    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    private Double grade;
    @NotNull
    @Min(value = 1900)
    @Max(value = 2021)
    private Integer year;
    @NotNull
    @ValidIdGenre
    private Integer idGenre;
    @NotNull
    @ValidTime
    private LocalTime time;

    public Movies(){

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
