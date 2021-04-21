package com.example.cinema.model.responseModel;

import io.swagger.v3.oas.annotations.media.Schema;

public class CinemaResponse {
    @Schema(description = "Unique identifier of the Cinema.",
            example = "1")
    private Integer id;
    @Schema(description = "Name of the Cinema.",
            example = "Cineplexx")
    private String name;
    @Schema(description = "Address of the Cinema.",
            example = "Usce shopping center")
    private String addres;

    public CinemaResponse() {
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

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }
}
