package com.example.Cinema.resource;

import com.example.Cinema.mapper.ProjectionsMapper;
import com.example.Cinema.model.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/projections")
public class ProjectionsResource {
    private ProjectionsMapper projectionsMapper;


    @Autowired
    public ProjectionsResource(ProjectionsMapper projectionsMapper) {
        this.projectionsMapper = projectionsMapper;
    }

    @GetMapping("/all")
    public List<Projections> getAll(){
        return projectionsMapper.findAll();
    }
}
