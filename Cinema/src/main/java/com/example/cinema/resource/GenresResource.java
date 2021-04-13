package com.example.cinema.resource;

import com.example.cinema.mapper.GenresMapper;
import com.example.cinema.model.dbModel.GenreDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/genres")
public class GenresResource {
    private GenresMapper genresMapper;

    @Autowired
    public GenresResource(GenresMapper genresMapper) {
        this.genresMapper = genresMapper;
    }

    @GetMapping("/all")
    public List<GenreDB> getAll() {
        return genresMapper.findAll();
    }
}
