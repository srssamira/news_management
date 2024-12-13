package com.news_management.br.news_management.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.news_management.br.news_management.app.adapters.NewsVerificationAdapter;
import com.news_management.br.news_management.domain.dtos.NewsVerificationRequestDTO;
import com.news_management.br.news_management.domain.dtos.NewsVerificationResponseDTO;
import com.news_management.br.news_management.domain.usecases.NewsVerificationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NewsVerificationAdapterTest {

    @InjectMocks
    private NewsVerificationAdapter newsVerificationAdapter;

    @Mock
    private NewsVerificationUseCase newsVerificationUseCase;

    private NewsVerificationRequestDTO newsVerificationRequestDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        newsVerificationRequestDTO = new NewsVerificationRequestDTO();
        newsVerificationRequestDTO.setHaveCommunicationVehicle(true);
        newsVerificationRequestDTO.setHaveAuthor(true);
        newsVerificationRequestDTO.setHavePublicationDate(true);
        newsVerificationRequestDTO.setHaveTrustedSource(true);
        newsVerificationRequestDTO.setHaveSensacionalistLanguage(false);
    }

    @Test
    public void shouldReturnHighConfidenceWhenNewsIsValid() {
        NewsVerificationResponseDTO expectedResponse = new NewsVerificationResponseDTO();
        expectedResponse.setClassification("HIGH CONFIDENCE");
        expectedResponse.setScore(11);
        expectedResponse.setUrl("https://www.example.com/news");

        String url = "https://www.example.com/news";

        when(newsVerificationUseCase.verifyNews(newsVerificationRequestDTO, url)).thenReturn(expectedResponse);

        NewsVerificationResponseDTO actualResponse = newsVerificationAdapter.verifyNews(newsVerificationRequestDTO, url);

        assertEquals(expectedResponse.getClassification(), actualResponse.getClassification());
        assertEquals(expectedResponse.getScore(), actualResponse.getScore());
        assertEquals(expectedResponse.getUrl(), actualResponse.getUrl());
    }
}
