package br.com.playlist.weather.service;

import br.com.playlist.weather.config.SpotifyConfig;
import br.com.playlist.weather.model.Genre;
import br.com.playlist.weather.model.PlayList;

public interface SpotifyQueryService {

    /**
     * Performs a get in the spotify recommendations api using genre as a seed
     *
     * @param spotifyConfig Config containing url's for the services.
     * @param genre         The genre to retrieve the playlist from.
     * @return @see{Playlist}
     */
    PlayList getTrackSuggestions(SpotifyConfig spotifyConfig, Genre genre);
}
