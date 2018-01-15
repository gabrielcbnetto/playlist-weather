package br.com.playlist.weather.service.impl;

import br.com.playlist.weather.config.OpenWeatherConfig;
import br.com.playlist.weather.exception.CacheMissException;
import br.com.playlist.weather.model.CityTemperature;
import br.com.playlist.weather.service.OpenWeatherMapService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {

    private static final Logger LOGGER = LogManager.getLogger(OpenWeatherMapServiceImpl.class);
    private RestTemplate restTemplate = new RestTemplate();
    private static final String cacheKey = "OpenWeatherCache";
    @Autowired
    private CacheManager cacheManager;

    @Override
    @HystrixCommand(fallbackMethod = "getCachedTemperature")
    @CachePut(value = cacheKey, key = "#city", unless = "#result == null")
    public double getTemperatureByCity(OpenWeatherConfig weatherConfig, String city) {
        return restTemplate.getForObject(weatherConfig.getCityUrl(), CityTemperature.class, city, weatherConfig.getOpenWeatherAppId()).getMain().getTemp();
    }

    @Override
    @HystrixCommand(fallbackMethod = "getCachedTemperature")
    @CachePut(value = cacheKey, key = "{#lat, #lon}", unless = "#result == null")
    public double getTemperatureByLatLon(OpenWeatherConfig weatherConfig, double lat, double lon) {
        return restTemplate.getForObject(weatherConfig.getCordsUrl(), CityTemperature.class, lat, lon, weatherConfig.getOpenWeatherAppId()).getMain().getTemp();

    }

    protected double getCachedTemperature(OpenWeatherConfig weatherConfig, String city) {
        LOGGER.info("Returning cached temperature for city: " + city);
        Double res = cacheManager.getCache(cacheKey).get(city, Double.class);
        if (res == null) {
            throw new CacheMissException("City not found in cache, and cant get live version");
        }
        return res;
    }


    protected double getCachedTemperature(OpenWeatherConfig weatherConfig, double lat, double lon) {
        LOGGER.info("Returning cached temperature for lat: " + lat + "lon: " + lon);
        Double res = cacheManager.getCache(cacheKey).get(Arrays.asList(lat, lon), Double.class);
        if (res == null) {
            throw new CacheMissException("Latitude and Longitude not found in cache, and cant get live version");
        }
        return res;
    }
}
