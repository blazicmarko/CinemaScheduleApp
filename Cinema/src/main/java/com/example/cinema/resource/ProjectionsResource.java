package com.example.cinema.resource;

import com.example.cinema.model.kafkaModel.ProjectionKafka;
import com.example.cinema.model.requestModel.FilterReq;
import com.example.cinema.model.requestModel.ProjectionReq;
import com.example.cinema.model.requestModel.ProjectionUpdateReq;
import com.example.cinema.model.responseModel.BasicResponse;
import com.example.cinema.model.responseModel.ProjectionRespone;
import com.example.cinema.model.responseModel.ProjectionViewResposne;
import com.example.cinema.service.ProjectionService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/rest/projections")
public class ProjectionsResource {
    public static Logger logger = LogManager.getLogger(ProjectionsResource.class.getName());

    private Logger getLogger() {
        return logger;
    }

    private ProjectionService projectionService;

    private static final String TOPIC = "projection_live";
    private KafkaTemplate<String, ProjectionKafka> kafkaTemplate;

    @Autowired
    public ProjectionsResource(ProjectionService projectionService, KafkaTemplate kafkaTemplate) {
        this.projectionService = projectionService;
        this.kafkaTemplate = kafkaTemplate;
    }

    public static ResponseEntity<Object> handleUpdateInProjections() {
        HttpStatus inserted = HttpStatus.ACCEPTED;
        BasicResponse basicResponse = new BasicResponse(
                "The projection is updated in table projections.",
                HttpStatus.ACCEPTED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, inserted);
    }

    public static ResponseEntity<Object> handleInsertInProjections() {
        HttpStatus inserted = HttpStatus.CREATED;
        BasicResponse basicResponse = new BasicResponse(
                "The projection is inserted into table projections.",
                HttpStatus.CREATED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, inserted);

    }

    //get all projections
    @Operation(summary = "Get all projections")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All projections",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectionRespone.class))}),
            @ApiResponse(responseCode = "204", description = "Table empty",
                    content = @Content)})
    @GetMapping("/all")
    public List<ProjectionRespone> getAll() {
        getLogger().info("Geting all projections.");
        return projectionService.getAll();
    }

    //insert new projection
    @Operation(summary = "Insert new projection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The projection is inserted into table projections.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Data you inserted is not valid",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The time you want for your projection is in use.",
                    content = @Content)
    })
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<Object> insert(@RequestBody @Validated(RequestValidationSequence.class) ProjectionReq projectionReq) {
        ProjectionKafka projectionKafka = projectionService.insert(projectionReq);
        getLogger().info("Projection with data " + projectionReq.toString() + " inserted in table.");
        if (projectionKafka != null)
            kafkaTemplate.send(TOPIC, projectionKafka);
        return handleInsertInProjections();
    }

    @Operation(summary = "Update projection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "The projection is updated.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Data you inserted is not valid",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The time you want for your projection is in use.",
                    content = @Content)
    })
    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Object> update(@RequestBody @Validated(RequestValidationSequence.class) ProjectionUpdateReq projection) {
        ProjectionKafka projectionKafka = projectionService.update(projection);
        getLogger().info("Projection updated with id :" + projection.getId());
        if (projectionKafka != null || kafkaTemplate.)
            kafkaTemplate.send(TOPIC, projectionKafka);
        return handleUpdateInProjections();
    }

    @Operation(summary = "Filter your projects with name and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get filtered projections",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectionViewResposne.class))}),
            @ApiResponse(responseCode = "204", description = "Table empty",
                    content = @Content)})
    @GetMapping("/filter")
    public List<ProjectionViewResposne> getSelected(@RequestBody @Valid FilterReq filterReq) {
        getLogger().info("Filter of projections with data movieName : " + filterReq.getMovieName() + " date : " + filterReq.getDate());
        return projectionService.getSelected(filterReq);
    }
}
