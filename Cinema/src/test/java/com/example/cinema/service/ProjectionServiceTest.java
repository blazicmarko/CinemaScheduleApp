package com.example.cinema.service;

import com.example.cinema.exception.AppointmentCheckException;
import com.example.cinema.exception.NoIdException;
import com.example.cinema.exception.TableEmptyException;
import com.example.cinema.mapper.ProjectionsMapper;
import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.requestModel.FilterReq;
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
    private MoviesService serviceMovie;

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
        Assert.assertThrows(TableEmptyException.class, () -> service.getSelected(filterReq));
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

        Assert.assertEquals(list, service.getAll());
        verify(mapper, times(1)).findAll();
    }

    @Test
    public void getAllReturnEmpty(){
        when(mapper.findAll()).thenReturn(new LinkedList<>());
        Assert.assertThrows(TableEmptyException.class,() -> service.getAll());
    }

    @Test
    public void InsertReturningRightData() {
        ProjectionDB projectionDB = new ProjectionDB();

        when(mapper.insert(projectionDB)).thenReturn(true);
        Assert.assertEquals(projectionDB, service.insert(projectionDB));
        verify(mapper, times(1)).insert(projectionDB);
    }

    @Test
    public void InsertThrowAppointmentCheck() {
        ProjectionDB projectionDB = new ProjectionDB();
        when(mapper.isFreeToInsert(projectionDB)).thenReturn(1);
        Assert.assertThrows(AppointmentCheckException.class, () -> service.insert(projectionDB));

    }

    @Test
    public void UpdateReturningRightData() {
        ProjectionDB oldProjectionDB = new ProjectionDB();
        oldProjectionDB.setId(1);
        oldProjectionDB.setIdMovie(1);
        oldProjectionDB.setIdHall(1);
        oldProjectionDB.setDate(LocalDate.of(2021, 7, 13));
        oldProjectionDB.setStartTime(LocalTime.of(20, 0, 0));
        oldProjectionDB.setEndTime(LocalTime.of(22, 30, 0));
        ProjectionDB newProjectionDB = new ProjectionDB();
        newProjectionDB.setId(1);
        newProjectionDB.setIdMovie(2);
        newProjectionDB.setIdHall(2);
        newProjectionDB.setDate(LocalDate.of(2021, 7, 14));
        newProjectionDB.setStartTime(LocalTime.of(20, 10, 0));
        newProjectionDB.setEndTime(LocalTime.of(22, 40, 0));
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

        when(mapper.findOne(oldProjectionDB.getId())).thenReturn(1);
        when(mapper.findSpecific(projection.getId())).thenReturn(oldProjectionDB);
        when(serviceMovie.findTime(oldProjectionDB)).thenReturn(LocalTime.of(2, 30, 0));
        when(mapper.getEndTime(oldProjectionDB, LocalTime.of(2, 30, 0))).thenReturn(LocalTime.of(22, 40, 0));
        when(mapper.isFreeToUpdate(oldProjectionDB)).thenReturn(0);
        when(mapper.update(map, projection.getId())).thenReturn(true);
        Assert.assertTrue(service.update(projection));
    }

    @Test
    public void UpdateReturnNoIdExceptionForNullId() {
        ProjectionUpdateReq projection = new ProjectionUpdateReq();
        Assert.assertThrows(NoIdException.class, () -> service.update(projection));
    }

    @Test
    public void UpdateReturnNoIdExceptionForZeroId() {
        ProjectionUpdateReq projection = new ProjectionUpdateReq();
        projection.setId(0);
        Assert.assertThrows(NoIdException.class, () -> service.update(projection));
    }

    @Test
    public void UpdateReturnAppointmentCheckException() {
        ProjectionDB oldProjectionDB = new ProjectionDB();
        oldProjectionDB.setId(1);
        oldProjectionDB.setIdMovie(1);
        oldProjectionDB.setIdHall(1);
        oldProjectionDB.setDate(LocalDate.of(2021, 7, 13));
        oldProjectionDB.setStartTime(LocalTime.of(20, 0, 0));
        oldProjectionDB.setEndTime(LocalTime.of(22, 30, 0));
        ProjectionUpdateReq projection = new ProjectionUpdateReq();
        projection.setId(1);
        projection.setIdMovie(2);
        projection.setIdHall(2);
        projection.setDate(LocalDate.of(2021, 7, 14));
        projection.setStartTime(LocalTime.of(20, 10, 0));
        projection.setEndTime(LocalTime.of(22, 40, 0));

        when(mapper.findOne(oldProjectionDB.getId())).thenReturn(1);
        when(mapper.findSpecific(projection.getId())).thenReturn(oldProjectionDB);
        when(serviceMovie.findTime(oldProjectionDB)).thenReturn(LocalTime.of(2, 30, 0));
        when(mapper.getEndTime(oldProjectionDB, LocalTime.of(2, 30, 0))).thenReturn(LocalTime.of(22, 40, 0));
        when(mapper.isFreeToUpdate(oldProjectionDB)).thenReturn(1);
        Assert.assertThrows(AppointmentCheckException.class, () -> service.update(projection));
    }

}
