package com.example.Cinema.service;

import com.example.Cinema.exception.TableEmptyException;
import com.example.Cinema.mapper.ProjectionsMapper;
import com.example.Cinema.model.Projections;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectionServiceTestGetAll {

    @Autowired
    private ProjectionService service;

    @MockBean
    private ProjectionsMapper mapper;

    @Test
    public void getAllNumOfProjections(){
        when(mapper.findAll()).thenReturn(Stream.of(
                new Projections(1,1,1),
                new Projections(2,2,2)
        ).collect(Collectors.toList()));

        Assert.assertEquals(2,service.getAll().size());
        verify(mapper,times(1)).findAll();
    }

    @Test
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

//    @Test
//    public void getSelectedProjections(){
//        Filter filter = new Filter("Matrix", null);
//        Filter filter2 = new Filter("Matrix", LocalDate.now());
//
//        List<ProjectionView> list = Stream.of(
//                new ProjectionView("Matrix", "Sala 1"),
//                new ProjectionView("Matrix", "Sala 4"),
//                new ProjectionView("Matrix", "Sala 3"),
//                new ProjectionView("Matrix", "Sala 2")
//        ).collect(Collectors.toCollection(LinkedList::new));
//
//        List<ProjectionView> list2 = Stream.of(
//                new ProjectionView("Matrix", "Sala 1"),
//                new ProjectionView("Matrix", "Sala 4")
//        ).collect(Collectors.toCollection(LinkedList::new));
//
//        when(mapper.getSelectedNoDate(filter)).thenReturn(list);
//
//        when(mapper.getSelected(filter2)).thenReturn(list2);
//
//        verify(mapper,times(1)).getSelectedNoDate(filter);
//
//        verify(mapper,times(0)).getSelectedNoDate(filter2);
//
//        verify(mapper,times(1)).getSelected(filter2);
//
//        verify(mapper,times(0)).getSelected(filter);
//
//        Assert.assertEquals(list,service.getSelected(filter));
//
//        Assert.assertEquals(list2,service.getSelected(filter2));
//
//
//    }
}
