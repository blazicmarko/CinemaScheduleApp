package com.example.Cinema.mapper;

import com.example.Cinema.model.Halls;
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
}
