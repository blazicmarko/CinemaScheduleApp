package com.example.Cinema.service;

import com.example.Cinema.mapper.MoviesMapper;
import com.example.Cinema.model.Projections;
import com.example.Cinema.model.ProjectionsUpdate;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class MoviesService {

    private MoviesMapper moviesMapper;

    public MoviesService(MoviesMapper moviesMapper) {
        this.moviesMapper = moviesMapper;
    }

    public LocalTime findTime(Projections projection) {
        return moviesMapper.findTime(projection);
    }

    public Integer getLastId() {
        return moviesMapper.getLastId();
    }
}
