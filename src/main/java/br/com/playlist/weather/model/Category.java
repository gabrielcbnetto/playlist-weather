package br.com.playlist.weather.model;

public enum Category {
    ROCK(""),
    CLASSIC(""),
    POP(""),
    PARTY("");
    private final String categoryId;

    Category(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }
}
