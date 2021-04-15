package com.example.cinema.resource;

import com.example.cinema.model.responseModel.HallResponse;
import com.example.cinema.service.HallsService;
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

@RestController
@RequestMapping("/rest/halls")
public class HallsResource {
    private HallsService hallsService;


    @Autowired
    public HallsResource(HallsService hallsService) {
        this.hallsService = hallsService;
    }

    @Operation(summary = "Get all halls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All halls",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HallResponse.class))}),
            @ApiResponse(responseCode = "204", description = "Table empty",
                    content = @Content)})
    @GetMapping("/all")
    public List<HallResponse> getAll() {
        return hallsService.findAll();
    }
}
