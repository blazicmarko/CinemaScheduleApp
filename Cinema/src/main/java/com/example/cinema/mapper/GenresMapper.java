package com.example.cinema.mapper;


import com.example.cinema.model.dbModel.GenreDB;
import com.example.cinema.queryStrings.GenreQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface GenresMapper {
    @Select(GenreQuery.FIND_ALL)
    List<GenreDB> findAll();

    @Select(GenreQuery.GET_LAST_ID)
    int getLastId();

    @Select(GenreQuery.GET_ID_BY_NAME)
    Integer getIdByName(String genre);
}
