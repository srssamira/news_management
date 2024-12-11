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
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }



    public Optional<NewsItem> findNewsItemByKeyword(String keyword) {
        String url = buildUrlApi(keyword);
        ResponseEntity<NewsAPIResponseDTO[]> apiResponse = searchNewsFromApi(url);
        return checkApiResponse(apiResponse);
    }



    private String buildUrlApi(String keyword) {
        return UriComponentsBuilder
                .fromUriString(URL_API)
                .queryParam("busca", keyword)
                .toUriString();
    }



    private Optional<NewsItem> checkApiResponse(ResponseEntity<NewsAPIResponseDTO[]> apiResponse) {
        if (apiResponse == null)
            return Optional.empty();

        HttpStatus responseHttpStatus = (HttpStatus) apiResponse.getStatusCode();

        if (responseHttpStatus.is2xxSuccessful()) {
            NewsAPIResponseDTO[] newsAPIResponseDTOs = apiResponse.getBody();
            return Optional.of(mapper.convertValue(newsAPIResponseDTOs, NewsItem.class));
        }

        else if (responseHttpStatus.is4xxClientError())
            return Optional.empty();


        else
            throw new ApiFailedException("Api error with satus code: " + responseHttpStatus);
    }



    private ResponseEntity<NewsAPIResponseDTO[]> searchNewsFromApi(String url) {
        try {
            return restTemplate.getForEntity(url, NewsAPIResponseDTO[].class);
        }
        catch (ApiFailedException apiFailedException) {
            throw new ApiFailedException("http error: " + apiFailedException.getStatusCode(), apiFailedException);
        }
        catch (ApiFailedException apiFailedException) {
            throw new ApiFailedException("api error: ", + apiFailedException.getMessage(), apiFailedException);
        }
    }



    private NewsItem toNewsItem(NewsAPIResponseDTO newsAPIResponseDTO) {
        return mapper.convertValue(newsAPIResponseDTO, NewsItem.class);
    }



}
