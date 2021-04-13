package com.example.Cinema.mapper;

import com.example.Cinema.model.dbModel.FilterDB;
import com.example.Cinema.model.dbModel.ProjectionDB;
import com.example.Cinema.model.responseModel.ProjectionViewResposne;
import com.example.Cinema.queryStrings.ProjectionQuery;
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
    LocalTime getEndTime(ProjectionDB projectionDB, LocalTime time);

    @Select(ProjectionQuery.FIND_SPECIFIC)
    ProjectionDB findSpecific(Integer id);


}
