package com.example.cinema.model.responseModel;

import io.swagger.v3.oas.annotations.media.Schema;

public class HallResponse {
    @Schema(description = "Unique identifier of the Hall.",
            example = "1")
    private Integer id;
    @Schema(description = "Name of the Hall.",
            example = "Sala 1")
    private String name;
    @Schema(description = "Identifier of Cinema of the Hall.",
            example = "1")
    private Integer idCinema;


    public HallResponse() {
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

    public Integer getIdCinema() {
        return idCinema;
    }

    public void setIdCinema(Integer idCinema) {
        this.idCinema = idCinema;
    }
}
