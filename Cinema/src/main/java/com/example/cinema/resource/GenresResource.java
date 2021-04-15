package com.example.cinema.resource;

import com.example.cinema.model.responseModel.GenreResponse;
import com.example.cinema.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.cinema.CinemaApplication.getLogger;

@RestController
@RequestMapping("/rest/genres")
public class GenresResource {
    private GenreService genresService;

    @Autowired
    public GenresResource(GenreService genresService) {
        this.genresService = genresService;
    }

    @Operation(summary = "Get all genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All genres",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenreResponse.class))}),
            @ApiResponse(responseCode = "204", description = "Table empty",
                    content = @Content)})
    @GetMapping("/all")
    public List<GenreResponse> getAll() {
        getLogger().info("Getting all genres.");
        return genresService.findAll();
    }
}
