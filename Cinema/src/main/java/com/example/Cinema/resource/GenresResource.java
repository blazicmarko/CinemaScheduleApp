package com.example.Cinema.resource;

import com.example.Cinema.mapper.GenresMapper;
import com.example.Cinema.model.Genres;
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
    public List<Genres> getAll(){
        return genresMapper.findAll();
    }
}
