package com.example.cinema.resource;

import com.example.cinema.model.responseModel.CinemaResponse;
import com.example.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/cinema")
public class CinemaResource {
    private CinemaService cinemaService;

    @Autowired
    public CinemaResource(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/all")
    public List<CinemaResponse> getAll() {
        return cinemaService.findAll();
    }
}
