package com.example.cinema.mapper;

import com.example.cinema.model.dbModel.MovieDB;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.queryStrings.MovieQuery;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface MoviesMapper {

    @Select(MovieQuery.FIND_ALL)
    List<MovieDB> findAll();

    @Insert(MovieQuery.INSERT)
    void insert(MovieDB movieDB);

    @Update(MovieQuery.UPDATE)
    void update(@Param("vars") Map<String,String> vars, @Param("id") Integer id);

    @Select(MovieQuery.FIND_TIME)
    LocalTime findTime(ProjectionDB projection);

    @Select(MovieQuery.FIND_MOVIE)
    Integer findMovie(Integer idMovie);

    @Select(MovieQuery.GET_LAST_ID)
    Integer getLastId();

    @Select(MovieQuery.CHECK_MOVIE_NAME)
    boolean checkMovieName(String value);

    @Select(MovieQuery.GET_BY_NAME)
    List<MovieDB> getByName(String name);

    @Select(MovieQuery.GET_BY_GENRE)
    List<MovieDB> getByGenre(int idGenre);

    @Select(MovieQuery.GET_ALL_NAMES)
    List<String> getAllNames();

    @Select(MovieQuery.CHECK_MOVIE)
    boolean checkForSameMovie(MovieDB movieDB);
}
