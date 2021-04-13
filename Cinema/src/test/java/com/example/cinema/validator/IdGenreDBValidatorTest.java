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
public class IdGenreDBValidatorTest {


    IdGenreValidator genreValidator;

    @Before
    public void initMock() {
        genreValidator = new IdGenreValidator();
        mockStatic(InitService.class);

    }

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
