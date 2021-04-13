package com.example.cinema.service;

import com.example.cinema.mapper.GenresMapper;
import com.example.cinema.mapper.HallsMapper;
import com.example.cinema.mapper.MoviesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class InitService {

    static Integer genreLastId;
    static Integer hallLastId;
    static Integer movieLastId;
    static List<String> movieNames;

    public static Integer getGenreLastId() {
        return genreLastId;
    }

    public static void setGenreLastId(Integer genreLastId) {
        InitService.genreLastId = genreLastId;
    }

    public static Integer getHallLastId() {
        return hallLastId;
    }

    public static void setHallLastId(Integer hallLastId) {
        InitService.hallLastId = hallLastId;
    }

    public static Integer getMovieLastId() {
        return movieLastId;
    }

    public static void setMovieLastId(Integer movieLastId) {
        InitService.movieLastId = movieLastId;
    }

    public static List<String> getMovieNames() {
        return movieNames;
    }

    public static void setMovieNames(List<String> movieNames) {
        InitService.movieNames = movieNames;
    }

    private GenresMapper genresMapper;
    private HallsMapper hallsMapper;
    private MoviesMapper moviesMapper;

    @Autowired
    public InitService(GenresMapper genresMapper, HallsMapper hallsMapper, MoviesMapper moviesMapper) {
        this.genresMapper = genresMapper;
        this.hallsMapper = hallsMapper;
        this.moviesMapper = moviesMapper;
    }

    @PostConstruct
    private void postConstruct() {
        genreLastId = genresMapper.getLastId();
        hallLastId = hallsMapper.getLastId();
        movieLastId = moviesMapper.getLastId();
        movieNames = moviesMapper.getAllNames();
    }
}

