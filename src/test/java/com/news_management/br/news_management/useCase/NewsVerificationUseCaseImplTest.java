package com.news_management.br.news_management.useCase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.news_management.br.news_management.domain.dtos.NewsVerificationRequestDTO;
import com.news_management.br.news_management.domain.dtos.NewsVerificationResponseDTO;
import com.news_management.br.news_management.domain.usecases.impl.NewsVerificationUseCaseImpl;
import org.junit.jupiter.api.Test;

public class NewsVerificationUseCaseImplTest {

    @Test
    public void shouldReturnHighConfidenceWhenNewsIsValid() {
        NewsVerificationRequestDTO request = new NewsVerificationRequestDTO();
        request.setHaveCommunicationVehicle(true);
        request.setHaveAuthor(true);
        request.setHavePublicationDate(true);
        request.setHaveTrustedSource(true);
        request.setHaveSensacionalistLanguage(false);

        String url = "https://www.example.com/news";

        NewsVerificationUseCaseImpl useCase = new NewsVerificationUseCaseImpl();

        NewsVerificationResponseDTO response = useCase.verifyNews(request, url);

        assertEquals("HIGH CONFIDENCE", response.getClassification());
        assertEquals(11, response.getScore());
        assertEquals(url, response.getUrl());
    }

    @Test
    public void shouldReturnLowConfidenceWhenNewsHasNoTrustedSource() {
        NewsVerificationRequestDTO request = new NewsVerificationRequestDTO();
        request.setHaveCommunicationVehicle(false);
        request.setHaveAuthor(false);
        request.setHavePublicationDate(false);
        request.setHaveTrustedSource(false);
        request.setHaveSensacionalistLanguage(true);

        String url = "https://www.example.com/news";

        NewsVerificationUseCaseImpl useCase = new NewsVerificationUseCaseImpl();

        NewsVerificationResponseDTO response = useCase.verifyNews(request, url);

        assertEquals("HIGH SUSPICION", response.getClassification());
        assertEquals(-6, response.getScore());
        assertEquals(url, response.getUrl());
    }

    @Test
    public void shouldReturnMediumConfidenceWhenScoreIsInRange() {
        NewsVerificationRequestDTO request = new NewsVerificationRequestDTO();
        request.setHaveCommunicationVehicle(true);
        request.setHaveAuthor(false);
        request.setHavePublicationDate(false);
        request.setHaveTrustedSource(true);
        request.setHaveSensacionalistLanguage(true);

        String url = "https://www.example.com/news";

        NewsVerificationUseCaseImpl useCase = new NewsVerificationUseCaseImpl();

        NewsVerificationResponseDTO response = useCase.verifyNews(request, url);

        assertEquals("MEDIUM CONFIDENCE", response.getClassification());
        assertEquals(8, response.getScore());
        assertEquals(url, response.getUrl());
    }
}
