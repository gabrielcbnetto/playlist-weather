package br.com.playlist.weather.service.impl;

import br.com.playlist.weather.config.OpenWeatherConfig;
import br.com.playlist.weather.model.CityTemperature;
import br.com.playlist.weather.service.OpenWeatherMapService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public float getTemperatureByCity(OpenWeatherConfig weatherConfig, String city) {
        // return restTemplate.getForObject("http://samples.openweathermap.org/data/2.5/weather?q=London&appid=b6907d289e10d714a6e88b30761fae22", CityTemperature.class).getMain().getTemp();
        //HttpEntity<?> request = new HttpEntity<>();
        return restTemplate.getForObject("http://samples.openweathermap.org/data/2.5/weather?q={city}&appid={appid}", CityTemperature.class, city, weatherConfig.getOpenWeatherAppId()).getMain().getTemp();
    }

    @Override
    public float getTemperatureByLatLon(OpenWeatherConfig weatherConfig, int lat, int lon) {
        return restTemplate.getForObject("http://samples.openweathermap.org/data/2.5/weather?q=London&appid=b6907d289e10d714a6e88b30761fae22", CityTemperature.class).getMain().getTemp();
    }
}
