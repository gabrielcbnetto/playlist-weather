package br.com.playlist.weather.model;

public class CityTemperature {
    private int id;
    private int cod;
    private float temp;
    private String name;
    private WeatherData main;

    public int getId() {
        return id;
    }

    public int getCod() {
        return cod;
    }

    public float getTemp() {
        return temp;
    }

    public String getName() {
        return name;
    }

    public WeatherData getMain() {
        return main;
    }
}
