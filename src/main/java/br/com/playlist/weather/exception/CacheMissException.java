package br.com.playlist.weather.exception;

public class CacheMissException extends RuntimeException {

    public CacheMissException(String message) {
        super(message);
    }
}
