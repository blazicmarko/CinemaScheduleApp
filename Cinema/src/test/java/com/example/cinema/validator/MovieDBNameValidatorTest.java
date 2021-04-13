package com.example.cinema.validator;

import com.example.cinema.service.MoviesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieDBNameValidatorTest {
    MovieNameValidator movieNameValidator;

    @MockBean
    MoviesService moviesService;

    @Before
    public void initMock() {
        movieNameValidator = new MovieNameValidator(moviesService);
    }


    @Test
    public void isValidRightData(){

        Assert.assertTrue(movieNameValidator.isValid("Matrix",null));
    }

    @Test
    public void isValidOutOfBound(){

        Assert.assertFalse(movieNameValidator.isValid("Matrix 2",null));
    }
}
