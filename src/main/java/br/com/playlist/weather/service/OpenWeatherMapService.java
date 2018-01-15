package br.com.playlist.weather.service;

import br.com.playlist.weather.config.OpenWeatherConfig;

public interface OpenWeatherMapService {

    double getTemperatureByCity(OpenWeatherConfig weatherConfig, String city);

    double getTemperatureByLatLon(OpenWeatherConfig weatherConfig, double lat, double lon);
}
