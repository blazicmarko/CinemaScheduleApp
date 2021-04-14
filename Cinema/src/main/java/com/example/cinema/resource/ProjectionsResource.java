package com.example.cinema.resource;

import com.example.cinema.model.requestModel.FilterReq;
import com.example.cinema.model.requestModel.ProjectionReq;
import com.example.cinema.model.requestModel.ProjectionUpdateReq;
import com.example.cinema.model.responseModel.BasicResponse;
import com.example.cinema.model.responseModel.ProjectionRespone;
import com.example.cinema.model.responseModel.ProjectionViewResposne;
import com.example.cinema.service.ProjectionService;
import com.example.cinema.validator.sequences.RequestValidationSequence;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/rest/projections")

public class ProjectionsResource {
    private ProjectionService projectionService;

    @Autowired
    public ProjectionsResource(ProjectionService projectionService) {
        this.projectionService = projectionService;
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
    @GetMapping("/all")
    @ApiOperation(value = "Getting all projections",
            response = LinkedList.class)
    public List<ProjectionRespone> getAll() {
        return projectionService.getAll();

    }

    //insert new projection
    @PostMapping("/insert")
    @ResponseBody
    @ApiOperation(value = "Inserting new projection")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The projection is inserted into table projections."),
            @ApiResponse(code = 409, message = "Data you used is not acceptable")})
    public ResponseEntity<Object> insert(@RequestBody @Validated(RequestValidationSequence.class)
                                         @ApiParam(value = "Projection that has all objects", required = true,
                                                 examples = @Example(
                                                         value = {
                                                                 @ExampleProperty(value = "{'date': '2021-04-16'}", mediaType = "application/json")
                                                         })) ProjectionReq projectionReq) {
        projectionService.insert(projectionReq);
        return handleInsertInProjections();
    }

    //When we update some of variables
    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Object> update(@RequestBody @Validated(RequestValidationSequence.class) ProjectionUpdateReq projection) {
        projectionService.update(projection);
        return handleUpdateInProjections();
    }

    //filter projections by movie name and optionally date
    @GetMapping("/filter")
    public List<ProjectionViewResposne> getSelected(@RequestBody @Valid FilterReq filterReq) {
        return projectionService.getSelected(filterReq);
    }
}
