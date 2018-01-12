package br.com.playlist.weather.model;

public enum Genre {
    ROCK("Rock"),
    CLASSIC("Classic"),
    POP("Pop"),
    PARTY("Party");
    private final String prettyName;

    Genre(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getPrettyName() {
        return prettyName;
    }
}
