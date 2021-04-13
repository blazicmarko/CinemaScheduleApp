package com.example.cinema.service;

import com.example.cinema.mapper.GenresMapper;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    private GenresMapper genresMapper;


    public GenreService(GenresMapper genresMapper) {
        this.genresMapper = genresMapper;
    }

    public int getLastId(){
        return InitService.getGenreLastId();
    }

    public Integer getGenreIdByName(String genre) {
        return genresMapper.getIdByName(genre);
    }
}
