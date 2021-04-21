package com.example.cinema.service;

import com.example.cinema.mapper.CinemaMapper;
import com.example.cinema.model.dbModel.CinemaDB;
import com.example.cinema.model.responseModel.CinemaResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CinemaService {

    private CinemaMapper cinemaMapper;

    public CinemaService(CinemaMapper cinemaMapper) {
        this.cinemaMapper = cinemaMapper;
    }

    public CinemaResponse fromDBtoResponse(CinemaDB cinemaDB) {
        CinemaResponse cinemaResponse = new CinemaResponse();
        cinemaResponse.setId(cinemaDB.getId());
        cinemaResponse.setName(cinemaDB.getName());
        cinemaResponse.setAddres(cinemaDB.getAddres());
        return cinemaResponse;
    }

    public List<CinemaResponse> findAll() {
        List<CinemaDB> listDB = cinemaMapper.findAll();
        List<CinemaResponse> listResponse = new LinkedList<>();
        for (CinemaDB cinema : listDB) {
            listResponse.add(fromDBtoResponse(cinema));
        }
        return listResponse;
    }
}
