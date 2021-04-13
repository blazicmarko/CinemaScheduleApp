package com.example.Cinema.resource;

import com.example.Cinema.exception.ApiHappyEnd;
import com.example.Cinema.model.Movies;
import com.example.Cinema.model.MoviesUpdate;
import com.example.Cinema.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/rest/movies")
public class MoviesResource {
    private MoviesService moviesService;

    @Autowired
    public MoviesResource(MoviesService moviesService){
        this.moviesService = moviesService;
    }

    @GetMapping("/all")
    public List<Movies> getAll(){
        return moviesService.findAll();
    }

    @GetMapping("/findMovieByName")
    public List<Movies> getByName(@RequestBody String name){
        return moviesService.getByName(name);
    }

    @GetMapping("/findMoviesByGenre")
    public List<Movies> getByGenre(@RequestBody String genre){
        return moviesService.getByGenre(genre);
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Valid Movies movie){
        moviesService.insert(movie);
        return handleInsertInMovies();
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody @Valid MoviesUpdate movie){
        moviesService.update(movie);
        return handleUpdateInMovies();
    }

    public static ResponseEntity<Object> handleUpdateInMovies() {
        HttpStatus inserted = HttpStatus.ACCEPTED;
        ApiHappyEnd apiHappyEnd = new ApiHappyEnd(
                "The movie is updated in table movies.",
                HttpStatus.ACCEPTED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiHappyEnd, inserted);
    }

    public static ResponseEntity<Object> handleInsertInMovies(){
        HttpStatus inserted = HttpStatus.CREATED;
        ApiHappyEnd apiHappyEnd = new ApiHappyEnd(
                "The movie is inserted into table movies.",
                HttpStatus.CREATED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiHappyEnd, inserted);

    }

}