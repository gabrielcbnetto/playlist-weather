package br.com.playlist.weather.service;

import br.com.playlist.weather.config.SpotifyConfig;

public interface SpotifyAuthorizationService {

    /**
     * Get authorization token from spotify api
     *
     * @param spotifyConfig Spotify Config containing secret and client id, plus the services urls
     * @return Authorization token
     */
    String getOauthToken(SpotifyConfig spotifyConfig);

    /**
     * Evicts token from cache
     */
    void evictTokenFromCache();
}
