package br.com.playlist.weather.service.impl;

import br.com.playlist.weather.model.Genre;
import br.com.playlist.weather.service.GenreSelectionService;
import org.springframework.stereotype.Service;


@Service
public class GenreSelectionServiceImpl implements GenreSelectionService {

    private static final double HOTTEMPERATURE = 30d;
    private static final double COOLTEMPERATURE = 15d;
    private static final double CHILLYTEMPERATURE = 10d;

    @Override
    public Genre getGenreByTemperature(double temperature) {
        Genre genre;
        if (temperature >= HOTTEMPERATURE) {
            genre = Genre.PARTY;
        } else if (temperature >= COOLTEMPERATURE) {
            genre = Genre.POP;
        } else if (temperature >= CHILLYTEMPERATURE) {
            genre = Genre.ROCK;
        } else {
            genre = Genre.CLASSIC;
        }
        return genre;
    }
}
