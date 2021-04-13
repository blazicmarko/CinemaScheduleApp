package com.example.Cinema.validator;

import com.example.Cinema.service.HallsService;
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
public class IdHallValidatorTest {

    IdHallValidator hallValidator;

    @MockBean
    HallsService hallsService;

    @Before
    public void initMock(){
        hallValidator = new IdHallValidator(hallsService);
    }

    @Test
    public void isValidNull(){
        Assert.assertTrue(hallValidator.isValid(null, null));
    }

    @Test
    public void isValidRightData(){
        when(hallsService.getLastId()).thenReturn(10);
        Assert.assertTrue(hallValidator.isValid(1,null));
    }

    @Test
    public void isValidOutOfBound(){
        when(hallsService.getLastId()).thenReturn(10);
        Assert.assertFalse(hallValidator.isValid(11,null));
    }
}
