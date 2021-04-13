package com.example.Cinema.service;

import com.example.Cinema.exception.WrongGenreNameException;
import com.example.Cinema.mapper.MoviesMapper;
import com.example.Cinema.model.Movies;
import com.example.Cinema.model.MoviesUpdate;
import com.example.Cinema.model.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MoviesService {

    private MoviesMapper moviesMapper;
    private GenreService genreService;
    private InitService initService;

    @Autowired
    public MoviesService(MoviesMapper moviesMapper, GenreService genreService, InitService initService) {
        this.moviesMapper = moviesMapper;
        this.genreService = genreService;
        this.initService = initService;
    }


    public LocalTime findTime(Projections projection) {
        return moviesMapper.findTime(projection);
    }

    public Integer getLastId() {
        return InitService.getMovieLastId();
    }

    public List<Movies> findAll() { return  moviesMapper.findAll();
    }

    public void insert(Movies movie) {
        moviesMapper.insert(movie);
        InitService.setMovieLastId(moviesMapper.getLastId());
        InitService.setMovieNames(moviesMapper.getAllNames());
    }

    public void update(MoviesUpdate movie) {
        Map<String, String> vars = new HashMap<>();
        if(movie.getName() != null)
            vars.put("name", movie.getName());
        if(movie.getGrade() != null)
            vars.put("grade", movie.getGrade().toString());
        if(movie.getIdGenre() != null)
            vars.put("id_genre", movie.getIdGenre().toString());
        if(movie.getTime() != null)
            vars.put("time", movie.getTime().toString());
        if(movie.getYear() != null)
            vars.put("year", movie.getYear().toString());
        moviesMapper.update(vars, movie.getId());
        InitService.setMovieNames(moviesMapper.getAllNames());
    }

    public boolean checkMovieName(String value) {
        List<String> list =InitService.getMovieNames();
        return !list.contains(value);
    }

    public List<Movies> getByName(String name) {
        return moviesMapper.getByName(name);
    }

    public List<Movies> getByGenre(String genre) {
        Integer idGenre = genreService.getGenreIdByName(genre);
        if(idGenre == null) {
            throw new WrongGenreNameException("There is no genre with that name!");
        }
        else
            return moviesMapper.getByGenre(idGenre);
    }
}
