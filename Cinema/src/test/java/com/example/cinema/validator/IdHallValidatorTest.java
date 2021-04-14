package com.example.cinema.validator;

import com.example.cinema.service.InitService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.example.cinema.service.*")
@SpringBootTest
public class IdHallValidatorTest {

    IdHallValidator hallValidator;

    @Before
    public void initMock() {
        hallValidator = new IdHallValidator();
        mockStatic(InitService.class);

    }

    @Test
    public void isValidNull() {
        Assert.assertTrue(hallValidator.isValid(null, null));
    }

    @Test
    public void isValidRightData() {
        when(InitService.getHallLastId()).thenReturn(10);
        Assert.assertTrue(hallValidator.isValid(1, null));
    }

    @Test
    public void isValidOutOfBound() {
        when(InitService.getHallLastId()).thenReturn(10);
        Assert.assertFalse(hallValidator.isValid(11, null));
    }
}
