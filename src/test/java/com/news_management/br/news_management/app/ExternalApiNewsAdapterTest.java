package com.news_management.br.news_management.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_management.br.news_management.app.adapters.ExternalApiNewsAdapter;
import com.news_management.br.news_management.domain.dtos.NewsAPIResponseDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExternalApiNewsAdapterTest {

    @InjectMocks
    private ExternalApiNewsAdapter apiNewsAdapter;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper mapper;

    private NewsItem newsItem;

    private NewsAPIResponseDTO apiResponse;

    @BeforeEach
    void setUp() {
        apiResponse = new NewsAPIResponseDTO();
        apiResponse.setUrl("https://newsapi.com/");
        apiResponse.setText("Bolsonaro foi preso");
        apiResponse.setPublicationDate(LocalDate.now());

        newsItem = new NewsItem();
        newsItem.setUrl("https://newsapi.com/");
        newsItem.setText("Bolsonaro foi preso");
        newsItem.setPublicationDate(LocalDate.now());
    }

    @Test
    void testWhenNewsHasFoundWithSuccess() {
        NewsAPIResponseDTO[] apiResponses = { apiResponse };
        ResponseEntity<NewsAPIResponseDTO[]> responseEntity = ResponseEntity.ok(apiResponses);

        when(restTemplate.getForEntity(anyString(), eq(NewsAPIResponseDTO[].class)))
                .thenReturn(responseEntity);

        when(mapper.convertValue(any(NewsAPIResponseDTO.class), eq(NewsItem.class)))
                .thenReturn(newsItem);

        Optional<NewsItem> newsItemOptional = apiNewsAdapter.findNewsItemByKeyword("preso");

        assertTrue(newsItemOptional.isPresent());
        assertEquals(newsItem.getUrl(), newsItemOptional.get().getUrl());
        assertEquals(newsItem.getText(), newsItemOptional.get().getText());
        assertEquals(newsItem.getPublicationDate(), newsItemOptional.get().getPublicationDate());

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(NewsAPIResponseDTO[].class));
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(NewsAPIResponseDTO[].class));
        verify(mapper, times(1)).convertValue(any(NewsAPIResponseDTO.class), eq(NewsItem.class));
    }

    @Test
    void testWhenNewsHasNotFound() throws Exception {
        ResponseEntity<NewsAPIResponseDTO[]> apiResponses = ResponseEntity.notFound().build();

        when(restTemplate.getForEntity(anyString(), eq(NewsAPIResponseDTO[].class)))
            .thenReturn(apiResponses);

        Optional<NewsItem> newsItemOptional = apiNewsAdapter.findNewsItemByKeyword("preso");

        assertThrows(RuntimeException.class, () -> newsItemOptional.get());
    }

}
