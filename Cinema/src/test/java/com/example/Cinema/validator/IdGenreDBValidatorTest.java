package com.example.Cinema.validator;

import com.example.Cinema.service.InitService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@PrepareForTest(InitService.class)
public class IdGenreDBValidatorTest {


    IdGenreValidator genreValidator;

    @Test
    public void isValidNull() {
        Assert.assertTrue(genreValidator.isValid(null, null));
    }

    @Test
    public void isValidRightData() {
        when(InitService.getGenreLastId()).thenReturn(10);
        Assert.assertTrue(genreValidator.isValid(1, null));
    }

    @Test
    public void isValidOutOfBound() {
        when(InitService.getGenreLastId()).thenReturn(10);
        Assert.assertFalse(genreValidator.isValid(11, null));
    }
}
