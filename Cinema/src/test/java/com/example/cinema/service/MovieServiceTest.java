package com.example.cinema.service;

import com.example.cinema.exception.NoIdException;
import com.example.cinema.exception.TableEmptyException;
import com.example.cinema.exception.WrongGenreNameException;
import com.example.cinema.mapper.MoviesMapper;
import com.example.cinema.model.dbModel.MovieDB;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.requestModel.MovieReq;
import com.example.cinema.model.requestModel.MovieUpdateReq;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MoviesMapper moviesMapper;

    @Mock
    GenreService genreService;

    @InjectMocks
    MoviesService moviesService;

    private AutoCloseable closeable;

    @Before
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    void findTime() {
        ProjectionDB projection = new ProjectionDB();
        projection.setId(1);
        projection.setIdHall(1);
        projection.setIdMovie(1);
        when(moviesMapper.findTime(projection)).thenReturn(LocalTime.of(2, 0, 0));
        Assert.assertEquals(LocalTime.of(2, 0, 0), moviesService.findTime(projection));
    }

    @Test(expected = NoIdException.class)
    void findTimeNullException() {
        ProjectionDB projection = new ProjectionDB();
        projection.setId(1);
        projection.setIdHall(1);
        projection.setIdMovie(1);
        when(moviesMapper.findTime(projection)).thenReturn(null);
        moviesService.findTime(projection);
    }

    @Test
    void findAll() {
        List<MovieDB> list = new LinkedList<>();
        list.add(new MovieDB());
        list.add(new MovieDB());
        when(moviesMapper.findAll()).thenReturn(list);
        Assert.assertEquals(moviesService.findAll().size(), list.size());
    }

    @Test(expected = TableEmptyException.class)
    void findAllEmptyList() {
        when(moviesMapper.findAll()).thenReturn(null);
        moviesService.findAll();
    }

    @Test
    void insert() {
        MovieReq movieReq = new MovieReq();
        movieReq.setId(10);
        Assert.assertTrue(moviesService.insert(movieReq));
        verify(moviesMapper, times(1)).insert(any());
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
        Assert.assertEquals(moviesService.getByName("Titanic").size(), list.size());
    }

    @Test(expected = TableEmptyException.class)
    void getByNameEmptyList() {
        when(moviesMapper.getByName("Titanic")).thenReturn(null);
        moviesService.getByName("Titanic");
    }

    @Test
    void getByGenreRight() {
        List<MovieDB> list = new LinkedList<>();
        list.add(new MovieDB());
        when(genreService.getGenreIdByName("Titanic")).thenReturn(1);
        when(moviesMapper.getByGenre(1)).thenReturn(list);
        Assert.assertEquals(moviesService.getByGenre("Titanic").size(), list.size());
    }

    @Test(expected = WrongGenreNameException.class)
    void getByGenreException() {
        when(genreService.getGenreIdByName("Titanic")).thenReturn(null);
        moviesService.getByGenre("Titanic");
    }

    @Test(expected = TableEmptyException.class)
    void getByGenreEmptyList() {
        when(genreService.getGenreIdByName("Titanic")).thenReturn(1);
        when(moviesMapper.getByGenre(1)).thenReturn(null);
        moviesService.getByGenre("Titanic");
    }
}