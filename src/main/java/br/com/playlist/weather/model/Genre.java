package br.com.playlist.weather.model;

public enum Genre {
    ROCK("Rock", "rock"),
    CLASSIC("Classic", "classical"),
    POP("Pop", "pop"),
    PARTY("Party", "party");
    private final String prettyName;
    private final String queryName;

    Genre(String prettyName, String queryName) {
        this.prettyName = prettyName;
        this.queryName = queryName;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public String getQueryName() {
        return queryName;
    }
}
