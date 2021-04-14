package com.example.cinema.resource;

import com.example.cinema.model.requestModel.MovieReq;
import com.example.cinema.model.requestModel.MovieUpdateReq;
import com.example.cinema.model.responseModel.BasicResponse;
import com.example.cinema.model.responseModel.MovieResponse;
import com.example.cinema.service.MoviesService;
import com.example.cinema.validator.sequences.RequestValidationSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/rest/movies")
public class MoviesResource {
    private MoviesService moviesService;

    @Autowired
    public MoviesResource(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    public static ResponseEntity<Object> handleUpdateInMovies() {
        HttpStatus inserted = HttpStatus.ACCEPTED;
        BasicResponse basicResponse = new BasicResponse(
                "The movie is updated in table movies.",
                HttpStatus.ACCEPTED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, inserted);
    }

    public static ResponseEntity<Object> handleInsertInMovies() {
        HttpStatus inserted = HttpStatus.CREATED;
        BasicResponse basicResponse = new BasicResponse(
                "The movie is inserted into table movies.",
                HttpStatus.CREATED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, inserted);

    }

    @GetMapping("/all")
    public List<MovieResponse> getAll() {
        return moviesService.findAll();
    }

    @GetMapping("/findMovieByName")
    public List<MovieResponse> getByName(@RequestBody String name) {
        return moviesService.getByName(name);
    }

    @GetMapping("/findMoviesByGenre")
    public List<MovieResponse> getByGenre(@RequestBody String genre) {
        return moviesService.getByGenre(genre);
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Validated(RequestValidationSequence.class) MovieReq movieReq) {
        moviesService.insert(movieReq);
        return handleInsertInMovies();
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody @Validated(RequestValidationSequence.class) MovieUpdateReq movie) {
        moviesService.update(movie);
        return handleUpdateInMovies();
    }

}