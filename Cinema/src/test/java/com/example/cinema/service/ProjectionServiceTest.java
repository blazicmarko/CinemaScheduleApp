package com.example.cinema.service;

import com.example.cinema.exception.AppointmentCheckException;
import com.example.cinema.exception.NoIdException;
import com.example.cinema.exception.TableEmptyException;
import com.example.cinema.mapper.ProjectionsMapper;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.requestModel.FilterReq;
import com.example.cinema.model.requestModel.ProjectionReq;
import com.example.cinema.model.requestModel.ProjectionUpdateReq;
import com.example.cinema.model.responseModel.ProjectionViewResposne;
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

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectionServiceTest {
    @Autowired
    private ProjectionService service;

    @MockBean
    private ProjectionsMapper mapper;

    @MockBean
    private MoviesService moviesService;


    @Test
    public void getRightDataFilter() {
        FilterReq filterReq = new FilterReq();
        filterReq.setMovieName("Matrix");

        FilterReq filterReq2 = new FilterReq();
        filterReq2.setMovieName("Matrix");
        filterReq2.setDate(LocalDate.now());

        List<ProjectionViewResposne> list = new LinkedList<>();
        List<ProjectionViewResposne> list2 = new LinkedList<>();
        ProjectionViewResposne projectionViewResposne = new ProjectionViewResposne();
        projectionViewResposne.setMovieName("Matrix");
        projectionViewResposne.setHallName("Sala 1");
        list.add(projectionViewResposne);
        list2.add(projectionViewResposne);
        projectionViewResposne.setHallName("Sala 2");
        list.add(projectionViewResposne);
        projectionViewResposne.setHallName("Sala 3");
        list.add(projectionViewResposne);
        projectionViewResposne.setHallName("Sala 4");
        list.add(projectionViewResposne);
        list2.add(projectionViewResposne);


        when(mapper.getSelectedNoDate(filterReq)).thenReturn(list);

        when(mapper.getSelected(filterReq2)).thenReturn(list2);

        Assert.assertEquals(list, service.getSelected(filterReq));

        Assert.assertEquals(list2, service.getSelected(filterReq2));
    }

    @Test
    public void getEmptyListFilter() {
        FilterReq filterReq = new FilterReq();
        filterReq.setMovieName("blabla");
        when(mapper.getSelectedNoDate(filterReq)).thenReturn(new LinkedList<>());
    }

    @Test
    public void getAllNumOfProjections() {
        List<ProjectionDB> list = new LinkedList<>();
        ProjectionDB projection = new ProjectionDB();
        projection.setId(1);
        list.add(projection);
        projection.setId(2);
        list.add(projection);

        when(mapper.findAll()).thenReturn(list);

        Assert.assertEquals(2, service.getAll().size());
        verify(mapper, times(1)).findAll();
    }

    @Test()
    public void getAllReturningRightData() {
        List<ProjectionDB> list = new LinkedList<>();
        ProjectionDB projection = new ProjectionDB();
        projection.setId(1);
        list.add(projection);
        projection.setId(2);
        list.add(projection);

        when(mapper.findAll()).thenReturn(list);

        Assert.assertEquals(list.size(), service.getAll().size());
        verify(mapper, times(1)).findAll();
    }

    @Test(expected = TableEmptyException.class)
    public void getAllReturnEmpty() {
        when(mapper.findAll()).thenReturn(new LinkedList<>());
        service.getAll();
    }

    @Test
    public void insertReturningRightData() {
        ProjectionReq projectionReq = new ProjectionReq();
        projectionReq.setId(2);

        when(mapper.isFreeToInsert(any())).thenReturn(0);
        Assert.assertEquals(service.insert(projectionReq).getId(), projectionReq.getId());
        verify(mapper, times(1)).insert(any());
    }

    @Test(expected = AppointmentCheckException.class)
    public void insertThrowAppointmentCheck() {
        ProjectionReq projectionReq = new ProjectionReq();
        projectionReq.setId(2);

        when(mapper.isFreeToInsert(any())).thenReturn(1);
        service.insert(projectionReq);

    }

    @Test
    public void updateReturningRightData() {
        ProjectionDB oldProjectionReq = new ProjectionDB();
        oldProjectionReq.setId(1);
        oldProjectionReq.setIdMovie(1);
        oldProjectionReq.setIdHall(1);
        oldProjectionReq.setDate(LocalDate.of(2021, 7, 13));
        oldProjectionReq.setStartTime(LocalTime.of(20, 0, 0));
        oldProjectionReq.setEndTime(LocalTime.of(22, 30, 0));
        ProjectionReq newProjectionReq = new ProjectionReq();
        newProjectionReq.setId(1);
        newProjectionReq.setIdMovie(2);
        newProjectionReq.setIdHall(2);
        newProjectionReq.setDate(LocalDate.of(2021, 7, 14));
        newProjectionReq.setStartTime(LocalTime.of(20, 10, 0));
        newProjectionReq.setEndTime(LocalTime.of(22, 40, 0));
        ProjectionUpdateReq projection = new ProjectionUpdateReq();
        projection.setId(1);
        projection.setIdMovie(2);
        projection.setIdHall(2);
        projection.setDate(LocalDate.of(2021, 7, 14));
        projection.setStartTime(LocalTime.of(20, 10, 0));
        projection.setEndTime(LocalTime.of(22, 40, 0));
        Map<String, String> map = new HashMap<>();
        map.put("date", "2021-07-14");
        map.put("startTime", "20:00:00");
        map.put("id_hall", "2");
        map.put("id_movie", "2");
        map.put("endTime", "22:30:00");

        when(mapper.findOne(oldProjectionReq.getId())).thenReturn(1);
        when(mapper.findSpecific(projection.getId())).thenReturn(oldProjectionReq);
        when(moviesService.findTime(oldProjectionReq)).thenReturn(LocalTime.of(2, 30, 0));
        when(mapper.getEndTime(oldProjectionReq, LocalTime.of(2, 30, 0))).thenReturn(LocalTime.of(22, 40, 0));
        when(mapper.isFreeToUpdate(oldProjectionReq)).thenReturn(0);
        when(mapper.update(map, projection.getId())).thenReturn(true);
        Assert.assertEquals(service.update(projection).getId(), projection.getId());
    }

    @Test(expected = NoIdException.class)
    public void updateReturnNoIdExceptionForNullId() {
        ProjectionUpdateReq projection = new ProjectionUpdateReq();
        service.update(projection);
    }

    @Test(expected = NoIdException.class)
    public void updateReturnNoIdExceptionForZeroId() {
        ProjectionUpdateReq projection = new ProjectionUpdateReq();
        projection.setId(0);
        service.update(projection);
    }

    @Test(expected = AppointmentCheckException.class)
    public void updateReturnAppointmentCheckException() {
        ProjectionDB oldProjectionReq = new ProjectionDB();
        oldProjectionReq.setId(1);
        oldProjectionReq.setIdMovie(1);
        oldProjectionReq.setIdHall(1);
        oldProjectionReq.setDate(LocalDate.of(2021, 7, 13));
        oldProjectionReq.setStartTime(LocalTime.of(20, 0, 0));
        oldProjectionReq.setEndTime(LocalTime.of(22, 30, 0));
        ProjectionUpdateReq projection = new ProjectionUpdateReq();
        projection.setId(1);
        projection.setIdMovie(2);
        projection.setIdHall(2);
        projection.setDate(LocalDate.of(2021, 7, 14));
        projection.setStartTime(LocalTime.of(20, 10, 0));
        projection.setEndTime(LocalTime.of(22, 40, 0));

        when(mapper.findOne(oldProjectionReq.getId())).thenReturn(1);
        when(mapper.findSpecific(projection.getId())).thenReturn(oldProjectionReq);
        when(moviesService.findTime(oldProjectionReq)).thenReturn(LocalTime.of(2, 30, 0));
        when(mapper.getEndTime(oldProjectionReq, LocalTime.of(2, 30, 0))).thenReturn(LocalTime.of(22, 40, 0));
        when(mapper.isFreeToUpdate(oldProjectionReq)).thenReturn(1);
        service.update(projection);
    }

}
