package com.example.cinema.service;

import com.example.cinema.exception.AppointmentCheckException;
import com.example.cinema.exception.NoIdException;
import com.example.cinema.exception.TableEmptyException;
import com.example.cinema.mapper.ProjectionsMapper;
import com.example.cinema.model.dbModel.FilterDB;
import com.example.cinema.model.dbModel.ProjectionDB;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectionDBServiceTest {
    @Autowired
    private ProjectionService service;

    @MockBean
    private ProjectionsMapper mapper;

    @MockBean
    private MoviesService serviceMovie;

    @Test
    public void getRightDataFilter() {
        FilterDB filterDB = new FilterDB("Matrix", null);
        FilterDB filterDB2 = new FilterDB("Matrix", LocalDate.now());

        List<ProjectionViewResposne> list = Stream.of(
                new ProjectionViewResposne("Matrix", "Sala 1"),
                new ProjectionViewResposne("Matrix", "Sala 4"),
                new ProjectionViewResposne("Matrix", "Sala 3"),
                new ProjectionViewResposne("Matrix", "Sala 2")
        ).collect(Collectors.toCollection(LinkedList::new));

        List<ProjectionViewResposne> list2 = Stream.of(
                new ProjectionViewResposne("Matrix", "Sala 1"),
                new ProjectionViewResposne("Matrix", "Sala 4")
        ).collect(Collectors.toCollection(LinkedList::new));

        when(mapper.getSelectedNoDate(filterDB)).thenReturn(list);

        when(mapper.getSelected(filterDB2)).thenReturn(list2);

        Assert.assertEquals(list, service.getSelected(filterDB));

        Assert.assertEquals(list2, service.getSelected(filterDB2));
    }

    @Test
    public void getEmptyListFilter() {
        FilterDB filterDB = new FilterDB("blbalba", null);
        when(mapper.getSelectedNoDate(filterDB)).thenReturn(new LinkedList<>());
        Assert.assertThrows(TableEmptyException.class, () -> service.getSelected(filterDB));
    }

    @Test
    public void getAllNumOfProjections(){
        when(mapper.findAll()).thenReturn(Stream.of(
                new ProjectionDB(1, 1, 1),
                new ProjectionDB(2, 2, 2)
        ).collect(Collectors.toList()));

        Assert.assertEquals(2,service.getAll().size());
        verify(mapper,times(1)).findAll();
    }

    @Test()
    public void getAllReturningRightData() {
        List<ProjectionDB> list = Stream.of(
                new ProjectionDB(1, 1, 1),
                new ProjectionDB(2, 2, 2)
        ).collect(Collectors.toCollection(LinkedList::new));
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
        ProjectionDB projectionDB = new ProjectionDB(1, 1, 1, LocalDate.now(), LocalTime.now(), null);
        when(mapper.insert(projectionDB)).thenReturn(true);
        Assert.assertEquals(projectionDB, service.insert(projectionDB));
        verify(mapper, times(1)).insert(projectionDB);
    }

    @Test
    public void InsertThrowAppointmentCheck() {
        ProjectionDB projectionDB = new ProjectionDB(1, 1, 1, LocalDate.of(2021, 5, 8),
                LocalTime.of(20, 0, 0), null);
        when(mapper.isFreeToInsert(projectionDB)).thenReturn(1);
        Assert.assertThrows(AppointmentCheckException.class, () -> service.insert(projectionDB));

    }

    @Test
    public void UpdateReturningRightData() {
        ProjectionDB oldProjectionDB = new ProjectionDB(1, 1, 1, LocalDate.of(2021, 7, 13),
                LocalTime.of(20, 0, 0), LocalTime.of(22, 30, 0));
        ProjectionDB newProjectionDB = new ProjectionDB(1, 2, 2, LocalDate.of(2021, 7, 14),
                LocalTime.of(20, 10, 0), LocalTime.of(22, 40, 0));
        ProjectionUpdateReq projection = new ProjectionUpdateReq(1, 2, 2, LocalDate.of(2021, 7, 14), LocalTime.of(20, 10, 0));
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
        Assert.assertEquals(newProjectionDB.getId(), service.update(projection).getId());
        Assert.assertEquals(newProjectionDB.getDate(), service.update(projection).getDate());
        Assert.assertEquals(newProjectionDB.getIdHall(), service.update(projection).getIdHall());
        Assert.assertEquals(newProjectionDB.getIdMovie(), service.update(projection).getIdMovie());
        Assert.assertEquals(newProjectionDB.getStartTime(), service.update(projection).getStartTime());
        Assert.assertEquals(newProjectionDB.getEndTime(), service.update(projection).getEndTime());
    }

    @Test
    public void UpdateReturnNoIdException(){
        ProjectionUpdateReq projection = new ProjectionUpdateReq(null, 2, 2, LocalDate.of(2021, 7, 14), LocalTime.of(20, 10, 0));
        Assert.assertThrows(NoIdException.class, () -> service.update(projection));
    }

    @Test
    public void UpdateReturnAppointmentCheckException() {
        ProjectionDB oldProjectionDB = new ProjectionDB(1, 1, 1, LocalDate.of(2021, 7, 13),
                LocalTime.of(20, 0, 0), LocalTime.of(22, 30, 0));
        ProjectionUpdateReq projection = new ProjectionUpdateReq(1, 2, 2, LocalDate.of(2021, 7, 14), LocalTime.of(20, 10, 0));

        when(mapper.findOne(oldProjectionDB.getId())).thenReturn(1);
        when(mapper.findSpecific(projection.getId())).thenReturn(oldProjectionDB);
        when(serviceMovie.findTime(oldProjectionDB)).thenReturn(LocalTime.of(2, 30, 0));
        when(mapper.getEndTime(oldProjectionDB, LocalTime.of(2, 30, 0))).thenReturn(LocalTime.of(22, 40, 0));
        when(mapper.isFreeToUpdate(oldProjectionDB)).thenReturn(1);
        Assert.assertThrows(AppointmentCheckException.class, () -> service.update(projection));
    }

}
