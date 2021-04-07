package com.example.Cinema.resource;

import com.example.Cinema.exception.*;
import com.example.Cinema.mapper.HallsMapper;
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
    private HallsMapper hallsMapper;

    @Autowired
    public ProjectionsResource(ProjectionsMapper projectionsMapper, MoviesMapper moviesMapper, HallsMapper hallsMapper) {
        this.projectionsMapper = projectionsMapper;
        this.moviesMapper = moviesMapper;
        this.hallsMapper = hallsMapper;
    }

    //get all projections
    @GetMapping("/all")
    public List<Projections> getAll(){
        List<Projections> list = projectionsMapper.findAll();
        if(list.isEmpty()){
            throw new TableEmptyException("Table projections is empty");
        }
        else
            return list;
    }

    //insert new projection
    @PostMapping("/insert")
    public void insert(@RequestBody Projections projection){
        if(projection.getIdMovie() == null || projection.getIdHall() == null || projection.getStartTime() == null || projection.getDate() == null){
            throw new NullVariableException("You did not inserted some of data.");

        }
        if(moviesMapper.findMovie(projection.getIdMovie()) == 0){
            throw new NoMovieException("There is no movie with id you inserted.");
        }
        if(hallsMapper.findHall(projection.getIdHall()) == 0){
            throw new NoHallException("There is no hall with id you inserted.");
        }
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
            throw new IdExistsException("Projection with id "+ projection.getId()+" already exists.");

    }

//    //When we update all variables except id
//    @PostMapping("/updateAll")
//    public void updateAll(@RequestBody Projections projection){
//        if(projectionsMapper.findOne(projection) == 1) {
//            Time movieTime = moviesMapper.findTime(projection);
//            Time time = projectionsMapper.getEndTime(projection, movieTime);
//            projection.setEndTime(time);
//            Integer numOfProjections;
//            numOfProjections = projectionsMapper.isFree(projection);
//            if (numOfProjections == 0) {
//                projectionsMapper.update(projection);
//            } else {
//                throw new AppointmentCheckException("This appointment is full. Choose another one!");
//            }
//        }
//        else
//            throw new AppointmentCheckException("There is no projection with id="+ projection.getId()+". Try again.");
//
//    }

    //When we update some of variables
    @PostMapping("/update")
    public void update(@RequestBody Projections projection){
        Projections oldProjection;
        if(projection.getId() != null){
            oldProjection = projectionsMapper.findSpecific(projection);
        }
        else {
            throw new NoIdException("There is no inserted id in table projections.");
        }
        if(oldProjection != null) {
            if(projection.getDate() != null){
                oldProjection.setDate(projection.getDate());
            }
            if(projection.getStartTime() != null){
                oldProjection.setStartTime(projection.getStartTime());
            }
            if(projection.getIdHall() != null){
                oldProjection.setIdHall(projection.getIdHall());
            }
            if(projection.getIdMovie() != null){
                oldProjection.setIdMovie(projection.getIdMovie());
            }
            Time movieTime = moviesMapper.findTime(oldProjection);
            Time time = projectionsMapper.getEndTime(oldProjection, movieTime);
            oldProjection.setEndTime(time);
            Integer numOfProjections;
            numOfProjections = projectionsMapper.isFree(oldProjection);
            if (numOfProjections == 0) {
                projectionsMapper.update(oldProjection);
            } else {
                throw new AppointmentCheckException("This appointment is full. Choose another one!");
            }
        }
        else
            throw new IdExistsException("There is no projection with id="+ projection.getId()+". Try again.");
    }

    //filter projections by movie name and optionally date
    @GetMapping("/filter")
    public List<ProjectionView> getSelected(@RequestBody Filter filter){
        List<ProjectionView> list;
        if(filter.getDate() != null){
            list = projectionsMapper.getSelected(filter);
        }
        else{
            list = projectionsMapper.getSelectedNoDate(filter);
        }
        if(list.isEmpty()){
            throw new TableEmptyException("Table projections is empty");
        }
        else
            return list;
    }

}
