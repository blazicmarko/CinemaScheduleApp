package com.example.cinema.service;

import com.example.cinema.exception.AppointmentCheckException;
import com.example.cinema.exception.NoIdException;
import com.example.cinema.exception.TableEmptyException;
import com.example.cinema.mapper.ProjectionsMapper;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.kafkaModel.ProjectionKafka;
import com.example.cinema.model.requestModel.FilterReq;
import com.example.cinema.model.requestModel.ProjectionReq;
import com.example.cinema.model.requestModel.ProjectionUpdateReq;
import com.example.cinema.model.responseModel.ProjectionRespone;
import com.example.cinema.model.responseModel.ProjectionViewResposne;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    public static Logger logger = LogManager.getLogger(ProjectionService.class.getName());

    private Logger getLogger() {
        return logger;
    }

    private ProjectionsMapper projectionsMapper;
    private MoviesService moviesService;
    private HallsService hallsService;
    private ProjectionDB oldProjectionDB;

    public ProjectionDB getOldProjectionDB() {
        return oldProjectionDB;
    }

    public void setOldProjectionDB(ProjectionDB oldProjectionDB) {
        this.oldProjectionDB = oldProjectionDB;
    }

    @Autowired
    public ProjectionService(ProjectionsMapper projectionsMapper, HallsService hallsService, MoviesService moviesService) {
        this.projectionsMapper = projectionsMapper;
        this.moviesService = moviesService;
        this.hallsService = hallsService;
    }

    public ProjectionKafka insert(ProjectionReq projectionReq) {
        ProjectionDB projectionDB = makeDBModel(projectionReq);
        getLogger().debug("Seting endTime of projection.");
        LocalTime movieTime = moviesService.findTime(projectionDB);
        LocalTime time = projectionsMapper.getEndTime(projectionDB, movieTime);
        projectionDB.setEndTime(time);
        getLogger().debug("EndTime of projection set on " + time + " .");
        Integer numOfProjections;
        getLogger().debug("Checking availability of date and time for projection");
        numOfProjections = projectionsMapper.isFreeToInsert(projectionDB);
        if (numOfProjections == 0) {
            getLogger().debug("Check passed.");
            getLogger().debug("DB operation insert:");
            projectionsMapper.insert(projectionDB);
            return formDBtoKafka(projectionDB);
        } else {
            getLogger().error("AppointmentCheckException thrown for time :" + projectionDB.getStartTime().toString() +
                    " and date :" + projectionDB.getDate().toString() + ".");
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
        getLogger().debug("DB operation select all:");
        List<ProjectionDB> list = projectionsMapper.findAll();
        if (list.isEmpty()) {
            getLogger().error("TableEmptyException thrown in getAll method.");
            throw new TableEmptyException("Table projections is empty");
        } else
            return fromDBListToResponseList(list);
    }

    public ProjectionKafka update(ProjectionUpdateReq projection) {
        Map<String, String> updateVars = checkForUpdate(projection);
        getLogger().debug("DB operation update:");
        projectionsMapper.update(updateVars, projection.getId());
        return formDBtoKafka(getOldProjectionDB());
    }

    public Map<String, String> checkForUpdate(ProjectionUpdateReq projection) {
        Map<String, String> updateVars = new HashMap<>();
        ProjectionDB oldProjectionDB;
        getLogger().debug("Checking id of projection for update.");
        if (projection.getId() != null) {
            if (projectionsMapper.findOne(projection.getId()) == 0) {
                getLogger().error("NoIdException thrown in update method because there is no id=" + projection.getId() + " in table.");
                throw new NoIdException("There is no inserted that id in table projections");
            }
            oldProjectionDB = projectionsMapper.findSpecific(projection.getId());
        } else {
            getLogger().error("NoIdException thrown in update method because there is no id=" + projection.getId() + " in table.");
            throw new NoIdException("There is no inserted id in your data of projections.");
        }
        getLogger().debug("Check passed for update.");
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
        getLogger().debug("Seting endTime of projection(update).");
        LocalTime movieTime = moviesService.findTime(oldProjectionDB);
        LocalTime time = projectionsMapper.getEndTime(oldProjectionDB, movieTime);
        oldProjectionDB.setEndTime(time);
        getLogger().debug("EndTime of projection set on " + time + " (update).");
        getLogger().debug("Checking availability of date and time for projection(update)");
        if (projectionsMapper.isFreeToUpdate(oldProjectionDB) == 0) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            updateVars.put("endTime", oldProjectionDB.getEndTime().format(dtf));
            getLogger().debug("Check passed(update).");
            setOldProjectionDB(oldProjectionDB);
            return updateVars;
        } else {
            getLogger().error("AppointmentCheckException thrown for time :" + oldProjectionDB.getStartTime().toString() +
                    " and date :" + oldProjectionDB.getDate().toString() + ".");
            String message = getListOfFreeHalls(oldProjectionDB);
            throw new AppointmentCheckException(message);
        }

    }

    public List<ProjectionViewResposne> getSelected(FilterReq filterReq) {
        List<ProjectionViewResposne> list;
        if (filterReq.getDate() != null) {
            getLogger().debug("DB opreation select filter with date:");
            list = projectionsMapper.getSelected(filterReq);
        } else {
            getLogger().debug("DB opreation select filter without date:");
            list = projectionsMapper.getSelectedNoDate(filterReq);
        }
        if (list.isEmpty()) {
            getLogger().error("TableEmptyException thrown in filter method.");
            throw new TableEmptyException("Table projections is empty");
        } else
            return list;
    }

    public String getListOfFreeHalls(ProjectionDB projectionDB) {
        getLogger().debug("Getting list of free halls for AppointmentCheckException.");
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

    public ProjectionKafka formDBtoKafka(ProjectionDB projectionDB) {
        ProjectionKafka projectionKafka = new ProjectionKafka();
        if (projectionDB.getId() == null)
            projectionKafka.setId(projectionsMapper.getLastId());
        else
            projectionKafka.setId(projectionDB.getId());
        projectionKafka.setIdMovie(projectionDB.getIdMovie());
        projectionKafka.setIdHall(projectionDB.getIdHall());
        projectionKafka.setDate(projectionDB.getDate());
        projectionKafka.setStartTime(projectionDB.getStartTime());
        projectionKafka.setEndTime(projectionDB.getEndTime());
        return projectionKafka;
    }
}
