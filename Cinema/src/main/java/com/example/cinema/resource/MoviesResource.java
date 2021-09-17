package com.example.cinema.resource;

import com.example.cinema.model.requestModel.MovieReq;
import com.example.cinema.model.requestModel.MovieUpdateReq;
import com.example.cinema.model.responseModel.BasicResponse;
import com.example.cinema.model.responseModel.MovieResponse;
import com.example.cinema.service.MoviesService;
import com.example.cinema.validator.sequences.RequestValidationSequence;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/rest/movies")
public class MoviesResource {
    public static Logger logger = LogManager.getLogger(MoviesResource.class.getName());

    private Logger getLogger() {
        return logger;
    }

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

    @Operation(summary = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All movies",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))}),
            @ApiResponse(responseCode = "204", description = "Table empty",
                    content = @Content)})
    @GetMapping("/all")
    public List<MovieResponse> getAll() {
        getLogger().info("Getting all movies.");
        return moviesService.findAll();
    }

    @Operation(summary = "Filter all movies by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All movies with selected name",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))}),
            @ApiResponse(responseCode = "204", description = "Table empty",
                    content = @Content)})
    @GetMapping("/findMovieByName")
    public List<MovieResponse> getByName(@RequestBody String name) {
        getLogger().info("Filter of movies with movie name " + name);
        return moviesService.getByName(name);
    }

    @Operation(summary = "Filter all movies by genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All movies with selected genre",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))}),
            @ApiResponse(responseCode = "204", description = "Table empty",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Inserted wrong genre name",
                    content = @Content)
    })
    @GetMapping("/findMoviesByGenre")
    public List<MovieResponse> getByGenre(@RequestBody String genre) {
        getLogger().info("Filter of movies with genre " + genre);
        return moviesService.getByGenre(genre);
    }

    @Operation(summary = "Insert new movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The movie is inserted into table movies.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Data you inserted is not valid",
                    content = @Content)
    })
    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Validated(RequestValidationSequence.class) MovieReq movieReq) {
        moviesService.insert(movieReq);
        getLogger().info("Movie with data " + movieReq.toString() + " inserted in table.");
        return handleInsertInMovies();
    }

    @PostMapping(value = "/insert/{imdbId}")
    public ResponseEntity<Object> insertWithId(@PathVariable("imdbId") String imdbId){
        moviesService.insertWithId(imdbId);
        getLogger().info("Movie with id on IMDB " + imdbId + " inserted in table.");
        return handleInsertInMovies();
    }

    @Operation(summary = "Update movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "The movie is updated.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Data you inserted is not valid",
                    content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody @Validated(RequestValidationSequence.class) MovieUpdateReq movie) {
        getLogger().info("Movie with id =" + movie.getId() + " updated.");
        moviesService.update(movie);
        return handleUpdateInMovies();
    }

}