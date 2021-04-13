package com.example.Cinema.mapper;

import com.example.Cinema.model.dbModel.CinemaDB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CinemaMapper {
    @Select("select * from cinema")
    List<CinemaDB> findAll();
}
