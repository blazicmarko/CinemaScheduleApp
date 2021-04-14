package com.example.cinema.resource;

import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.requestModel.FilterReq;
import com.example.cinema.model.requestModel.ProjectionUpdateReq;
import com.example.cinema.model.responseModel.BasicResponse;
import com.example.cinema.model.responseModel.ProjectionViewResposne;
import com.example.cinema.service.ProjectionService;
import com.example.cinema.validator.sequences.RequestValidationSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
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
    public List<ProjectionDB> getAll() {
        return projectionService.getAll();

    }

    //insert new projection
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<Object> insert(@RequestBody @Validated(RequestValidationSequence.class) ProjectionDB projectionDB) {
        projectionService.insert(projectionDB);
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
