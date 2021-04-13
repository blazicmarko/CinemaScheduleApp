package com.example.cinema.mapper;

import com.example.cinema.model.dbModel.CinemaDB;
import com.example.cinema.queryStrings.CinemaQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CinemaMapper {
    @Select(CinemaQuery.FIND_ALL)
    List<CinemaDB> findAll();
}
