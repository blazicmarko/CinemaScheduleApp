package com.example.cinema;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@MapperScan("com.example.cinema.mapper")
@SpringBootApplication
public class CinemaApplication {


    public static void main(String[] args) {

        SpringApplication.run(CinemaApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("Cinema application for reserving projections") String appDesciption, @Value("1.0") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Cinema application")
                        .version(appVersion)
                        .description(appDesciption)
                        .contact(new Contact().email("mblazic96@gmail.com").name("Marko Blazic").url("https://github.com/blazicmarko"))
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
