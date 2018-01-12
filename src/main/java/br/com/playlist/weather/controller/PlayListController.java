package br.com.playlist.weather.controller;

import br.com.playlist.weather.config.OpenWeatherConfig;
import br.com.playlist.weather.config.SpotifyConfig;
import br.com.playlist.weather.model.PlayList;
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

	@RequestMapping("/playlist/city/")
	public PlayList playListCity(@RequestParam(value="city") String city) {
        float temp = openWeatherMapService.getTemperatureByCity(openWeatherConfig, city);
        return null;
	}

    @RequestMapping("/token/")
    public String getOauth() {
        return spotifyQueryService.getOauthToken(spotifyConfig);
    }
}
