package com.example.cinema.service;

import com.example.cinema.mapper.HallsMapper;
import com.example.cinema.model.dbModel.HallDB;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.responseModel.HallResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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

    public List<HallResponse> findAll() {
        List<HallDB> listDB = hallsMapper.findAll();
        List<HallResponse> responseList = new LinkedList<>();
        for (HallDB hallDB : listDB) {
            responseList.add(fromDBtoResponse(hallDB));
        }
        return responseList;
    }

    public HallResponse fromDBtoResponse(HallDB hallDB) {
        HallResponse hallResponse = new HallResponse();
        hallResponse.setId(hallDB.getId());
        hallResponse.setIdCinema(hallDB.getIdCinema());
        hallResponse.setName(hallDB.getName());
        return hallResponse;
    }
}
