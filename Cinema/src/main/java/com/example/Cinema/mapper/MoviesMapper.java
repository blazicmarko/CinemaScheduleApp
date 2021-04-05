package com.example.Cinema.mapper;

import com.example.Cinema.model.Movies;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface MoviesMapper {

    @Select("select * from movies")
    List<Movies> findAll();

    @Insert("insert into movies(id,name,year,grade,idGenre,time)"+
    "values (#{id}, #{name}, #{year}, #{grade}, #{idGenre}, #{time}")
    public Movies insert(Movies movie);
}
