package com.example.cinema.resource;

import com.example.cinema.mapper.HallsMapper;
import com.example.cinema.model.dbModel.HallDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/halls")
public class HallsResource {
    private HallsMapper hallsMapper;


    @Autowired
    public HallsResource(HallsMapper hallsMapper) {
        this.hallsMapper = hallsMapper;
    }

    @GetMapping("/all")
    public List<HallDB> getAll() {
        return hallsMapper.findAll();
    }
}
