package com.example.Cinema.service;

import com.example.Cinema.mapper.HallsMapper;
import com.example.Cinema.model.dbModel.ProjectionDB;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallsService {

    private HallsMapper hallsMapper;

    public HallsService(HallsMapper hallsMapper) {
        this.hallsMapper = hallsMapper;
    }

    public Integer getLastId() {
        return InitService.getHallLastId();
    }

    public Integer findCinemaOfHall(ProjectionDB projectionDB) {
        return hallsMapper.findCinemaOfHall(projectionDB);
    }

    public List<Integer> findAllHalls(Integer idCinema) {
        return hallsMapper.findAllHalls(idCinema);
    }

    public String getName(Integer temp) {
        return hallsMapper.getName(temp);
    }
}
