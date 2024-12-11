package com.news_management.br.news_management.app.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_management.br.news_management.domain.dtos.NewsAPIResponseDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
public class ExternalApiNewsAdapter {

    private static final String URL_API = "http://servicodados.ibge.gov.br/api/v3/noticias/";

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    public ExternalApiNewsAdapter(RestTemplate restTemplate, ObjectMapper mapper) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    public Optional<NewsItem> findNewsItemByKeyword(String keyword) {
        String url = UriComponentsBuilder
                .fromPath(URL_API)
                .queryParam("busca", keyword)
                .toUriString();

        try {
            ResponseEntity<NewsAPIResponseDTO[]> apiResponse = restTemplate
                    .getForEntity(url, NewsAPIResponseDTO[].class);

            if (apiResponse.getStatusCode().is2xxSuccessful()) {
                NewsAPIResponseDTO[] newsApiItems = apiResponse.getBody();
                if (newsApiItems.length > 0 && newsApiItems != null) {
                    return Optional.of(toNewsItem(newsApiItems[0]));
                } else return Optional.empty();

            } else if (apiResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Optional.empty();

            } else throw new RuntimeException("Api error");

        } catch (HttpClientErrorException exception) {
            throw new RuntimeException("http error" + exception.getStatusCode());
        } catch (RuntimeException exception) {
            throw new RuntimeException("Api error: " + exception.getMessage());
        }
    }

    private NewsItem toNewsItem(NewsAPIResponseDTO apiResponseDTO) {
        return mapper.convertValue(apiResponseDTO, NewsItem.class);
    }
}
