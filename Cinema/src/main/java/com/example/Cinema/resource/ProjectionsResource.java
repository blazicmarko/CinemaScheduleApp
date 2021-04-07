package com.example.Cinema.resource;

import com.example.Cinema.model.Filter;
import com.example.Cinema.model.ProjectionView;
import com.example.Cinema.model.Projections;
import com.example.Cinema.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.ConnectException;
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
    public void insert(@RequestBody @Valid Projections projection) throws ConnectException {
        projectionService.insert(projection);
    }

    //When we update some of variables
    @PostMapping("/update")
    public void update(@RequestBody @Valid Projections projection) throws ConnectException {
        projectionService.update(projection);
    }

    //filter projections by movie name and optionally date
    @GetMapping("/filter")
    public List<ProjectionView> getSelected(@RequestBody @Valid Filter filter) throws ConnectException {
        return projectionService.getSelected(filter);
    }

}
