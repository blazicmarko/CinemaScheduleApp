package com.example.Cinema.mapper;

import com.example.Cinema.model.Halls;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface HallsMapper {

    @Select("select * from halls")
    List<Halls> findAll();
}