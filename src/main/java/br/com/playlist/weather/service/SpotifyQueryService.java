package br.com.playlist.weather.service;

import br.com.playlist.weather.config.SpotifyConfig;
import br.com.playlist.weather.model.Genre;
import br.com.playlist.weather.model.PlayList;

public interface SpotifyQueryService {
    String getOauthToken(SpotifyConfig config);

    PlayList getTrackSuggestions(SpotifyConfig spotifyConfig, Genre genre);
}
