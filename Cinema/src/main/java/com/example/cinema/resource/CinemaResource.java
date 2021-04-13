package com.example.cinema.resource;

import com.example.cinema.mapper.CinemaMapper;
import com.example.cinema.model.dbModel.CinemaDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/cinema")
public class CinemaResource {
    private CinemaMapper cinemaMapper;


    @Autowired
    public CinemaResource(CinemaMapper cinemaMapper) {
        this.cinemaMapper = cinemaMapper;
    }

    @GetMapping("/all")
    public List<CinemaDB> getAll() {
        return cinemaMapper.findAll();
    }
}
