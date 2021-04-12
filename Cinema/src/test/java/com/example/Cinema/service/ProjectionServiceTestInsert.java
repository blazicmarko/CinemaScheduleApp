package com.example.Cinema.service;

import com.example.Cinema.exception.AppointmentCheckException;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectionServiceTestInsert {

    @Autowired
    private ProjectionService service;

    @MockBean
    private ProjectionsMapper mapper;

    @Test
    public void InsertReturningRightData(){
        Projections projection = new Projections(1,1,1, LocalDate.now(), LocalTime.now(),null);
        when(mapper.insert(projection)).thenReturn(true);
        try {
            Assert.assertEquals(projection,service.insert(projection));
        }
        catch (ConnectException e){

        }
        verify(mapper,times(1)).insert(projection);
    }

    @Test
    public void InsertThrowAppointmentCheck(){
        Projections projection = new Projections(1,1,1,LocalDate.of(2021, 5, 8),
                LocalTime.of(20,0,0),null);
        when(mapper.isFreeToInsert(projection)).thenReturn(1);
        Assert.assertThrows(AppointmentCheckException.class,() ->service.insert(projection));

    }

    @Test
    public void InsertExceptionForDate(){
        Projections projection = new Projections(1,1,1,LocalDate.of(2021, 1, 8),
                LocalTime.of(20,0,0),null);
        when(mapper.insert(projection)).thenThrow(new ValidationException("Wrong date"));
        Assert.assertThrows(ValidationException.class,() -> service.insert(projection));
        verify(mapper,times(1)).insert(projection);
    }

    @Test
    public void InsertExceptionForTime(){
        Projections projection = new Projections(1,1,1,LocalDate.of(2021, 5, 8),
                LocalTime.of(23,0,0),null);
        when(mapper.insert(projection)).thenThrow(new ValidationException("Wrong time"));
        Assert.assertThrows(ValidationException.class,() -> service.insert(projection));
        verify(mapper,times(1)).insert(projection);
    }

    @Test
    public void InsertExceptionForIdHall(){
        Projections projection = new Projections(1,1,0,LocalDate.of(2021, 5, 8),
                LocalTime.of(20,0,0),null);
        when(mapper.insert(projection)).thenThrow(new ValidationException("Wrong idHall"));
        Assert.assertThrows(ValidationException.class,() -> service.insert(projection));
        verify(mapper,times(1)).insert(projection);
    }

    @Test
    public void InsertExceptionForIdMovie(){
        Projections projection = new Projections(1,0,1,LocalDate.of(2021, 5, 8),
                LocalTime.of(20,0,0),null);
        when(mapper.insert(projection)).thenThrow(new ValidationException("Wrong idMovie"));
        Assert.assertThrows(ValidationException.class,() -> service.insert(projection));
        verify(mapper,times(1)).insert(projection);
    }
}
