package com.example.Cinema.mapper;

import com.example.Cinema.model.Projections;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ProjectionsMapper {

    @Select("select * from projections")
    List<Projections> findAll();
}
