package br.com.playlist.weather.service;

import br.com.playlist.weather.config.SpotifyConfig;
import br.com.playlist.weather.model.Category;
import br.com.playlist.weather.model.PlayList;

public interface SpotifyQueryService {
    String getOauthToken(SpotifyConfig config);

    PlayList getTrackSuggestions(Category category);
}
