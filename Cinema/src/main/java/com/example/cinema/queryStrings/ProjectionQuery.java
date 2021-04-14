package com.example.cinema.queryStrings;

public class ProjectionQuery {

    public static final String FIND_ONE = "select count(*) from projections where id = #{id}";

    public static final String FIND_ALL = "select id, startTime, endTime, date, id_movie as idMovie, id_hall as idHall from projections";

    public static final String INSERT = "insert into projections(date, startTime, endTime, id_hall, id_movie)" +
            "values (#{date}, #{startTime}, #{endTime}, #{idHall}, #{idMovie})";

    public static final String UPDATE = "<script> " +
            "update projections set " +
            "<foreach item = 'item' index = 'index' collection = 'vars' separator =','> " +
            "${index} = #{item} " +
            "</foreach> " +
            "where id = #{id} " +
            "</script>";

    public static final String GET_SELECTED = "select m.name as movieName , p.date as date , p.startTime as time, h.name as hallName " +
            "from projections as p " +
            "inner join movies as m on p.id_movie = m.id " +
            "inner join halls as h on p.id_hall = h.id " +
            "where m.name = #{movieName} and p.date = #{date}";

    public static final String GET_SELECTED_NO_DATE = "select m.name as movieName , p.date as date , p.startTime as time, h.name as hallName " +
            "from projections as p " +
            "inner join movies as m on p.id_movie = m.id " +
            "inner join halls as h on p.id_hall = h.id " +
            "where m.name = #{movieName}";

    public static final String IS_FREE_TO_ISNERT = "select count(*) " +
            "from projections as p " +
            "where p.date = #{date} " +
            "and p.id_hall = #{idHall} " +
            "and (p.startTime between #{startTime} and #{endTime} " +
            "or p.endTime between #{startTime} and #{endTime})";

    public static final String IS_FREE_TO_UPDATE = "select count(*) " +
            "from projections as p " +
            "where p.date = #{date} " +
            "and p.id_hall = #{idHall} " +
            "and p.id != #{id} " +
            "and (p.startTime between #{startTime} and #{endTime} " +
            "or p.endTime between #{startTime} and #{endTime})";

    public static final String GET_END_TIME = "select addtime(#{projection.startTime},#{time})";

    public static final String FIND_SPECIFIC = "select id, startTime, endTime, date, id_movie as idMovie, id_hall as idHall from projections " +
            "where id = #{id}";

    public static final String FIND_FIRST = "select id, startTime, endTime, date, id_movie as idMovie, id_hall as idHall from projections " +
            "where id = 1";
}
