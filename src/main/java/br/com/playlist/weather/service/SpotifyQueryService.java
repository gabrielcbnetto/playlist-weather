package br.com.playlist.weather.service;

import br.com.playlist.weather.config.SpotifyConfig;
import br.com.playlist.weather.model.Genre;
import br.com.playlist.weather.model.PlayList;

public interface SpotifyQueryService {

    /**
     * Get authorization token from spotify api
     *
     * @param spotifyConfig Spotify Config containing secret and client id, plus the services urls
     * @return Authorization token
     */
    String getOauthToken(SpotifyConfig spotifyConfig);

    /**
     * Performs a get in the spotify recommendations api using genre as a seed
     *
     * @param spotifyConfig Config containing url's for the services.
     * @param genre         The genre to retrieve the playlist from.
     * @return @see{Playlist}
     */
    PlayList getTrackSuggestions(SpotifyConfig spotifyConfig, Genre genre);
}
