package com.example.cinema.resource;

import com.example.cinema.model.responseModel.GenreResponse;
import com.example.cinema.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/genres")
public class GenresResource {
    private GenreService genresService;

    @Autowired
    public GenresResource(GenreService genresService) {
        this.genresService = genresService;
    }

    @GetMapping("/all")
    public List<GenreResponse> getAll() {
        return genresService.findAll();
    }
}
