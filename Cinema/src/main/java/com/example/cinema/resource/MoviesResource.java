package com.example.cinema.resource;

import com.example.cinema.model.dbModel.MovieDB;
import com.example.cinema.model.requestModel.MovieUpdateReq;
import com.example.cinema.model.responseModel.ApiResponseModel;
import com.example.cinema.service.MoviesService;
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
    public MoviesResource(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    public static ResponseEntity<Object> handleUpdateInMovies() {
        HttpStatus inserted = HttpStatus.ACCEPTED;
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                "The movie is updated in table movies.",
                HttpStatus.ACCEPTED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, inserted);
    }

    public static ResponseEntity<Object> handleInsertInMovies() {
        HttpStatus inserted = HttpStatus.CREATED;
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                "The movie is inserted into table movies.",
                HttpStatus.CREATED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, inserted);

    }

    @GetMapping("/all")
    public List<MovieDB> getAll() {
        return moviesService.findAll();
    }

    @GetMapping("/findMovieByName")
    public List<MovieDB> getByName(@RequestBody String name) {
        return moviesService.getByName(name);
    }

    @GetMapping("/findMoviesByGenre")
    public List<MovieDB> getByGenre(@RequestBody String genre) {
        return moviesService.getByGenre(genre);
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Valid MovieDB movieDB) {
        moviesService.insert(movieDB);
        return handleInsertInMovies();
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody @Valid MovieUpdateReq movie) {
        moviesService.update(movie);
        return handleUpdateInMovies();
    }

}