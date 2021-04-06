package com.example.Cinema.resource;

import com.example.Cinema.mapper.MoviesMapper;
import com.example.Cinema.model.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/movies")
public class MoviesResource {
    private MoviesMapper moviesMapper;

    @Autowired
    public MoviesResource(MoviesMapper moviesMapper) {
        this.moviesMapper = moviesMapper;
    }

    @GetMapping("/all")
    public List<Movies> getAll() {
        return moviesMapper.findAll();
    }

    @PostMapping("/insert")
    public void insert(@RequestBody Movies movie){
        moviesMapper.insert(movie);
    }

    @PostMapping("/update")
    public void update(@RequestBody Movies movie){ moviesMapper.update(movie);}

}