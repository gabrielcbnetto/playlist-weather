package br.com.playlist.weather.service;

import br.com.playlist.weather.model.Genre;

public interface GenreSelectionService {

    Genre getGenreByTemperature(double temperature);

}
