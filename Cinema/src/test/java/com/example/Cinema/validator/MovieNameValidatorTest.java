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
public class MovieNameValidatorTest {
    MovieNameValidator movieNameValidator;

    @MockBean
    MoviesService moviesService;

    @Before
    public void initMock(){
        movieNameValidator = new MovieNameValidator(moviesService);
    }


    @Test
    public void isValidRightData(){
        when(moviesService.checkMovieName("Matrix")).thenReturn(true);
        Assert.assertTrue(movieNameValidator.isValid("Matrix",null));
    }

    @Test
    public void isValidOutOfBound(){
        when(moviesService.checkMovieName("Matrix 2")).thenReturn(false);
        Assert.assertFalse(movieNameValidator.isValid("Matrix 2",null));
    }
}
