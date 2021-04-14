package com.example.cinema.resource;

import com.example.cinema.model.requestModel.MovieReq;
import com.example.cinema.model.requestModel.MovieUpdateReq;
import com.example.cinema.model.responseModel.MovieResponse;
import com.example.cinema.service.MoviesService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MoviesResourceTest {

    @Autowired
    private MoviesResource resource;

    @MockBean
    private MoviesService service;

    @Test
    void getAllRightData() {
        List<MovieResponse> list = new LinkedList<>();
        list.add(new MovieResponse());
        when(service.findAll()).thenReturn(list);
        Assert.assertEquals(resource.getAll(), list);
    }

    @Test
    void getByNameRightData() {
        List<MovieResponse> list = new LinkedList<>();
        list.add(new MovieResponse());
        when(service.getByName("Matrix")).thenReturn(list);
        Assert.assertEquals(resource.getByName("Matrix"), list);
    }

    @Test
    void getByGenreRightData() {
        List<MovieResponse> list = new LinkedList<>();
        list.add(new MovieResponse());
        when(service.getByGenre("Crime")).thenReturn(list);
        Assert.assertEquals(resource.getByGenre("Crime"), list);
    }

    @Test
    void insertRightData() {
        MovieReq movie = new MovieReq();
        doNothing().when(service).insert(movie);
        Assert.assertEquals(resource.insert(movie).getStatusCode(), MoviesResource.handleInsertInMovies().getStatusCode());
        verify(service, times(1)).insert(movie);

    }

    @Test
    void updateRightData() {
        MovieUpdateReq movie = new MovieUpdateReq();
        doNothing().when(service).update(movie);
        Assert.assertEquals(resource.update(movie).getStatusCode(), MoviesResource.handleUpdateInMovies().getStatusCode());
        verify(service, times(1)).update(movie);
    }
}