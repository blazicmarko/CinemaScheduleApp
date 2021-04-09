package com.example.Cinema.service;

import com.example.Cinema.exception.AppointmentCheckException;
import com.example.Cinema.exception.NoIdException;
import com.example.Cinema.exception.TableEmptyException;
import com.example.Cinema.mapper.ProjectionsMapper;
import com.example.Cinema.model.Filter;
import com.example.Cinema.model.ProjectionView;
import com.example.Cinema.model.Projections;
import com.example.Cinema.model.ProjectionsUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        LocalTime movieTime = moviesService.findTime(projection);
        LocalTime time = projectionsMapper.getEndTime(projection, movieTime);
        projection.setEndTime(time);
        Integer numOfProjections;
        numOfProjections = projectionsMapper.isFreeToInsert(projection);
        if (numOfProjections == 0) {
            projectionsMapper.insert(projection);
        } else {
            throw new AppointmentCheckException("This appointment is full. Choose another one!");
        }
    }

    public List<Projections> getAll(){
        List<Projections> list = projectionsMapper.findAll();
        if(list.isEmpty()){
            throw new TableEmptyException("Table projections is empty");
        }
        else
            return list;
    }

    public void update(ProjectionsUpdate projection){
        Map<String , String> updateVars = new HashMap<>();
        Projections oldProjection;
        if(projection.getId() != null){
            oldProjection = projectionsMapper.findSpecific(projection.getId());
        }
        else {
            throw new NoIdException("There is no inserted id in table projections.");
        }
        if(projection.getDate() != null){
            updateVars.put("date",projection.getDate().toString());
            oldProjection.setDate(projection.getDate());
        }
        if(projection.getStartTime() != null){
            updateVars.put("startTime",projection.getStartTime().toString());
            oldProjection.setStartTime(projection.getStartTime());
        }
        if(projection.getIdHall() != null){
            updateVars.put("id_hall",projection.getIdHall().toString());
            oldProjection.setIdHall(projection.getIdHall());
        }
        if(projection.getIdMovie() != null){
            updateVars.put("id_movie",projection.getIdMovie().toString());
            oldProjection.setIdMovie(projection.getIdMovie());
        }

        LocalTime movieTime = moviesService.findTime(oldProjection);
        LocalTime time = projectionsMapper.getEndTime(oldProjection, movieTime);
        oldProjection.setEndTime(time);
        Integer numOfProjections;
        numOfProjections = projectionsMapper.isFreeToUpdate(oldProjection);
        if (numOfProjections == 0) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            updateVars.put("endTime", oldProjection.getEndTime().format(dtf));
            projectionsMapper.update(updateVars , projection.getId());
        } else {
            throw new AppointmentCheckException("This appointment is full. Choose another one!");
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
