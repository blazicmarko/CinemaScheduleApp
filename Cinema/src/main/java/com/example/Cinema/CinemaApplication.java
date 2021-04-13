package com.example.Cinema;

import com.example.Cinema.model.dbModel.MovieDB;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MappedTypes(MovieDB.class)
@MapperScan("com.example.Cinema.mapper")
@SpringBootApplication
public class CinemaApplication {


    public static void main(String[] args) {

        SpringApplication.run(CinemaApplication.class, args);
    }

}
