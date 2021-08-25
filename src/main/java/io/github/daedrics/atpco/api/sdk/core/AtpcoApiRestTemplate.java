package io.github.daedrics.atpco.api.sdk.core;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.github.daedrics.atpco.api.sdk.config.AtpcoConfigurationProperties;
import io.github.daedrics.atpco.api.sdk.config.AtpcoCredentials;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AtpcoApiRestTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(AtpcoApiRestTemplate.class);

    private static final String RESOURCE_ACCESS_EXCEPTION = "Resource access exception {}";

    private static final String TOO_MANY_REQUESTS = "Too many requests reported by ATPCO!";

    private static final String BAD_ATPCO_CREDENTIALS = "Bad ATPCO credentials configured";



    private static final ConcurrentHashMap<String, OAuth2RestTemplate> restTemplates = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;

    private final AtpcoConfigurationProperties atpcoConfiguration;


    /**
     * Get a RestTemplate for a specific client based on it's client secret id.
     * Create one if it hasn't been initialized yet.
     *
     * @return {@link OAuth2RestTemplate}
     */
    private OAuth2RestTemplate getRestTemplateForAtpco(AtpcoCredentials atpcoCredentials) {
        synchronized (restTemplates) {
            OAuth2RestTemplate restTemplate = restTemplates.get(atpcoCredentials.getClientSecret());

            if (restTemplate == null) {
                ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
                details.setAccessTokenUri(atpcoConfiguration.getBaseUri() + atpcoConfiguration.getAccessTokenUri());
                details.setClientId(atpcoCredentials.getClientId());
                details.setClientSecret(atpcoCredentials.getClientSecret());
                details.setGrantType("client_credentials");
                details.setClientAuthenticationScheme(AuthenticationScheme.form);

                restTemplate = new OAuth2RestTemplate(details, new DefaultOAuth2ClientContext());
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(atpcoConfiguration.getBaseUri()));

                //add object mapper needed for the serializers/deserializers to oath rest template creation
                //replace existing http message converter by autowiring the already configured objectMapper
                MappingJackson2HttpMessageConverter newConverter = new MappingJackson2HttpMessageConverter();
                newConverter.setObjectMapper(objectMapper);
                List<HttpMessageConverter<?>> defaultMessageConverters = restTemplate.getMessageConverters();
                int idxOfHttpMsgConverter = IntStream.range(0, defaultMessageConverters.size())
                        .filter(msgConverterIdx -> defaultMessageConverters.get(msgConverterIdx) instanceof MappingJackson2HttpMessageConverter)
                        .findFirst()
                        .getAsInt();
                restTemplate.getMessageConverters().set(idxOfHttpMsgConverter, newConverter);

                restTemplates.put(atpcoCredentials.getClientSecret(), restTemplate);
            }

            return restTemplate;
        }
    }

    public AtpcoConfigurationProperties getAtpcoConfiguration() {
        return atpcoConfiguration;
    }

    public <T> T getForEntity(AtpcoCredentials credentialsDetails, Class<T> clazz, String url, Object... uriVariables) {
        try {
            ResponseEntity<String> response = getRestTemplateForAtpco(credentialsDetails).getForEntity(url, String.class, uriVariables);
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            return readValue(response, javaType);
        } catch (HttpClientErrorException httpClientErrorException) {
            LOGGER.error(httpClientErrorException.getMessage(), httpClientErrorException);
            return null;
        } catch (ResourceAccessException resourceAccessException) {
            LOGGER.error(RESOURCE_ACCESS_EXCEPTION, resourceAccessException.getMessage(), resourceAccessException);
            return null;
        }
    }

    public <T> List<T> getForList(AtpcoCredentials credentialsDetails, Class<T> clazz, String url, Object... uriVariables) {
        try {
            ResponseEntity<String> response = getRestTemplateForAtpco(credentialsDetails).getForEntity(url, String.class, uriVariables);
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return readValue(response, collectionType);
        } catch (HttpClientErrorException httpClientErrorException) {
            LOGGER.error(httpClientErrorException.getMessage(), httpClientErrorException);
            return null;
        } catch (ResourceAccessException resourceAccessException) {
            LOGGER.error(RESOURCE_ACCESS_EXCEPTION, resourceAccessException.getMessage(), resourceAccessException);
            return null;
        }
    }

    public <T, R> T postForEntity(AtpcoCredentials credentialsDetails, Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<R> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response;
        try {
            response = getRestTemplateForAtpco(credentialsDetails).postForEntity(url, request, String.class, uriVariables);
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            return readValue(response, javaType);
        } catch (OAuth2AccessDeniedException accessDeniedEx) {
            LOGGER.error(BAD_ATPCO_CREDENTIALS, accessDeniedEx);
            return null;
        } catch (HttpClientErrorException httpClientErrorException) {
            LOGGER.error(httpClientErrorException.getMessage(), httpClientErrorException);
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            response = new ResponseEntity<>(httpClientErrorException.getResponseBodyAsString(), HttpStatus.OK);
            return readValue(response, javaType);
        }
    }

    public <T, R> T putForEntity(AtpcoCredentials credentialsDetails, Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity<>(body);
        try {
            ResponseEntity<String> response = getRestTemplateForAtpco(credentialsDetails).exchange(url, HttpMethod.PUT, request, String.class, uriVariables);
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            return readValue(response, javaType);
        } catch (HttpClientErrorException httpClientErrorException) {
            LOGGER.error(TOO_MANY_REQUESTS, httpClientErrorException);
            return null;
        } catch (ResourceAccessException resourceAccessException) {
            LOGGER.error(RESOURCE_ACCESS_EXCEPTION, resourceAccessException.getMessage(), resourceAccessException);
            return null;
        }
    }

    public void delete(AtpcoCredentials credentialsDetails, String url, Object... uriVariables) {
        try {
            getRestTemplateForAtpco(credentialsDetails).delete(url, uriVariables);
        } catch (HttpClientErrorException httpClientErrorException) {
            LOGGER.error(TOO_MANY_REQUESTS, httpClientErrorException);

        } catch (ResourceAccessException resourceAccessException) {
            LOGGER.error(RESOURCE_ACCESS_EXCEPTION, resourceAccessException.getMessage(), resourceAccessException);
        }
    }

    private <T> T readValue(ResponseEntity<String> response, JavaType javaType) {
        T result = null;
        if (response.getStatusCode() == HttpStatus.OK ||
                response.getStatusCode() == HttpStatus.CREATED) {
            try {
                result = objectMapper.readValue(response.getBody(), javaType);
            } catch (IOException e) {
                LOGGER.info(e.getMessage());
            }
        } else {
            LOGGER.info("No data found {}", response.getStatusCode());
        }

        return result;
    }

}