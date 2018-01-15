package br.com.playlist.weather.service.impl;

import br.com.playlist.weather.config.OpenWeatherConfig;
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
    @CachePut(value = cacheKey, key = "#city")
    public double getTemperatureByCity(OpenWeatherConfig weatherConfig, String city) {
        return restTemplate.getForObject(weatherConfig.getCityUrl(), CityTemperature.class, city, weatherConfig.getOpenWeatherAppId()).getMain().getTemp();
    }

    @Override
    @HystrixCommand(fallbackMethod = "getCachedTemperature")
    @CachePut(value = cacheKey, key = "{#lat, #lon}")
    public double getTemperatureByLatLon(OpenWeatherConfig weatherConfig, int lat, int lon) {
        return restTemplate.getForObject(weatherConfig.getCordsUrl(), CityTemperature.class, lat, lon, weatherConfig.getOpenWeatherAppId()).getMain().getTemp();

    }

    protected double getCachedTemperature(OpenWeatherConfig weatherConfig, String city) {
        return cacheManager.getCache(cacheKey).get(city, Double.class);
    }


    protected double getCachedTemperature(OpenWeatherConfig weatherConfig, int lat, int lon) {
        LOGGER.info("Returning cached temperature for lat: " + lat + "lon: " + lon);
        return cacheManager.getCache(cacheKey).get(Arrays.asList(lat, lon), Double.class);
    }
}
