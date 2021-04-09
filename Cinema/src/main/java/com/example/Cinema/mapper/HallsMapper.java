package com.example.Cinema.mapper;

import com.example.Cinema.model.Halls;
import com.example.Cinema.model.Projections;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface HallsMapper {

    @Select("select * from halls")
    List<Halls> findAll();

    @Select("select count(*) from halls where id = #{idHall}")
    Integer findHall(Integer idHall);

    @Select("select id " +
            "from halls " +
            "order by id DESC " +
            "limit 1")
    Integer getLastId();

    @Select("select id_cinema from halls where id = #{idHall}")
    Integer findCinemaOfHall(Projections oldProjection);

    @Select("select id from halls where id_cinema = #{idCinema}")
    List<Integer> findAllHalls(Integer idCinema);

    @Select("select name from halls where id = #{temp}")
    String getName(Integer temp);
}
