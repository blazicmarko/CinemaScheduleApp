package com.example.Cinema.mapper;

import com.example.Cinema.model.Filter;
import com.example.Cinema.model.ProjectionView;
import com.example.Cinema.model.Projections;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface ProjectionsMapper {

    @Select("select count(*) from projections where id = #{id}")
    int findOne(Projections projection);

    @Select("select id, startTime, endTime, date, id_movie as idMovie, id_hall as idHall from projections")
    List<Projections> findAll();

    @Insert("insert into projections(date, startTime, endTime, id_hall, id_movie)"+
            "values (#{date}, #{startTime}, #{endTime}, #{idHall}, #{idMovie})")
    Boolean insert(Projections projection);


    @Update({
            "<script>",
            "update projections set",
            "<foreach item = 'item' index = 'index' collection = 'vars' separator =','>",
            "${index} = #{item}",
            "</foreach>",
            "where id = #{id}",
            "</script>"
    })
    Boolean update(@Param("vars")Map<String,String> vars, @Param("id")Integer id);

    @Select("select m.name as movieName , p.date as date , p.startTime as time, h.name as hallName " +
            "from projections as p " +
            "inner join movies as m on p.id_movie = m.id " +
            "inner join halls as h on p.id_hall = h.id " +
            "where m.name = #{movieName} and p.date = #{date}")
    List<ProjectionView> getSelected(Filter filter);

    @Select("select m.name as movieName , p.date as date , p.startTime as time, h.name as hallName " +
            "from projections as p " +
            "inner join movies as m on p.id_movie = m.id " +
            "inner join halls as h on p.id_hall = h.id " +
            "where m.name = #{movieName}")
    List<ProjectionView> getSelectedNoDate(Filter filter);

    @Select("select count(*) " +
            "from projections as p " +
            "where p.date = #{date} " +
            "and p.id_hall = #{idHall} " +
            "and (p.startTime between #{startTime} and #{endTime} " +
            "or p.endTime between #{startTime} and #{endTime})")
    Integer isFreeToInsert(Projections projection);

    @Select("select count(*) " +
            "from projections as p " +
            "where p.date = #{date} " +
            "and p.id_hall = #{idHall} " +
            "and p.id != #{id} " +
            "and (p.startTime between #{startTime} and #{endTime} " +
            "or p.endTime between #{startTime} and #{endTime})")
    Integer isFreeToUpdate(Projections projection);


    @Select("select addtime(#{projection.startTime},#{time})")
    LocalTime getEndTime(Projections projection, LocalTime time);

    @Select("select id, startTime, endTime, date, id_movie as idMovie, id_hall as idHall from projections " +
            "where id = #{id}")
    Projections findSpecific(Integer id);



}
