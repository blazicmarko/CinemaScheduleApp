package com.example.Cinema.service;

import com.example.Cinema.exception.TableEmptyException;
import com.example.Cinema.mapper.ProjectionsMapper;
import com.example.Cinema.model.Filter;
import com.example.Cinema.model.ProjectionView;
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
public class ProjectionServiceTestFilter {
    @Autowired
    private ProjectionService service;

    @MockBean
    private ProjectionsMapper mapper;

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
}
