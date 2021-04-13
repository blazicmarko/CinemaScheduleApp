package com.example.Cinema.resource;

import com.example.Cinema.mapper.HallsMapper;
import com.example.Cinema.model.dbModel.HallDB;
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
