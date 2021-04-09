package com.example.Cinema.mapper;


import com.example.Cinema.model.Genres;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface GenresMapper {
    @Select("select * from genres")
    List<Genres> findAll();

    @Select("select id " +
            "from genres " +
            "order by id DESC " +
            "limit 1")
    int getLastId();

    @Select("select id " +
            "from genres " +
            "where name like '%${genre}%' ")
    Integer getIdByName(String genre);
}
