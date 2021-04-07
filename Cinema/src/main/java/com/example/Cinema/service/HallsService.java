package com.example.Cinema.service;

import com.example.Cinema.mapper.HallsMapper;
import org.springframework.stereotype.Service;

@Service
public class HallsService {

    private HallsMapper hallsMapper;

    public HallsService(HallsMapper hallsMapper) {
        this.hallsMapper = hallsMapper;
    }

    public Integer getLastId(){
        return hallsMapper.getLastId();
    }
}
