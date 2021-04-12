package com.example.Cinema.resource;

import com.example.Cinema.mapper.ProjectionsMapper;
import com.example.Cinema.model.Projections;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
import java.net.ConnectException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectionResourceTest {
    @Autowired
    private ProjectionsResource resource;

    @MockBean
    private ProjectionsMapper service;

    @Test
    public void InsertExceptionForDate() throws ConnectException {
        Projections projection = new Projections(1,1,1, LocalDate.of(2021, 1, 8),
                LocalTime.of(20,0,0),null);
        when(resource.insert(projection)).thenThrow(new ValidationException("Wrong date"));
        Assert.assertThrows(ValidationException.class,() -> service.insert(projection));
        verify(service,times(1)).insert(projection);
    }

    @Test
    public void InsertExceptionForTime() throws ConnectException {
        Projections projection = new Projections(1,1,1,LocalDate.of(2021, 5, 8),
                LocalTime.of(23,0,0),null);
        when(resource.insert(projection)).thenThrow(new ValidationException("Wrong time"));
        Assert.assertThrows(ValidationException.class,() -> service.insert(projection));
        verify(service,times(1)).insert(projection);
    }

    @Test
    public void InsertExceptionForIdHall() throws ConnectException {
        Projections projection = new Projections(1,1,0,LocalDate.of(2021, 5, 8),
                LocalTime.of(20,0,0),null);
        when(resource.insert(projection)).thenThrow(new ValidationException("Wrong idHall"));
        Assert.assertThrows(ValidationException.class,() -> service.insert(projection));
        verify(service,times(1)).insert(projection);
    }

    @Test
    public void InsertExceptionForIdMovie() throws ConnectException {
        Projections projection = new Projections(1,0,1,LocalDate.of(2021, 5, 8),
                LocalTime.of(20,0,0),null);
        when(resource.insert(projection)).thenThrow(new ValidationException("Wrong idMovie"));
        Assert.assertThrows(ValidationException.class,() -> service.insert(projection));
        verify(service,times(1)).insert(projection);
    }
}
