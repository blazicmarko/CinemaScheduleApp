package com.example.cinema.service;

import com.example.cinema.exception.NoIdException;
import com.example.cinema.exception.TableEmptyException;
import com.example.cinema.exception.WrongGenreNameException;
import com.example.cinema.mapper.MoviesMapper;
import com.example.cinema.model.dbModel.MovieDB;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.requestModel.MovieReq;
import com.example.cinema.model.requestModel.MovieUpdateReq;
import com.example.cinema.model.responseModel.MovieResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
public class MoviesService {
    public static Logger logger = LogManager.getLogger(MoviesService.class.getName());

    private Logger getLogger() {
        return logger;
    }

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
        LocalTime time = moviesMapper.findTime(projection);
        if (time == null) {
            getLogger().error("NoIdException thrown in getTime method. Movie with id:" + projection.getId() + " doesn't exists.");
            throw new NoIdException("There is no Movie with ID you inserted.");
        } else
            return time;
    }

    public List<MovieResponse> findAll() {
        List<MovieDB> list = moviesMapper.findAll();
        if (list == null) {
            getLogger().error("TableEmptyException thrown in findAll method.");
            throw new TableEmptyException("No movie with that name in table");
        } else
            return fromDBListToResponseList(list);
    }

    public boolean insert(MovieReq movieReq) {
        MovieDB movieDB = makeDBModel(movieReq);
        getLogger().debug("DB operation insert:");
        moviesMapper.insert(movieDB);
        InitService.setMovieLastId(moviesMapper.getLastId());
        InitService.setMovieNames(moviesMapper.getAllNames());
        return true;
    }

    public MovieDB makeDBModel(MovieReq movieReq) {
        MovieDB movieDB = new MovieDB();
        movieDB.setId(movieReq.getId());
        movieDB.setGrade(movieReq.getGrade());
        movieDB.setIdGenre(movieReq.getIdGenre());
        movieDB.setName(movieReq.getName());
        movieDB.setTime(movieReq.getTime());
        movieDB.setYear(movieReq.getYear());
        return movieDB;
    }

    public void update(MovieUpdateReq movie) {
        getLogger().debug("Checking data for update.");
        Map<String, String> vars = checkForUpdate(movie);
        getLogger().debug("DB operation update:");
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
        getLogger().debug("Data checked for update.");
        return vars;
    }


    public List<MovieResponse> getByName(String name) {
        List<MovieDB> list = moviesMapper.getByName(name);
        if (list == null) {
            getLogger().error("TableEmptyException thrown in getByName method.");
            throw new TableEmptyException("No movie with that name in table");
        } else
            return fromDBListToResponseList(list);
    }

    public List<MovieResponse> getByGenre(String genre) {
        Integer idGenre = genreService.getGenreIdByName(genre);
        if (idGenre == null) {
            getLogger().error("WrongGenreNameException thrown in getByGenre method. Genre with name " + genre + " doesn't exists");
            throw new WrongGenreNameException("There is no genre with that name!");
        } else {
            List<MovieDB> list = moviesMapper.getByGenre(idGenre);
            if (list == null) {
                getLogger().error("TableEmptyException thrown in getByGenre method.");
                throw new TableEmptyException("No movie with that name in table");
            } else {
                return fromDBListToResponseList(list);
            }
        }

    }

    public MovieResponse fromDBtoResponse(MovieDB movieDB) {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(movieDB.getId());
        movieResponse.setGrade(movieDB.getGrade());
        movieResponse.setIdGenre(movieDB.getIdGenre());
        movieResponse.setName(movieDB.getName());
        movieResponse.setTime(movieDB.getTime());
        movieResponse.setYear(movieDB.getYear());
        return movieResponse;
    }

    public List<MovieResponse> fromDBListToResponseList(List<MovieDB> list) {
        List<MovieResponse> responseList = new LinkedList<>();
        for (MovieDB movieDB : list) {
            responseList.add(fromDBtoResponse(movieDB));
        }
        return responseList;
    }
}
