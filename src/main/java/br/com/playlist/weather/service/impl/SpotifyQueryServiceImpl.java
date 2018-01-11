package br.com.playlist.weather.service.impl;

import br.com.playlist.weather.model.AuthTokenSpotify;
import br.com.playlist.weather.model.Category;
import br.com.playlist.weather.model.PlayList;
import br.com.playlist.weather.model.SpotifyConfig;
import br.com.playlist.weather.service.SpotifyQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class SpotifyQueryServiceImpl implements SpotifyQueryService {

    private static final String KEY = "AuthToken";
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private CacheManager cacheManager;

    @Override
    @Cacheable(cacheNames = KEY)
    public String getOauthToken(SpotifyConfig config) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", config.getBasicAuth());
//        headers.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
        return restTemplate.exchange(config.getAuthSpotifyUrl(), HttpMethod.POST, entity, AuthTokenSpotify.class).getBody().getAccessToken();
    }

    @Override
    public PlayList getTrackSuggestions(Category category) {
        return null;
    }

    public void evictTokenFromCache() {
        final Cache cache = cacheManager.getCache(KEY);
        cache.evict(KEY);
    }


}
