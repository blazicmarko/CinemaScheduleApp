package com.example.Cinema.resource;

import com.example.Cinema.exception.AppointmentCheckException;
import com.example.Cinema.mapper.MoviesMapper;
import com.example.Cinema.mapper.ProjectionsMapper;
import com.example.Cinema.model.Filter;
import com.example.Cinema.model.ProjectionView;
import com.example.Cinema.model.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/rest/projections")
public class ProjectionsResource {
    private ProjectionsMapper projectionsMapper;
    private MoviesMapper moviesMapper;

    @Autowired
    public ProjectionsResource(ProjectionsMapper projectionsMapper, MoviesMapper moviesMapper) {
        this.projectionsMapper = projectionsMapper;
        this.moviesMapper = moviesMapper;
    }

    @GetMapping("/all")
    public List<Projections> getAll(){
        return projectionsMapper.findAll();
    }

    @PostMapping("/insert")
    public void insert(@RequestBody Projections projection){
        if(projectionsMapper.findOne(projection) == 0) {
            Time movieTime = moviesMapper.findTime(projection);
            Time time = projectionsMapper.getEndTime(projection, movieTime);
            projection.setEndTime(time);
            if (projectionsMapper.isFree(projection) == 0) {
                projectionsMapper.insert(projection);

            } else {
                throw new AppointmentCheckException("This appointment is full. Choose another one!");
            }
        }
        else
            throw new AppointmentCheckException("Projection with id "+ projection.getId()+" already exists.");

    }

    @PostMapping("/update")
    public void update(@RequestBody Projections projection){
        if(projectionsMapper.findOne(projection) == 1) {
            //NADJENU PROJEKCIJU IZMENITI JOJ PODATKE KOJI NISU NULL I SA NJOM RADITI
            Time movieTime = moviesMapper.findTime(projection);
            Time time = projectionsMapper.getEndTime(projection, movieTime);
            projection.setEndTime(time);
            Integer numOfProjections;
            numOfProjections = projectionsMapper.isFree(projection);
            if (numOfProjections == 0) {
                projectionsMapper.update(projection);
            } else {
                throw new AppointmentCheckException("This appointment is full. Choose another one!");
            }
        }
        else
            throw new AppointmentCheckException("There is no projection with id="+ projection.getId()+". Try again.");

    }


    @GetMapping("/filter")
    public List<ProjectionView> getSelected(@RequestBody Filter filter){
        if(filter.getDate() != null)
            return projectionsMapper.getSelected(filter);
        else
            return projectionsMapper.getSelectedNoDate(filter);
    }

}
