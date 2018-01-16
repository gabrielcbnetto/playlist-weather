package br.com.playlist.weather.controller;

import br.com.playlist.weather.config.OpenWeatherConfig;
import br.com.playlist.weather.config.SpotifyConfig;
import br.com.playlist.weather.model.Genre;
import br.com.playlist.weather.model.PlayList;
import br.com.playlist.weather.service.GenreSelectionService;
import br.com.playlist.weather.service.OpenWeatherMapService;
import br.com.playlist.weather.service.SpotifyAuthorizationService;
import br.com.playlist.weather.service.SpotifyQueryService;
import br.com.playlist.weather.validation.InRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
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

    @Autowired
    SpotifyAuthorizationService spotifyAuthorizationService;

    @RequestMapping(value = "/playlist", params = "city")
    public PlayList playList(@RequestParam(value = "city") String city) {
        double temp = openWeatherMapService.getTemperatureByCity(openWeatherConfig, city);
        Genre genre = genreSelectionService.getGenreByTemperature(temp);
        return spotifyQueryService.getTrackSuggestions(spotifyConfig, genre);
    }

    @RequestMapping(value = "/playlist", params = {"lat", "lon"})
    public PlayList playList(@Valid @InRange(min = -90d, max = 90d) @RequestParam(value = "lat") double lat,
                             @Valid @InRange(min = -180d, max = 180d) @RequestParam(value = "lon") double lon) {
        double temp = openWeatherMapService.getTemperatureByLatLon(openWeatherConfig, lat, lon);
        Genre genre = genreSelectionService.getGenreByTemperature(temp);
        return spotifyQueryService.getTrackSuggestions(spotifyConfig, genre);
    }

    @RequestMapping("/token")
    public String getOauth() {
        return spotifyAuthorizationService.getOauthToken(spotifyConfig);
    }

    @RequestMapping("/refreshToken")
    public String getNewOauth() {
        spotifyAuthorizationService.evictTokenFromCache();
        return spotifyAuthorizationService.getOauthToken(spotifyConfig);
    }
}
