package com.example.Cinema.resource;

import com.example.Cinema.exception.ApiException;
import com.example.Cinema.exception.ApiExceptionHandler;
import com.example.Cinema.model.Filter;
import com.example.Cinema.model.ProjectionView;
import com.example.Cinema.model.Projections;
import com.example.Cinema.model.ProjectionsUpdate;
import com.example.Cinema.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.ConnectException;
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

    //get all projections
    @GetMapping("/all")
    public List<Projections> getAll() throws ConnectException {
        return projectionService.getAll();

    }
    //insert new projection
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<Object> insert(@RequestBody @Valid Projections projection) throws ConnectException {
        projectionService.insert(projection);
        return handleInsertInProjections();
    }

    //When we update some of variables
    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Object> update(@RequestBody @Valid ProjectionsUpdate projection) throws ConnectException {
        projectionService.update(projection);
        return handleUpdateInProjections();
    }

    //filter projections by movie name and optionally date
    @GetMapping("/filter")
    public List<ProjectionView> getSelected(@RequestBody @Valid Filter filter) throws ConnectException {
        return projectionService.getSelected(filter);
    }

    public static ResponseEntity<Object> handleUpdateInProjections() {
        HttpStatus inserted = HttpStatus.ACCEPTED;
        ApiException apiException = new ApiException(
                "The projection is updated in table projections.",
                HttpStatus.ACCEPTED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, inserted);
    }

    public static ResponseEntity<Object> handleInsertInProjections(){
        HttpStatus inserted = HttpStatus.CREATED;
        ApiException apiException = new ApiException(
                "The projection is inserted into table projections.",
                HttpStatus.CREATED,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, inserted);

    }
}
