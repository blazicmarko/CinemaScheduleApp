package com.example.Cinema.service;

import com.example.Cinema.exception.*;
import com.example.Cinema.mapper.ProjectionsMapper;
import com.example.Cinema.model.Filter;
import com.example.Cinema.model.ProjectionView;
import com.example.Cinema.model.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ProjectionService {

    private ProjectionsMapper projectionsMapper;
    private MoviesService moviesService;
    private HallsService hallsService;

    @Autowired
    public ProjectionService(ProjectionsMapper projectionsMapper, HallsService hallsService, MoviesService moviesService) {
        this.projectionsMapper = projectionsMapper;
        this.moviesService = moviesService;
        this.hallsService = hallsService;
    }

    public void insert(Projections projection) {

        if(projection.getIdHall() > hallsService.getLastId()){
            throw new NoHallException("Hall with that id doesn't exists.");
        }
        if(projection.getIdMovie() > moviesService.getLastId()){
            throw new NoMovieException("Movie with that id doesn't exists.");
        }
        if(projectionsMapper.findOne(projection) == 0) {
            LocalTime movieTime = moviesService.findTime(projection);
            LocalTime time = projectionsMapper.getEndTime(projection, movieTime);
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

    public List<Projections> getAll(){
        List<Projections> list = projectionsMapper.findAll();
        if(list.isEmpty()){
            throw new TableEmptyException("Table projections is empty");
        }
        else
            return list;
    }

    public void update(Projections projection){
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

            LocalTime movieTime = moviesService.findTime(oldProjection);
            LocalTime time = projectionsMapper.getEndTime(oldProjection, movieTime);
            oldProjection.setEndTime(time);
            Integer numOfProjections;
            numOfProjections = projectionsMapper.isFree(oldProjection);
            if (numOfProjections == 0) {
                projectionsMapper.update(oldProjection);
            } else {
                throw new AppointmentCheckException("This appointment is full. Choose another one!");
            }
        }
    }

    public List<ProjectionView> getSelected(Filter filter){
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
