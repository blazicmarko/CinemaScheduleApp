package com.example.Cinema.validator;

import com.example.Cinema.service.GenreService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdGenreValidatorTest {


    IdGenreValidator genreValidator;


    @MockBean
    GenreService genresService;

    public IdGenreValidatorTest() {
        genreValidator = new IdGenreValidator(genresService);
    }


    @Test
    public void IsValidNull(){
        Assert.assertTrue(genreValidator.isValid(null, null));
    }
}
