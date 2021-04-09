package com.example.Cinema.service;

import com.example.Cinema.exception.WrongGenreNameException;
import com.example.Cinema.mapper.GenresMapper;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    private GenresMapper genresMapper;

    public GenreService(GenresMapper genresMapper) {
        this.genresMapper = genresMapper;
    }

    public int getLastId(){
        return genresMapper.getLastId();
    }

    public Integer getGenreIdByName(String genre) {
        return genresMapper.getIdByName(genre);
    }
}
