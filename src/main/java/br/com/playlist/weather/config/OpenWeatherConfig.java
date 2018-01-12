package br.com.playlist.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenWeatherConfig {
    @Value("${service.openweather.app-id")
    private String openWeatherAppId;

    @Value("${service.openweather.city-url")
    private String cityUrl;

    @Value("${service.openweather.cords-url")
    private String cordsUrl;

    public String getOpenWeatherAppId() {
        return openWeatherAppId;
    }

    public String getCityUrl() {
        return cityUrl;
    }

    public String getCordsUrl() {
        return cordsUrl;
    }

}
