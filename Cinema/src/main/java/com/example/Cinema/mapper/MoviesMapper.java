package com.example.Cinema.mapper;

import com.example.Cinema.model.dbModel.MovieDB;
import com.example.Cinema.model.dbModel.ProjectionDB;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface MoviesMapper {

    @Select("select * from movies")
    List<MovieDB> findAll();

    @Insert("insert into movies(name,year,grade,id_genre,time)" +
            "values (#{name}, #{year}, #{grade}, #{idGenre}, #{time})")
    void insert(MovieDB movieDB);

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
    LocalTime findTime(ProjectionDB projectionDB);

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
    List<MovieDB> getByName(String name);

    @Select("select * " +
            "from movies " +
            "where id_genre = #{idGenre} ")
    List<MovieDB> getByGenre(int idGenre);

    @Select("select name " +
            "from movies")
    List<String> getAllNames();
}
