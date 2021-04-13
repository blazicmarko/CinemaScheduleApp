package com.example.Cinema.resource;

import com.example.Cinema.service.ProjectionService;
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
                        "    \"idMovie\" :0}")
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
                        "    \"startTime\" : \"23:40:00\", \n" +
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
}
