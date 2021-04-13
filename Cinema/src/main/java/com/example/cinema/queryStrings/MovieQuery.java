package com.example.cinema.queryStrings;

public class MovieQuery {
    public static final String FIND_ALL = "select * from movies";

    public static final String INSERT = "insert into movies(name,year,grade,id_genre,time)" +
            "values (#{name}, #{year}, #{grade}, #{idGenre}, #{time})";

    public static final String UPDATE = "<script> " +
            "update movies set " +
            "<foreach item = 'item' index = 'index' collection = 'vars' separator =','> " +
            "${index} = #{item} " +
            "</foreach> " +
            "where id = #{id} " +
            "</script>";

    public static final String FIND_TIME = "select time from movies " +
            "where id = #{idMovie}";

    public static final String FIND_MOVIE = "select count(*) from movies where id = #{idMovie}";

    public static final String GET_LAST_ID = "select id " +
            "from movies " +
            "order by id DESC " +
            "limit 1";

    public static final String CHECK_MOVIE_NAME = "select count(*) " +
            "from movies " +
            "where name like #{value}";

    public static final String GET_BY_NAME = "select * " +
            "from movies " +
            "where name like '%${name}%' ";

    public static final String GET_BY_GENRE = "select * " +
            "from movies " +
            "where id_genre = #{idGenre} ";

    public static final String GET_ALL_NAMES = "select name " +
            "from movies";
}
