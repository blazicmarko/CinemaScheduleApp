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

import java.net.ConnectException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
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

    public Projections insert(Projections projection) throws ConnectException {
        LocalTime movieTime = moviesService.findTime(projection);
        LocalTime time = projectionsMapper.getEndTime(projection, movieTime);
        projection.setEndTime(time);
        Integer numOfProjections;
        numOfProjections = projectionsMapper.isFreeToInsert(projection);
        if (numOfProjections == 0) {
            projectionsMapper.insert(projection);
            return projection;
        } else {
            String message = getListOfFreeHalls(projection);
            throw new AppointmentCheckException(message);
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

    public Projections update(ProjectionsUpdate projection){
        Map<String , String> updateVars = new HashMap<>();
        Projections oldProjection;
        if(projection.getId() != null){
            if(projectionsMapper.findOne(projection.getId()) == 0){
                throw new NoIdException("There is no inserted that id in table projections");
            }
            oldProjection = projectionsMapper.findSpecific(projection.getId());
        }
        else {
            throw new NoIdException("There is no inserted id in your data of projections.");
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
        final Integer[] numOfProjections = new Integer[1];
        numOfProjections[0] = projectionsMapper.isFreeToUpdate(oldProjection);
        if (numOfProjections[0] == 0) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            updateVars.put("endTime", oldProjection.getEndTime().format(dtf));
            projectionsMapper.update(updateVars , projection.getId());
            return oldProjection;
        } else {
            String message = getListOfFreeHalls(oldProjection);
            throw new AppointmentCheckException(message);
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

    public String getListOfFreeHalls(Projections projection){
        final Integer[] numOfProjections = {1};
        Integer idCinema = hallsService.findCinemaOfHall(projection);
        List<Integer> listIdHalls;
        List<String> halls= new LinkedList<>();
        listIdHalls = hallsService.findAllHalls(idCinema);
        listIdHalls.forEach((temp) -> {
            projection.setIdHall(temp);
            numOfProjections[0] =projectionsMapper.isFreeToInsert(projection);
            if(numOfProjections[0] == 0){
                halls.add(hallsService.getName(temp));
            }
        });
        String message = "This appointment is full. Choose available halls :";
        for (String hall : halls) {
            message=message.concat(hall + " ");
        }
        return message;
    }
}
