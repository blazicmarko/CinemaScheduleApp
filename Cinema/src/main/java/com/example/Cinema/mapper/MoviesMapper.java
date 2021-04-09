package com.example.Cinema.mapper;

import com.example.Cinema.model.Movies;
import com.example.Cinema.model.Projections;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface MoviesMapper {

    @Select("select * from movies")
    List<Movies> findAll();

    @Insert("insert into movies(name,year,grade,id_genre,time)"+
            "values (#{name}, #{year}, #{grade}, #{idGenre}, #{time})")
    void insert(Movies movie);

    @Update({
            "<script>",
            "update movies set",
            "<foreach item = 'item' index = 'index' collection = 'vars' separator =','>",
            "${index} = #{item}",
            "</foreach>",
            "where id = #{id}",
            "</script>"
    })
    void update(@Param("vars") Map<String,String> vars, @Param("id") Integer id);

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

    @Select("select count(*) " +
            "from movies " +
            "where name like #{value}")
    boolean checkMovieName(String value);

    @Select("select * " +
            "from movies " +
            "where name like '%${name}%' ")
    List<Movies> getByName(String name);

    @Select("select * " +
            "from movies " +
            "where id_genre = #{idGenre} ")
    List<Movies> getByGenre(int idGenre);
}
