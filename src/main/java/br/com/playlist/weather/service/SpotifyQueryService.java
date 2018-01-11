package br.com.playlist.weather.service;

import br.com.playlist.weather.model.Category;
import br.com.playlist.weather.model.PlayList;
import br.com.playlist.weather.model.SpotifyConfig;

public interface SpotifyQueryService {
    String getOauthToken(SpotifyConfig config);

    PlayList getTrackSuggestions(Category category);
}
