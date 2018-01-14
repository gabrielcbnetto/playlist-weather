package br.com.playlist.weather.service;

import br.com.playlist.weather.model.Genre;

public interface GenreSelectionService {

    /**
     * Define a genre of music based on the current temperature<p>
     * If temperature > 30 degrees return party<br>
     * If temperature >= 15 and < 30 degrees return pop<br>
     * If temperature >= 10 and < 15 degrees return rock<br>
     * If temperature < 10 return classical
     * }
     *
     * @param temperature Temperature in celsius
     * @return {@link Genre} Music Genre
     */
    Genre getGenreByTemperature(double temperature);

}
