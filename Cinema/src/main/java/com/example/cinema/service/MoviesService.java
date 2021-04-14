package com.example.cinema.service;

import com.example.cinema.exception.WrongGenreNameException;
import com.example.cinema.mapper.MoviesMapper;
import com.example.cinema.model.dbModel.MovieDB;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.requestModel.MovieUpdateReq;
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


    public LocalTime findTime(ProjectionDB projection) {
        return moviesMapper.findTime(projection);
    }

    public List<MovieDB> findAll() {
        return moviesMapper.findAll();
    }

    public void insert(MovieDB movieDB) {
        moviesMapper.insert(movieDB);
        InitService.setMovieLastId(moviesMapper.getLastId());
        InitService.setMovieNames(moviesMapper.getAllNames());
    }

    public void update(MovieUpdateReq movie) {
        Map<String, String> vars = checkForUpdate(movie);
        moviesMapper.update(vars, movie.getId());
        InitService.setMovieNames(moviesMapper.getAllNames());
    }

    private Map<String, String> checkForUpdate(MovieUpdateReq movie) {
        Map<String, String> vars = new HashMap<>();
        if (movie.getName() != null)
            vars.put("name", movie.getName());
        if (movie.getGrade() != null)
            vars.put("grade", movie.getGrade().toString());
        if (movie.getIdGenre() != null)
            vars.put("id_genre", movie.getIdGenre().toString());
        if (movie.getTime() != null)
            vars.put("time", movie.getTime().toString());
        if (movie.getYear() != null)
            vars.put("year", movie.getYear().toString());
        return vars;
    }


    public List<MovieDB> getByName(String name) {
        return moviesMapper.getByName(name);
    }

    public List<MovieDB> getByGenre(String genre) {
        Integer idGenre = genreService.getGenreIdByName(genre);
        if (idGenre == null) {
            throw new WrongGenreNameException("There is no genre with that name!");
        } else
            return moviesMapper.getByGenre(idGenre);
    }
}
