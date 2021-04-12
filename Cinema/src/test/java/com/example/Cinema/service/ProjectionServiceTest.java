package com.example.Cinema.service;

import com.example.Cinema.exception.AppointmentCheckException;
import com.example.Cinema.exception.NoIdException;
import com.example.Cinema.exception.TableEmptyException;
import com.example.Cinema.mapper.ProjectionsMapper;
import com.example.Cinema.model.*;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectionServiceTest {
    @Autowired
    private ProjectionService service;

    @MockBean
    private ProjectionsMapper mapper;

    @MockBean
    private MoviesService serviceMovie;

    @Test
    public void getRightDataFilter(){
        Filter filter = new Filter("Matrix", null);
        Filter filter2 = new Filter("Matrix", LocalDate.now());

        List<ProjectionView> list = Stream.of(
                new ProjectionView("Matrix", "Sala 1"),
                new ProjectionView("Matrix", "Sala 4"),
                new ProjectionView("Matrix", "Sala 3"),
                new ProjectionView("Matrix", "Sala 2")
        ).collect(Collectors.toCollection(LinkedList::new));

        List<ProjectionView> list2 = Stream.of(
                new ProjectionView("Matrix", "Sala 1"),
                new ProjectionView("Matrix", "Sala 4")
        ).collect(Collectors.toCollection(LinkedList::new));

        when(mapper.getSelectedNoDate(filter)).thenReturn(list);

        when(mapper.getSelected(filter2)).thenReturn(list2);

        Assert.assertEquals(list,service.getSelected(filter));

        Assert.assertEquals(list2,service.getSelected(filter2));
    }

    @Test
    public void getEmptyListFilter(){
        Filter filter = new Filter("blbalba", null);
        when(mapper.getSelectedNoDate(filter)).thenReturn(new LinkedList<>());
        Assert.assertThrows(TableEmptyException.class, () ->service.getSelected(filter));
    }

    @Test
    public void getAllNumOfProjections(){
        when(mapper.findAll()).thenReturn(Stream.of(
                new Projections(1,1,1),
                new Projections(2,2,2)
        ).collect(Collectors.toList()));

        Assert.assertEquals(2,service.getAll().size());
        verify(mapper,times(1)).findAll();
    }

    @Test()
    public void getAllReturningRightData(){
        List<Projections> list = Stream.of(
                new Projections(1,1,1),
                new Projections(2,2,2)
        ).collect(Collectors.toCollection(LinkedList::new));
        when(mapper.findAll()).thenReturn(list);

        Assert.assertEquals(list,service.getAll());
        verify(mapper,times(1)).findAll();
    }

    @Test
    public void getAllReturnEmpty(){
        when(mapper.findAll()).thenReturn(new LinkedList<>());
        Assert.assertThrows(TableEmptyException.class,() -> service.getAll());
    }

    @Test
    public void InsertReturningRightData(){
        Projections projection = new Projections(1,1,1, LocalDate.now(), LocalTime.now(),null);
        when(mapper.insert(projection)).thenReturn(true);
        Assert.assertEquals(projection,service.insert(projection));
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
