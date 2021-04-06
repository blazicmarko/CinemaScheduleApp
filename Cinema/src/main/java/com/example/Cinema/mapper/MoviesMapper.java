package com.example.Cinema.mapper;

import com.example.Cinema.model.Movies;
import com.example.Cinema.model.Projections;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Time;
import java.util.List;

@Component
@Mapper
public interface MoviesMapper {

    @Select("select * from movies")
    List<Movies> findAll();

    @Insert("insert into movies(id,name,year,grade,id_genre,time)"+
            "values (#{id}, #{name}, #{year}, #{grade}, #{idGenre}, #{time})")
    void insert(Movies movie);

    @Update("update movies " +
            "set name = #{name}, year = #{year}, grade = #{grade}, id_genre = #{idGenre}, time = #{time} " +
            "where id = #{id}")
    void update(Movies movie);

    @Select("select time from movies " +
            "where id = #{idMovie}")
    Time findTime(Projections projection);
}
