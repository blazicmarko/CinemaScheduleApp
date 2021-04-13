package com.example.cinema.validator;

import com.example.cinema.service.InitService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdMovieDBValidatorTest {

    IdMovieValidator idMovieValidator;


    @Test
    public void isValidNull(){
        Assert.assertTrue(idMovieValidator.isValid(null, null));
    }

    @Test
    public void isValidRightData(){
        when(InitService.getMovieLastId()).thenReturn(10);
        Assert.assertTrue(idMovieValidator.isValid(1, null));
    }

    @Test
    public void isValidOutOfBound(){
        when(InitService.getMovieLastId()).thenReturn(10);
        Assert.assertFalse(idMovieValidator.isValid(11, null));
    }
}
