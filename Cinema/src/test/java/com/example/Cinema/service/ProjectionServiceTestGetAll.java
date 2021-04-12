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


}
