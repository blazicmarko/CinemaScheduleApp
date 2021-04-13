package com.example.cinema;

import com.example.cinema.model.dbModel.MovieDB;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MappedTypes(MovieDB.class)
@MapperScan("com.example.Cinema.mapper")
@SpringBootApplication
@EnableSwagger2
public class CinemaApplication {


    public static void main(String[] args) {

        SpringApplication.run(CinemaApplication.class, args);
    }

}
