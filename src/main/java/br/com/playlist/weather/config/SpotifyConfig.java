package br.com.playlist.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class SpotifyConfig {

    @Value("${service.spotify.client-id}")
    private String clientID;

    @Value("${service.spotify.client-secret}")
    private String clientSecret;

    @Value("${service.spotify.auth-url}")
    private String authSpotifyUrl;

    @Value("${service.spotify.track-url}")
    private String trackSpotifyUrl;

    public String getClientSecret() {
        return clientSecret;
    }

    public String getClientID() {
        return clientID;
    }

    public String getAuthSpotifyUrl() {
        return authSpotifyUrl;
    }

    public String getTrackSpotifyUrl() {
        return trackSpotifyUrl;
    }

    public String getBasicAuth() {
        String clientCredentials = getClientID() + ":" + getClientSecret();
        return "Basic " + Base64.getEncoder().encodeToString(clientCredentials.getBytes());
    }
}
