package br.com.playlist.weather.service.impl;

import br.com.playlist.weather.model.Genre;
import br.com.playlist.weather.service.GenreSelectionService;
import org.springframework.stereotype.Service;


@Service
public class GenreSelectionServiceImpl implements GenreSelectionService {

    @Override
    public Genre getGenreByTemperature(double temperature) {
        Genre genre;
        if (temperature >= 30) {
            genre = Genre.PARTY;
        } else if (temperature >= 15 && temperature < 30) {
            genre = Genre.POP;
        } else if (temperature >= 10 && temperature < 15) {
            genre = Genre.ROCK;
        } else {
            genre = Genre.CLASSIC;
        }
        return genre;
    }
}
