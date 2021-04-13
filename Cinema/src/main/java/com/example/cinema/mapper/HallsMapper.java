package com.example.cinema.mapper;

import com.example.cinema.model.dbModel.HallDB;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.queryStrings.HallQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface HallsMapper {

    @Select(HallQuery.FIND_ALL)
    List<HallDB> findAll();

    @Select(HallQuery.FIND_HALL)
    Integer findHall(Integer idHall);

    @Select(HallQuery.GET_LAST_ID)
    Integer getLastId();

    @Select(HallQuery.FIND_CINEMA_OF_HALLS)
    Integer findCinemaOfHall(ProjectionDB oldProjectionDB);

    @Select(HallQuery.FIND_ALL_HALLS)
    List<Integer> findAllHalls(Integer idCinema);

    @Select(HallQuery.GET_NAME)
    String getName(Integer temp);
}
