package com.example.cinema.queryStrings;

public class HallQuery {

    public static final String FIND_ALL = "select id,name,id_cinema as idCinema from halls";

    public static final String FIND_HALL = "select count(*) from halls where id = #{idHall}";

    public static final String GET_LAST_ID = "select id " +
            "from halls " +
            "order by id DESC " +
            "limit 1";

    public static final String FIND_CINEMA_OF_HALLS = "select id_cinema from halls where id = #{idHall}";

    public static final String FIND_ALL_HALLS = "select id from halls where id_cinema = #{idCinema}";

    public static final String GET_NAME = "select name from halls where id = #{temp}";
}
