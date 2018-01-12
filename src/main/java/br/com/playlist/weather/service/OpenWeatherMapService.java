package br.com.playlist.weather.service;

import br.com.playlist.weather.config.OpenWeatherConfig;

public interface OpenWeatherMapService {

    float getTemperatureByCity(OpenWeatherConfig weatherConfig, String city);

    float getTemperatureByLatLon(OpenWeatherConfig weatherConfig, int lat, int lon);
}
