package com.example.Cinema.resource;

import com.example.Cinema.mapper.CinemaMapper;
import com.example.Cinema.model.Cinema;
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
    public List<Cinema> getAll(){
        return cinemaMapper.findAll();
    }
}
