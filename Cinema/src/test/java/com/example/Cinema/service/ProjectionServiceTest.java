package com.example.Cinema.service;

import com.example.Cinema.mapper.ProjectionsMapper;
import com.example.Cinema.model.Filter;
import com.example.Cinema.model.ProjectionView;
import com.example.Cinema.model.Projections;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
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

    @Test
    public void getProjections(){
        when(mapper.findAll()).thenReturn(Stream.of(
                new Projections(1,1,1),
                new Projections(2,2,2)
        ).collect(Collectors.toList()));
        Assert.assertEquals(2,service.getAll().size());
    }

    @Test
    public void getSelectedProjections(){
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
        Assert.assertEquals(list,service.getSelected(filter));
        when(mapper.getSelected(filter2)).thenReturn(list2);
        Assert.assertEquals(list2,service.getSelected(filter2));
        verify(mapper,times(1)).getSelectedNoDate(filter);
        verify(mapper,times(0)).getSelectedNoDate(filter2);
        verify(mapper,times(1)).getSelected(filter2);
        verify(mapper,times(0)).getSelected(filter);
    }
}
