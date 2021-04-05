package com.example.Cinema.resource;

import com.example.Cinema.mapper.MoviesMapper;
import com.example.Cinema.model.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.net.URI;
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

    @PostMapping(value = "/insert",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Movies> postBody(@RequestBody Movies movies){
        Movies persistedMovie = moviesMapper.insert(movies);
        return ResponseEntity.created(URI.create(String.format("/movies/%s",movies.getId()))).body(persistedMovie);
    }
}