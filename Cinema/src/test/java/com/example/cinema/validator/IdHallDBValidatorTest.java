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
public class IdHallDBValidatorTest {

    IdHallValidator hallValidator;


    @Test
    public void isValidNull(){
        Assert.assertTrue(hallValidator.isValid(null, null));
    }

    @Test
    public void isValidRightData(){
        when(InitService.getHallLastId()).thenReturn(10);
        Assert.assertTrue(hallValidator.isValid(1, null));
    }

    @Test
    public void isValidOutOfBound(){
        when(InitService.getHallLastId()).thenReturn(10);
        Assert.assertFalse(hallValidator.isValid(11, null));
    }
}
