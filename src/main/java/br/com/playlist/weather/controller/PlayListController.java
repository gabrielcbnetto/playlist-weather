package br.com.playlist.weather.controller;

import br.com.playlist.weather.config.OpenWeatherConfig;
import br.com.playlist.weather.config.SpotifyConfig;
import br.com.playlist.weather.model.Genre;
import br.com.playlist.weather.model.PlayList;
import br.com.playlist.weather.service.GenreSelectionService;
import br.com.playlist.weather.service.OpenWeatherMapService;
import br.com.playlist.weather.service.SpotifyQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayListController {

    @Autowired
    SpotifyConfig spotifyConfig;
    @Autowired
    OpenWeatherConfig openWeatherConfig;

    @Autowired
    SpotifyQueryService spotifyQueryService;

    @Autowired
    OpenWeatherMapService openWeatherMapService;

    @Autowired
    GenreSelectionService genreSelectionService;

    @RequestMapping(value = "/playlist", params = "city")
	public PlayList playListCity(@RequestParam(value="city") String city) {
        double temp = openWeatherMapService.getTemperatureByCity(openWeatherConfig, city);
        Genre genre = genreSelectionService.getGenreByTemperature(temp);
        return spotifyQueryService.getTrackSuggestions(spotifyConfig, genre);
    }

    @RequestMapping(value = "/playlist", params = {"lat", "lon"})
    public PlayList playListCity(@RequestParam(value = "lat") int lat, @RequestParam(value = "lon") int lon) {
        double temp = openWeatherMapService.getTemperatureByLatLon(openWeatherConfig, lat, lon);
        Genre genre = genreSelectionService.getGenreByTemperature(temp);
        return spotifyQueryService.getTrackSuggestions(spotifyConfig, genre);
    }

    @RequestMapping("/token")
    public String getOauth() {
        return spotifyQueryService.getOauthToken(spotifyConfig);
    }
}
