package com.example.cinema.queryStrings;



public class GenreQuery {
    public static final String FIND_ALL = "select * from genres";

    public static final String GET_LAST_ID = "select id " +
            "from genres " +
            "order by id DESC " +
            "limit 1";

    public static final String GET_ID_BY_NAME = "select id " +
            "from genres " +
            "where name like '%${genre}%' ";
}
