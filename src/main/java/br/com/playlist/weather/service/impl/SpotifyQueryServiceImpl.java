package br.com.playlist.weather.service.impl;

import br.com.playlist.weather.config.SpotifyConfig;
import br.com.playlist.weather.model.Genre;
import br.com.playlist.weather.model.PlayList;
import br.com.playlist.weather.service.SpotifyAuthorizationService;
import br.com.playlist.weather.service.SpotifyQueryService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Component
public class SpotifyQueryServiceImpl implements SpotifyQueryService {

    private static final String cacheKey = "SpotifyTracksCache";
    private static final Logger LOGGER = LogManager.getLogger(SpotifyQueryServiceImpl.class);
    @Autowired
    SpotifyAuthorizationService spotifyAuthorizationService;
    private RestTemplate restTemplate;

    @Autowired
    CacheManager cacheManager;

    public SpotifyQueryServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @HystrixCommand(fallbackMethod = "getTrackFromCache")
    @CachePut(value = cacheKey, key = "#genre.queryName")
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

    public PlayList getTrackFromCache(SpotifyConfig config, Genre genre) {
        LOGGER.info("retornando playlist do cache");
        return cacheManager.getCache(cacheKey).get(genre.getQueryName(), PlayList.class);
    }
}
