package com.example.cinema.model.responseModel;

import io.swagger.v3.oas.annotations.media.Schema;

public class GenreResponse {
    @Schema(description = "Unique identifier of the Genre.",
            example = "1")
    private Integer id;
    @Schema(description = "Name of the Genre.",
            example = "Crime")
    private String name;

    public GenreResponse() {
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
}
