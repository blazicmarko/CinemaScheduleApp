package com.example.Cinema.mapper;

import com.example.Cinema.model.Movies;
import com.example.Cinema.model.Projections;
import com.example.Cinema.model.ProjectionsUpdate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
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
    LocalTime findTime(Projections projection);

    @Select("select count(*) from movies where id = #{idMovie}")
    Integer findMovie(Integer idMovie);

    @Select("select id " +
            "from movies " +
            "order by id DESC " +
            "limit 1")
    Integer getLastId();
}
