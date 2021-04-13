package com.example.Cinema.validator;

import com.example.Cinema.service.MoviesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdMovieDBValidatorTest {

    IdMovieValidator idMovieValidator;

    @MockBean
    MoviesService moviesService;

    @Before
    public void initMock() {
        idMovieValidator = new IdMovieValidator(moviesService);
    }

    @Test
    public void isValidNull(){
        Assert.assertTrue(idMovieValidator.isValid(null, null));
    }

    @Test
    public void isValidRightData(){
        when(moviesService.getLastId()).thenReturn(10);
        Assert.assertTrue(idMovieValidator.isValid(1,null));
    }

    @Test
    public void isValidOutOfBound(){
        when(moviesService.getLastId()).thenReturn(10);
        Assert.assertFalse(idMovieValidator.isValid(11,null));
    }
}
