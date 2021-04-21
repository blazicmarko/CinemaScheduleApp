package com.example.cinema.service;

import com.example.cinema.mapper.GenresMapper;
import com.example.cinema.model.dbModel.GenreDB;
import com.example.cinema.model.responseModel.GenreResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GenreService {
    private GenresMapper genresMapper;


    public GenreService(GenresMapper genresMapper) {
        this.genresMapper = genresMapper;
    }

    public int getLastId() {
        return InitService.getGenreLastId();
    }

    public Integer getGenreIdByName(String genre) {
        return genresMapper.getIdByName(genre);
    }

    public List<GenreResponse> findAll() {
        List<GenreDB> listDB = genresMapper.findAll();
        List<GenreResponse> listResponse = new LinkedList<>();
        for (GenreDB genreDB : listDB) {
            listResponse.add(fromDBtoResponse(genreDB));
        }
        return listResponse;
    }

    public GenreResponse fromDBtoResponse(GenreDB genreDB) {
        GenreResponse genreResponse = new GenreResponse();
        genreResponse.setId(genreDB.getId());
        genreResponse.setName(genreDB.getName());
        return genreResponse;
    }
}
