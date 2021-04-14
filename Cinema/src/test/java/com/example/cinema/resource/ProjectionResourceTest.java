package com.example.cinema.resource;

import com.example.cinema.model.dbModel.ProjectionDB;
import com.example.cinema.model.requestModel.FilterReq;
import com.example.cinema.model.requestModel.ProjectionUpdateReq;
import com.example.cinema.model.responseModel.ProjectionViewResposne;
import com.example.cinema.service.ProjectionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectionResourceTest {
    @Autowired
    private ProjectionsResource resource;

    @MockBean
    private ProjectionService service;

    @Autowired
    private MockMvc mvc;

    @Test
    public void InsertExceptionForInvalidDate() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/rest/projections/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"date\" :\"2021-04-11\",\n" +
                        "    \"startTime\" : \"20:40:00\", \n" +
                        "    \"idHall\" :1,\n" +
                        "    \"idMovie\" :3}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("date : must be a date in the present or in the future, "))
                .andReturn();
    }

    @Test
    public void InsertExceptionForInvalidTime() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/rest/projections/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"date\" :\"2060-04-11\",\n" +
                        "    \"startTime\" : \"23:40:00\", \n" +
                        "    \"idHall\" :1,\n" +
                        "    \"idMovie\" :3}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("startTime : {Invalid timestamp}, "))
                .andReturn();
    }

    @Test
    public void InsertExceptionForInvalidIdHall() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/rest/projections/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"date\" :\"2060-04-11\",\n" +
                        "    \"startTime\" : \"20:40:00\", \n" +
                        "    \"idHall\" :0,\n" +
                        "    \"idMovie\" :3}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("idHall : {Invalid idHall}, "))
                .andReturn();
    }

    @Test
    public void InsertExceptionForInvalidIdMovie() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/rest/projections/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"date\" :\"2060-04-11\",\n" +
                        "    \"startTime\" : \"20:40:00\", \n" +
                        "    \"idHall\" :1,\n" +
                        "    \"idMovie\" :0}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("idMovie : {Invalid idMovie}, "))
                .andReturn();
    }

    @Test
    public void InsertExceptionForNullDate() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/rest/projections/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("    {\"startTime\" : \"20:40:00\", \n" +
                        "    \"idHall\" :1,\n" +
                        "    \"idMovie\" :1}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("date : must not be null, "))
                .andReturn();
    }

    @Test
    public void InsertExceptionForNullTime() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/rest/projections/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"date\" :\"2060-04-11\",\n" +
                        "    \"idHall\" :1,\n" +
                        "    \"idMovie\" :1}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("startTime : must not be null, "))
                .andReturn();
    }

    @Test
    public void InsertExceptionForNullIdHall() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/rest/projections/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"date\" :\"2060-04-11\",\n" +
                        "    \"startTime\" : \"20:40:00\", \n" +
                        "    \"idMovie\" :3}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("idHall : must not be null, "))
                .andReturn();
    }

    @Test
    public void InsertExceptionForNullIdMovie() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/rest/projections/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"date\" :\"2060-04-11\",\n" +
                        "    \"startTime\" : \"20:40:00\", \n" +
                        "    \"idHall\" :1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    public void GetAllRightData() {
        List<ProjectionDB> list = new LinkedList<>();
        list.add(new ProjectionDB());
        when(service.getAll()).thenReturn(list);
        Assert.assertEquals(resource.getAll(), list);
    }

    @Test
    public void InsertRightData() {
        ProjectionDB projection = new ProjectionDB();
        when(service.insert(projection)).thenReturn(projection);
        Assert.assertEquals(resource.insert(projection).getStatusCode(), ProjectionsResource.handleInsertInProjections().getStatusCode());
        verify(service, times(1)).insert(projection);
    }

    @Test
    public void UpdateRightData() {
        ProjectionUpdateReq projection = new ProjectionUpdateReq();
        when(service.update(projection)).thenReturn(true);
        Assert.assertEquals(resource.update(projection).getStatusCode(), ProjectionsResource.handleUpdateInProjections().getStatusCode());
        verify(service, times(1)).update(projection);
    }

    @Test
    public void FilterRightData() {
        ProjectionViewResposne projectionViewResposne = new ProjectionViewResposne();
        projectionViewResposne.setHallName("Sala 1");
        projectionViewResposne.setMovieName("Matrix");
        List<ProjectionViewResposne> list = new LinkedList<>();

        FilterReq filterReq = new FilterReq();
        filterReq.setMovieName("Matrix");
        filterReq.setDate(LocalDate.of(2021, 4, 11));
        list.add(projectionViewResposne);
        when(service.getSelected(filterReq)).thenReturn(list);
        Assert.assertEquals(resource.getSelected(filterReq), list);

    }
}
