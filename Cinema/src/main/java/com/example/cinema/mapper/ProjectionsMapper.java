package com.example.cinema.mapper;

import com.example.cinema.model.dbModel.FilterDB;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.responseModel.ProjectionViewResposne;
import com.example.cinema.queryStrings.ProjectionQuery;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface ProjectionsMapper {

    @Select(ProjectionQuery.FIND_ONE)
    int findOne(Integer id);

    @Select(ProjectionQuery.FIND_ALL)
    List<ProjectionDB> findAll();

    @Insert(ProjectionQuery.INSERT)
    Boolean insert(ProjectionDB projectionDB);


    @Update(ProjectionQuery.UPDATE)
    Boolean update(@Param("vars") Map<String, String> vars, @Param("id") Integer id);

    @Select(ProjectionQuery.GET_SELECTED)
    List<ProjectionViewResposne> getSelected(FilterDB filterDB);

    @Select(ProjectionQuery.GET_SELECTED_NO_DATE)
    List<ProjectionViewResposne> getSelectedNoDate(FilterDB filterDB);

    @Select(ProjectionQuery.IS_FREE_TO_ISNERT)
    Integer isFreeToInsert(ProjectionDB projectionDB);

    @Select(ProjectionQuery.IS_FREE_TO_UPDATE)
    Integer isFreeToUpdate(ProjectionDB projectionDB);

    @Select(ProjectionQuery.GET_END_TIME)
    LocalTime getEndTime(ProjectionDB projection, LocalTime time);

    @Select(ProjectionQuery.FIND_SPECIFIC)
    ProjectionDB findSpecific(Integer id);

    @Select(ProjectionQuery.FIND_FIRST)
    List<ProjectionDB> findFirst();
}
