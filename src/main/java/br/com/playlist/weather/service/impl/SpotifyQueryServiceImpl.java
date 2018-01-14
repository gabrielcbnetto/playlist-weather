package br.com.playlist.weather.service.impl;

import br.com.playlist.weather.config.SpotifyConfig;
import br.com.playlist.weather.model.AuthTokenSpotify;
import br.com.playlist.weather.model.Genre;
import br.com.playlist.weather.model.PlayList;
import br.com.playlist.weather.service.SpotifyAuthorizationService;
import br.com.playlist.weather.service.SpotifyQueryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Component
public class SpotifyQueryServiceImpl implements SpotifyQueryService {

    private static final String KEY = "AuthToken";
    private static final Logger LOGGER = LogManager.getLogger(SpotifyQueryServiceImpl.class);
    @Autowired
    SpotifyAuthorizationService spotifyAuthorizationService;
    private RestTemplate restTemplate;

    public SpotifyQueryServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    @Cacheable(cacheNames = KEY)
    public String getOauthToken(SpotifyConfig config) {
        LOGGER.info("Authenticating with spotify");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", config.getBasicAuth());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
        String response;
        ResponseEntity<AuthTokenSpotify> clientResponse = restTemplate.exchange(config.getAuthSpotifyUrl(), HttpMethod.POST, entity, AuthTokenSpotify.class);
        response = clientResponse.getBody().getAccessToken();
        LOGGER.info("Authorization Token: " + response);
        return response;
    }

    @Override
    public PlayList getTrackSuggestions(SpotifyConfig config, Genre genre) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + spotifyAuthorizationService.getOauthToken(config));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<PlayList> responseEntity;
        try {
            LOGGER.info("Getting track suggestions for genre: " + genre.getPrettyName());
            responseEntity = restTemplate.exchange(config.getTrackSpotifyUrl(), HttpMethod.GET, entity, PlayList.class, genre.getQueryName());
            return responseEntity.getBody();
        } catch (RestClientException e) {
            if (e instanceof HttpStatusCodeException && ((HttpStatusCodeException) e).getStatusCode() == HttpStatus.UNAUTHORIZED) {
                LOGGER.info("Received 401 unauthorized, trying to get a new token after evicting from cache");
                spotifyAuthorizationService.evictTokenFromCache();
                return getTrackSuggestions(config, genre);
            }
            LOGGER.error("Problem getting spotity playlist", e);
            throw e;
        }
    }

    @CacheEvict(cacheNames = KEY)
    public void evictTokenFromCache() {
        LOGGER.info("Evicting cache from key: " + KEY);
    }


}
