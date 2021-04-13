package com.example.Cinema.validator;

import com.example.Cinema.service.GenreService;
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
public class IdGenreValidatorTest {


    IdGenreValidator genreValidator;


    @MockBean
    GenreService genresService;


    @Before
    public void initMock(){
        genreValidator = new IdGenreValidator(genresService);
    }

    @Test
    public void isValidNull(){
        Assert.assertTrue(genreValidator.isValid(null, null));
    }

    @Test
    public void isValidRightData(){
        when(genresService.getLastId()).thenReturn(10);
        Assert.assertTrue(genreValidator.isValid(1,null));
    }

    @Test
    public void isValidOutOfBound(){
        when(genresService.getLastId()).thenReturn(10);
        Assert.assertFalse(genreValidator.isValid(11,null));
    }
}
