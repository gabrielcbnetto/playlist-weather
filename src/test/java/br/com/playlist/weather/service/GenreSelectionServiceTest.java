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

    private static final double CLASSIC = -10.9;
    private static final double ROCK = 14.5;
    private static final double POP = 16;
    private static final double PARTY = 34;


    @Autowired
    private GenreSelectionService genreSelectionService;

    @Test
    public void getGenreByTemperatureClassic() {
        Assert.assertEquals(genreSelectionService.getGenreByTemperature(CLASSIC), Genre.CLASSIC);
    }

    public void getGenreByTemperatureParty() {
        Assert.assertEquals(genreSelectionService.getGenreByTemperature(PARTY), Genre.PARTY);
    }

    public void getGenreByTemperaturePop() {
        Assert.assertEquals(genreSelectionService.getGenreByTemperature(POP), Genre.POP);
    }

    public void getGenreByTemperatureRock() {
        Assert.assertEquals(genreSelectionService.getGenreByTemperature(ROCK), Genre.ROCK);
    }
}