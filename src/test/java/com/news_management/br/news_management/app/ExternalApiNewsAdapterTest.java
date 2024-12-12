package com.news_management.br.news_management.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_management.br.news_management.app.adapters.ExternalApiNewsAdapter;
import com.news_management.br.news_management.domain.dtos.ApiReceivedDataDTO;
import com.news_management.br.news_management.domain.dtos.NewsAPIResponseDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
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

    private ApiReceivedDataDTO apiReceivedDataDTO;

    @BeforeEach
    void setUp() {
        apiResponse = new NewsAPIResponseDTO();
        apiResponse.setLink("https://newsapi.com/");
        apiResponse.setIntroducao("Bolsonaro foi preso");
        apiResponse.setData_publicacao("12/12/2024 09:00:00");

        newsItem = new NewsItem();
        newsItem.setUrl("https://newsapi.com/");
        newsItem.setText("Bolsonaro foi preso");
        newsItem.setPublicationDate(LocalDate.now());

        apiReceivedDataDTO = new ApiReceivedDataDTO();
        apiReceivedDataDTO.setItems(Collections.singletonList(apiResponse));

    }

    @Test
    void testWhenNewsHasFoundWithSuccess() {
        ResponseEntity<ApiReceivedDataDTO> responseEntity = ResponseEntity.ok(apiReceivedDataDTO);

        when(restTemplate.getForEntity(anyString(), eq(ApiReceivedDataDTO.class)))
                .thenReturn(responseEntity);

        Optional<NewsItem> newsItemOptional = apiNewsAdapter.findNewsItemByKeyword("preso");

        assertTrue(newsItemOptional.isPresent());
        assertEquals(newsItem.getUrl(), newsItemOptional.get().getUrl());
        assertEquals(newsItem.getText(), newsItemOptional.get().getText());
        assertEquals(newsItem.getPublicationDate(), newsItemOptional.get().getPublicationDate());
    }

    @Test
    void testWhenNewsHasNotFound() throws Exception {
        ResponseEntity<ApiReceivedDataDTO> apiResponses = ResponseEntity.notFound().build();

        when(restTemplate.getForEntity(anyString(), eq(ApiReceivedDataDTO.class)))
            .thenReturn(apiResponses);

        Optional<NewsItem> newsItemOptional = apiNewsAdapter.findNewsItemByKeyword("preso");

        assertFalse(newsItemOptional.isPresent());
        verify(restTemplate, times(1))
                .getForEntity(anyString(), eq(ApiReceivedDataDTO.class));
        verify(mapper, never())
                .convertValue(any(NewsAPIResponseDTO.class), eq(NewsItem.class));
    }


}
