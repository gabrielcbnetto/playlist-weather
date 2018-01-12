package br.com.playlist.weather.service;

import br.com.playlist.weather.model.Genre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GenreSelectionServiceTest {

    private double CLASSIC = -10.9;
    private double ROCK = 14.5;
    private double POP = 16;
    private double PARTY = 34;


    @Autowired
    private GenreSelectionService genreSelectionService;

    @Test
    public void getGenreByTemperature() {
        Assert.assertEquals(genreSelectionService.getGenreByTemperature(CLASSIC), Genre.CLASSIC);
        Assert.assertEquals(genreSelectionService.getGenreByTemperature(PARTY), Genre.PARTY);
        Assert.assertEquals(genreSelectionService.getGenreByTemperature(POP), Genre.POP);
        Assert.assertEquals(genreSelectionService.getGenreByTemperature(ROCK), Genre.ROCK);
    }
}