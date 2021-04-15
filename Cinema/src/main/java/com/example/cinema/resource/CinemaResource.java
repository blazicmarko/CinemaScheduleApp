package com.example.cinema.resource;

import com.example.cinema.model.responseModel.CinemaResponse;
import com.example.cinema.service.CinemaService;
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
@RequestMapping("/rest/cinema")
public class CinemaResource {
    private CinemaService cinemaService;

    @Autowired
    public CinemaResource(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Operation(summary = "Get all cinemas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All cinemas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CinemaResponse.class))}),
            @ApiResponse(responseCode = "204", description = "Table empty",
                    content = @Content)})
    @GetMapping("/all")
    public List<CinemaResponse> getAll() {
        getLogger().info("Getting all cinemas.");
        return cinemaService.findAll();
    }
}
