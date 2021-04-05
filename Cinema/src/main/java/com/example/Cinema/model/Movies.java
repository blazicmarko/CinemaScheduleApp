package com.example.Cinema.model;

import java.sql.Time;

public class Movies {
    private String name;
    private Integer id;
    private Long grade;
    private Integer year;
    private Integer idGenre;
    private Time time;

    public Movies(){

    }

    public Movies(String name, Long grade, Integer year, Integer idGenre, Time time) {
        this.name = name;
        this.grade = grade;
        this.year = year;
        this.idGenre = idGenre;
        this.time = time;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
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

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
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
