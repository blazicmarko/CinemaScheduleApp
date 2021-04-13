package com.example.cinema.validator;

import com.example.cinema.service.InitService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.example.cinema.service.*")
@SpringBootTest
public class MovieDBNameValidatorTest {

    MovieNameValidator movieNameValidator;

    List<String> list;

    @Before
    public void initMock() {
        movieNameValidator = new MovieNameValidator();
        mockStatic(InitService.class);
        list = new LinkedList<>();
        list.add("Matrix 2");
    }


    @Test
    public void isValidRightData() {
        when(InitService.getMovieNames()).thenReturn(list);
        Assert.assertTrue(movieNameValidator.isValid("Matrix", null));
    }

    @Test
    public void isValidOutOfBound() {
        when(InitService.getMovieNames()).thenReturn(list);
        Assert.assertFalse(movieNameValidator.isValid("Matrix 2", null));
    }
}
