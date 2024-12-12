package com.news_management.br.news_management.app.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_management.br.news_management.app.infra.ApiFailedException;
import com.news_management.br.news_management.domain.dtos.ApiReceivedDataDTO;
import com.news_management.br.news_management.domain.dtos.NewsAPIResponseDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class ExternalApiNewsAdapter {

    private static final String URL_API = "http://servicodados.ibge.gov.br/api/v3/noticias/";
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public ExternalApiNewsAdapter(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }



    public Optional<NewsItem> findNewsItemByKeyword(String keyword) {
        String url = buildUrlApi(keyword);
        ResponseEntity<ApiReceivedDataDTO> apiResponse = searchNewsFromApi(url);
        return checkApiResponse(apiResponse);
    }



    private String buildUrlApi(String keyword) {
        return UriComponentsBuilder
                .fromUriString(URL_API)
                .queryParam("busca", keyword)
                .toUriString();
    }



    private Optional<NewsItem> checkApiResponse(ResponseEntity<ApiReceivedDataDTO> apiResponse) {

        if (apiResponse == null || apiResponse.getBody() == null
        || apiResponse.getBody().getItems() == null)
            return Optional.empty();

        HttpStatus responseHttpStatus = (HttpStatus) apiResponse.getStatusCode();

        if (responseHttpStatus.is2xxSuccessful()) {
            List<NewsAPIResponseDTO> apiResponseDTOS = apiResponse.getBody().getItems();

            if (apiResponseDTOS.size() > 0)
                return Optional.of(toNewsItem(apiResponseDTOS.get(0)));

            else
                return Optional.empty();
        }

        else if (responseHttpStatus.is4xxClientError())
            return Optional.empty();


        else
            throw new ApiFailedException("Api error with satus code: " + responseHttpStatus);
    }



    private ResponseEntity<ApiReceivedDataDTO> searchNewsFromApi(String url) {
        try {
            return restTemplate.getForEntity(url, ApiReceivedDataDTO.class);
        }
        catch (ApiFailedException apiFailedException) {
            throw new ApiFailedException("api error" + apiFailedException.getMessage());
        }
    }



    private NewsItem toNewsItem(NewsAPIResponseDTO newsAPIResponseDTO) {
        NewsItem newsItem = new NewsItem();
        newsItem.setText(newsAPIResponseDTO.getIntroducao());
        newsItem.setUrl(newsAPIResponseDTO.getLink());
        newsItem.setPublicationDate(LocalDate.parse(newsAPIResponseDTO.getData_publicacao().toString().trim(), dateFormat));

        return newsItem;
    }



}
