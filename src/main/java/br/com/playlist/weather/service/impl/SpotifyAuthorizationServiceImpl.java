package br.com.playlist.weather.service.impl;

import br.com.playlist.weather.config.SpotifyConfig;
import br.com.playlist.weather.model.AuthTokenSpotify;
import br.com.playlist.weather.service.SpotifyAuthorizationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SpotifyAuthorizationServiceImpl implements SpotifyAuthorizationService {
    private static final Logger LOGGER = LogManager.getLogger(SpotifyQueryServiceImpl.class);
    private static final String KEY = "AuthToken";

    @Autowired
    CacheManager cacheManager;

    @HystrixCommand(fallbackMethod = "getBadToken")
    @Cacheable(value = KEY, unless = "#result!= null and #result.length()==0")
    @Override
    public String getOauthToken(SpotifyConfig config) {
        LOGGER.info("Authenticating with spotify");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", config.getBasicAuth());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
        String response;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AuthTokenSpotify> clientResponse = restTemplate.exchange(config.getAuthSpotifyUrl(), HttpMethod.POST, entity, AuthTokenSpotify.class);
        response = clientResponse.getBody().getAccessToken();
        LOGGER.info("Authorization Token: " + response);
        return response;
    }

    @CacheEvict(value = KEY, allEntries = true)
    public void evictTokenFromCache() {
        LOGGER.info("Evicting cache from key: " + KEY);
    }

    public String getBadToken(SpotifyConfig config) {
        LOGGER.info("Devolvendo vazio");
        return null;
    }
}
