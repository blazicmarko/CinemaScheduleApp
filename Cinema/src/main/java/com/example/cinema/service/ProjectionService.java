package com.example.cinema.service;

import com.example.cinema.exception.AppointmentCheckException;
import com.example.cinema.exception.NoIdException;
import com.example.cinema.exception.TableEmptyException;
import com.example.cinema.mapper.ProjectionsMapper;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.requestModel.FilterReq;
import com.example.cinema.model.requestModel.ProjectionReq;
import com.example.cinema.model.requestModel.ProjectionUpdateReq;
import com.example.cinema.model.responseModel.ProjectionRespone;
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

    public ProjectionRespone insert(ProjectionReq projectionReq) {
        ProjectionDB projectionDB = makeDBModel(projectionReq);
        LocalTime movieTime = moviesService.findTime(projectionDB);
        LocalTime time = projectionsMapper.getEndTime(projectionDB, movieTime);
        projectionDB.setEndTime(time);
        Integer numOfProjections;
        numOfProjections = projectionsMapper.isFreeToInsert(projectionDB);
        if (numOfProjections == 0) {
            projectionsMapper.insert(projectionDB);
            return fromDBtoResponse(makeDBModel(projectionReq));
        } else {
            String message = getListOfFreeHalls(projectionDB);
            throw new AppointmentCheckException(message);
        }
    }

    private ProjectionDB makeDBModel(ProjectionReq projectionReq) {
        ProjectionDB projectionDB = new ProjectionDB();
        projectionDB.setId(projectionReq.getId());
        projectionDB.setIdHall(projectionReq.getIdHall());
        projectionDB.setIdMovie(projectionReq.getIdMovie());
        projectionDB.setStartTime(projectionReq.getStartTime());
        projectionDB.setDate(projectionReq.getDate());
        return projectionDB;
    }

    public List<ProjectionRespone> getAll() {
        List<ProjectionDB> list = projectionsMapper.findAll();
        if (list.isEmpty()) {
            throw new TableEmptyException("Table projections is empty");
        } else
            return fromDBListToResponseList(list);
    }

    public List<ProjectionRespone> getFirst() {
        List<ProjectionDB> list = projectionsMapper.findFirst();
        if (list.isEmpty()) {
            throw new TableEmptyException("Table projections is empty");
        } else
            return fromDBListToResponseList(list);
    }

    public boolean update(ProjectionUpdateReq projection) {
        Map<String, String> updateVars = checkForUpdate(projection);
        projectionsMapper.update(updateVars, projection.getId());
        return true;
    }

    public Map<String, String> checkForUpdate(ProjectionUpdateReq projection) {
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
        if (projectionsMapper.isFreeToUpdate(oldProjectionDB) == 0) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            updateVars.put("endTime", oldProjectionDB.getEndTime().format(dtf));
            return updateVars;
        } else {
            String message = getListOfFreeHalls(oldProjectionDB);
            throw new AppointmentCheckException(message);
        }

    }

    public List<ProjectionViewResposne> getSelected(FilterReq filterReq) {
        List<ProjectionViewResposne> list;
        if (filterReq.getDate() != null) {
            list = projectionsMapper.getSelected(filterReq);
        } else {
            list = projectionsMapper.getSelectedNoDate(filterReq);
        }
        if (list.isEmpty()) {
            throw new TableEmptyException("Table projections is empty");
        } else
            return list;
    }

    public String getListOfFreeHalls(ProjectionDB projectionDB) {
        Integer idCinema = hallsService.findCinemaOfHall(projectionDB);
        List<Integer> listIdHalls;
        List<String> halls = new LinkedList<>();
        listIdHalls = hallsService.findAllHalls(idCinema);
        listIdHalls.forEach((temp) -> {
            projectionDB.setIdHall(temp);
            if (projectionsMapper.isFreeToInsert(projectionDB) == 0) {
                halls.add(hallsService.getName(temp));
            }
        });
        String message = "This appointment is full. Choose available halls :";
        for (String hall : halls) {
            message = message.concat(hall + " ");
        }
        return message;
    }

    public ProjectionRespone fromDBtoResponse(ProjectionDB projectionDB) {
        ProjectionRespone projectionRespone = new ProjectionRespone();
        projectionRespone.setId(projectionDB.getId());
        projectionRespone.setDate(projectionDB.getDate());
        projectionRespone.setEndTime(projectionDB.getEndTime());
        projectionRespone.setIdHall(projectionDB.getIdHall());
        projectionRespone.setIdMovie(projectionDB.getIdMovie());
        projectionRespone.setStartTime(projectionDB.getStartTime());
        return projectionRespone;
    }

    public List<ProjectionRespone> fromDBListToResponseList(List<ProjectionDB> list) {
        List<ProjectionRespone> responseList = new LinkedList<>();
        for (ProjectionDB projectionDB : list) {
            responseList.add(fromDBtoResponse(projectionDB));
        }
        return responseList;
    }
}
