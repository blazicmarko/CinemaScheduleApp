package com.example.Cinema.resource;

import com.example.Cinema.model.dbModel.FilterDB;
import com.example.Cinema.model.dbModel.ProjectionDB;
import com.example.Cinema.model.requestModel.ProjectionUpdateReq;
import com.example.Cinema.model.responseModel.ApiResponseModel;
import com.example.Cinema.model.responseModel.ProjectionViewResposne;
import com.example.Cinema.service.ProjectionService;
import com.example.Cinema.validator.OrderForCheck;
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
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                "The projection is updated in table projections.",
                HttpStatus.ACCEPTED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, inserted);
    }

    public static ResponseEntity<Object> handleInsertInProjections() {
        HttpStatus inserted = HttpStatus.CREATED;
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                "The projection is inserted into table projections.",
                HttpStatus.CREATED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, inserted);

    }

    //get all projections
    @GetMapping("/all")
    public List<ProjectionDB> getAll() {
        return projectionService.getAll();

    }

    //insert new projection
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<Object> insert(@RequestBody @Validated(OrderForCheck.class) ProjectionDB projectionDB) {
        projectionService.insert(projectionDB);
        return handleInsertInProjections();
    }

    //When we update some of variables
    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Object> update(@RequestBody @Validated(OrderForCheck.class) ProjectionUpdateReq projection) {
        projectionService.update(projection);
        return handleUpdateInProjections();
    }

    //filter projections by movie name and optionally date
    @GetMapping("/filter")
    public List<ProjectionViewResposne> getSelected(@RequestBody @Valid FilterDB filterDB) {
        return projectionService.getSelected(filterDB);
    }
}
