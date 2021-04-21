package com.example.cinema.model.responseModel;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

public class MovieResponse {

    @Schema(description = "Unique identifier of the Movie.",
            example = "1")
    private Integer id;
    @Schema(description = "Unique Name of the Movie.",
            example = "Matrix")
    private String name;
    @Schema(description = "Grade of the Movie.",
            example = "5.5")
    private Double grade;
    @Schema(description = "Year when the Movie is created.",
            example = "2005")
    private Integer year;
    @Schema(description = "Identifier of Genre of the Movie.",
            example = "1")
    private Integer idGenre;
    @Schema(description = "Time the Movie lasts.",
            example = "2:05:00", implementation = String.class)
    private LocalTime time;

    public MovieResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
