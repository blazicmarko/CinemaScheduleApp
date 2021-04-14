package com.example.cinema.resource;

import com.example.cinema.model.responseModel.HallResponse;
import com.example.cinema.service.HallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/halls")
public class HallsResource {
    private HallsService hallsService;


    @Autowired
    public HallsResource(HallsService hallsService) {
        this.hallsService = hallsService;
    }

    @GetMapping("/all")
    public List<HallResponse> getAll() {
        return hallsService.findAll();
    }
}
