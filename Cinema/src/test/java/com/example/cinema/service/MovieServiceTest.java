package com.example.cinema.service;

import com.example.cinema.exception.WrongGenreNameException;
import com.example.cinema.mapper.MoviesMapper;
import com.example.cinema.model.dbModel.MovieDB;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.requestModel.MovieUpdateReq;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MovieServiceTest {

    @Autowired
    MoviesService moviesService;

    @MockBean
    MoviesMapper moviesMapper;

    @MockBean
    GenreService genreService;

    @Test
    void findTime() {
        ProjectionDB projection = new ProjectionDB();
        projection.setId(1);
        projection.setIdHall(1);
        projection.setIdMovie(1);
        when(moviesMapper.findTime(projection)).thenReturn(LocalTime.of(2, 0, 0));
        Assert.assertEquals(LocalTime.of(2, 0, 0), moviesService.findTime(projection));
    }

    @Test
    void findAll() {
        List<MovieDB> list = new LinkedList<>();
        list.add(new MovieDB());
        list.add(new MovieDB());
        when(moviesMapper.findAll()).thenReturn(list);
        Assert.assertEquals(moviesService.findAll(), list);
    }

    @Test
    void insert() {
        MovieDB movie = new MovieDB();
        doNothing().when(moviesMapper).insert(movie);
        moviesService.insert(movie);
        verify(moviesMapper, times(1)).insert(movie);
    }

    @Test
    void update() {
        MovieUpdateReq movie = new MovieUpdateReq();
        movie.setId(1);
        movie.setGrade(7.2);
        movie.setIdGenre(1);
        Map<String, String> map = new HashMap<>();
        map.put("grade", "7.2");
        map.put("id_genre", "1");
        doNothing().when(moviesMapper).update(map, 1);
        moviesService.update(movie);
        verify(moviesMapper, times(1)).update(map, 1);
    }

    @Test
    void getByName() {
        List<MovieDB> list = new LinkedList<>();
        list.add(new MovieDB());
        when(moviesMapper.getByName("Titanic")).thenReturn(list);
        Assert.assertEquals(moviesService.getByName("Titanic"), list);
    }

    @Test
    void getByGenreRight() {
        List<MovieDB> list = new LinkedList<>();
        list.add(new MovieDB());
        when(genreService.getGenreIdByName("Titanic")).thenReturn(1);
        when(moviesMapper.getByGenre(1)).thenReturn(list);
        Assert.assertEquals(moviesService.getByGenre("Titanic"), list);
    }

    @Test
    void getByGenreException() {
        List<MovieDB> list = new LinkedList<>();
        list.add(new MovieDB());
        when(genreService.getGenreIdByName("Titanic")).thenReturn(null);
        when(moviesMapper.getByGenre(1)).thenReturn(list);
        Assert.assertThrows(WrongGenreNameException.class, () -> moviesService.getByGenre("Titanic"));
    }
}