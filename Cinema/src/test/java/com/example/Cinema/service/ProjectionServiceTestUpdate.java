package com.example.Cinema.service;

import com.example.Cinema.exception.AppointmentCheckException;
import com.example.Cinema.exception.NoIdException;
import com.example.Cinema.mapper.ProjectionsMapper;
import com.example.Cinema.model.Projections;
import com.example.Cinema.model.ProjectionsUpdate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectionServiceTestUpdate {

    @Autowired
    private ProjectionService service;

    @MockBean
    private ProjectionsMapper mapper;

    @MockBean
    private MoviesService serviceMovie;

    @Test
    public void UpdateReturningRightData(){
        Projections oldProjection = new Projections(1,1,1, LocalDate.of(2021,7,13),
                LocalTime.of(20,0,0),LocalTime.of(22,30,0));
        Projections newProjection = new Projections(1,2,2, LocalDate.of(2021,7,14),
                LocalTime.of(20,10,0),LocalTime.of(22,40,0));
        ProjectionsUpdate projection = new ProjectionsUpdate(1,2,2,LocalDate.of(2021,7,14),LocalTime.of(20,10,0));
        Map<String,String> map = new HashMap<>();
        map.put("date", "2021-07-14");
        map.put("startTime", "20:00:00");
        map.put("id_hall", "2");
        map.put("id_movie", "2");
        map.put("endTime", "22:30:00");

        when(mapper.findOne(oldProjection.getId())).thenReturn(1);
        when(mapper.findSpecific(projection.getId())).thenReturn(oldProjection);
        when(serviceMovie.findTime(oldProjection)).thenReturn(LocalTime.of(2,30,0));
        when(mapper.getEndTime(oldProjection,LocalTime.of(2,30,0))).thenReturn(LocalTime.of(22,40,0));
        when(mapper.isFreeToUpdate(oldProjection)).thenReturn(0);
        when(mapper.update(map,projection.getId())).thenReturn(true);
        Assert.assertEquals(newProjection.getId(),service.update(projection).getId());
        Assert.assertEquals(newProjection.getDate(),service.update(projection).getDate());
        Assert.assertEquals(newProjection.getIdHall(),service.update(projection).getIdHall());
        Assert.assertEquals(newProjection.getIdMovie(),service.update(projection).getIdMovie());
        Assert.assertEquals(newProjection.getStartTime(),service.update(projection).getStartTime());
        Assert.assertEquals(newProjection.getEndTime(),service.update(projection).getEndTime());
    }

    @Test
    public void UpdateReturnNoIdException(){
        ProjectionsUpdate projection = new ProjectionsUpdate(null,2,2,LocalDate.of(2021,7,14),LocalTime.of(20,10,0));
        Assert.assertThrows(NoIdException.class, () -> service.update(projection));
    }

    @Test
    public void UpdateReturnAppointmentCheckException(){
        Projections oldProjection = new Projections(1,1,1, LocalDate.of(2021,7,13),
                LocalTime.of(20,0,0),LocalTime.of(22,30,0));
        ProjectionsUpdate projection = new ProjectionsUpdate(1,2,2,LocalDate.of(2021,7,14),LocalTime.of(20,10,0));

        when(mapper.findOne(oldProjection.getId())).thenReturn(1);
        when(mapper.findSpecific(projection.getId())).thenReturn(oldProjection);
        when(serviceMovie.findTime(oldProjection)).thenReturn(LocalTime.of(2,30,0));
        when(mapper.getEndTime(oldProjection,LocalTime.of(2,30,0))).thenReturn(LocalTime.of(22,40,0));
        when(mapper.isFreeToUpdate(oldProjection)).thenReturn(1);
        Assert.assertThrows(AppointmentCheckException.class, () -> service.update(projection));
    }

}
