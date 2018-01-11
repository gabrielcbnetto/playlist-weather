package br.com.playlist.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PlaylistWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaylistWeatherApplication.class, args);
	}
}
