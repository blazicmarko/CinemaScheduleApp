package com.example.cinema;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;


@MapperScan("com.example.cinema.mapper")
@SpringBootApplication
@EnableSwagger2
public class CinemaApplication {


    public static void main(String[] args) {

        SpringApplication.run(CinemaApplication.class, args);
    }

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.cinema.resource"))
                .build()
                .useDefaultResponseMessages(false)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Cinema reservations API",
                "Sample API for Cinema reservations",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Marko Blazic", "https://www.github.com/blazicmarko", "mblaizc96@gmail.com"),
                "API License",
                "http://javabrains.io",
                Collections.emptyList()
        );
    }
}
