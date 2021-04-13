package com.example.cinema.service;

import com.example.cinema.exception.AppointmentCheckException;
import com.example.cinema.exception.NoIdException;
import com.example.cinema.exception.TableEmptyException;
import com.example.cinema.mapper.ProjectionsMapper;
import com.example.cinema.model.dbModel.FilterDB;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.requestModel.ProjectionUpdateReq;
import com.example.cinema.model.responseModel.ProjectionViewResposne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ProjectionDB insert(ProjectionDB projectionDB) {
        LocalTime movieTime = moviesService.findTime(projectionDB);
        LocalTime time = projectionsMapper.getEndTime(projectionDB, movieTime);
        projectionDB.setEndTime(time);
        Integer numOfProjections;
        numOfProjections = projectionsMapper.isFreeToInsert(projectionDB);
        if (numOfProjections == 0) {
            projectionsMapper.insert(projectionDB);
            return projectionDB;
        } else {
            String message = getListOfFreeHalls(projectionDB);
            throw new AppointmentCheckException(message);
        }
    }

    public List<ProjectionDB> getAll() {
        List<ProjectionDB> list = projectionsMapper.findAll();
        if (list.isEmpty()) {
            throw new TableEmptyException("Table projections is empty");
        } else
            return list;
    }

    public ProjectionDB update(ProjectionUpdateReq projection) {
        Map<String, String> updateVars = new HashMap<>();
        ProjectionDB oldProjectionDB;
        if (projection.getId() != null) {
            if (projectionsMapper.findOne(projection.getId()) == 0) {
                throw new NoIdException("There is no inserted that id in table projections");
            }
            oldProjectionDB = projectionsMapper.findSpecific(projection.getId());
        } else {
            throw new NoIdException("There is no inserted id in your data of projections.");
        }
        if (projection.getDate() != null) {
            updateVars.put("date", projection.getDate().toString());
            oldProjectionDB.setDate(projection.getDate());
        }
        if (projection.getStartTime() != null) {
            updateVars.put("startTime", projection.getStartTime().toString());
            oldProjectionDB.setStartTime(projection.getStartTime());
        }
        if (projection.getIdHall() != null) {
            updateVars.put("id_hall", projection.getIdHall().toString());
            oldProjectionDB.setIdHall(projection.getIdHall());
        }
        if (projection.getIdMovie() != null) {
            updateVars.put("id_movie", projection.getIdMovie().toString());
            oldProjectionDB.setIdMovie(projection.getIdMovie());
        }

        LocalTime movieTime = moviesService.findTime(oldProjectionDB);
        LocalTime time = projectionsMapper.getEndTime(oldProjectionDB, movieTime);
        oldProjectionDB.setEndTime(time);
        final Integer[] numOfProjections = new Integer[1];
        numOfProjections[0] = projectionsMapper.isFreeToUpdate(oldProjectionDB);
        if (numOfProjections[0] == 0) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            updateVars.put("endTime", oldProjectionDB.getEndTime().format(dtf));
            projectionsMapper.update(updateVars, projection.getId());
            return oldProjectionDB;
        } else {
            String message = getListOfFreeHalls(oldProjectionDB);
            throw new AppointmentCheckException(message);
        }
    }

    public List<ProjectionViewResposne> getSelected(FilterDB filterDB) {
        List<ProjectionViewResposne> list;
        if (filterDB.getDate() != null) {
            list = projectionsMapper.getSelected(filterDB);
        } else {
            list = projectionsMapper.getSelectedNoDate(filterDB);
        }
        if (list.isEmpty()) {
            throw new TableEmptyException("Table projections is empty");
        } else
            return list;
    }

    public String getListOfFreeHalls(ProjectionDB projectionDB) {
        final Integer[] numOfProjections = {1};
        Integer idCinema = hallsService.findCinemaOfHall(projectionDB);
        List<Integer> listIdHalls;
        List<String> halls = new LinkedList<>();
        listIdHalls = hallsService.findAllHalls(idCinema);
        listIdHalls.forEach((temp) -> {
            projectionDB.setIdHall(temp);
            numOfProjections[0] = projectionsMapper.isFreeToInsert(projectionDB);
            if (numOfProjections[0] == 0) {
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
