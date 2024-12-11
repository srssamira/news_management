package com.news_management.br.news_management.domain.usecases;

import com.news_management.br.news_management.domain.dtos.NewsRegisterDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import com.news_management.br.news_management.app.infra.NewsItemRepository;
import com.news_management.br.news_management.domain.usecases.impl.NewsRegisterUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NewsRegisterUseCaseImplTest {

    private NewsItemRepository newsItemRepository;

    private NewsRegisterUseCase newsRegisterUseCase;

    @BeforeEach
    void setUp() {

        newsItemRepository = mock(NewsItemRepository.class);

        newsRegisterUseCase = new NewsRegisterUseCaseImpl(newsItemRepository);
    }

    @Test
    void registerNews_success() {

        NewsRegisterDTO newsRegisterDTO = new NewsRegisterDTO();
        newsRegisterDTO.setUrl("https://www.example.com/news");
        newsRegisterDTO.setText("Texto da notícia");
        newsRegisterDTO.setPublicationDate(LocalDate.now());

        NewsItem savedNewsItem = new NewsItem();
        savedNewsItem.setId(1L);

        // comportamento do mock
        when(newsItemRepository.save(any(NewsItem.class))).thenReturn(savedNewsItem);

        Long newsId = newsRegisterUseCase.registerNews(newsRegisterDTO);

        assertNotNull(newsId);
        assertEquals(1L, newsId);

        // captura os argumentos para validar os dados persistidos
        ArgumentCaptor<NewsItem> captor = ArgumentCaptor.forClass(NewsItem.class);
        verify(newsItemRepository).save(captor.capture());

        NewsItem capturedNewsItem = captor.getValue();
        assertEquals("https://www.example.com/news", capturedNewsItem.getUrl());
        assertEquals("News Test", capturedNewsItem.getText());
        assertEquals(LocalDate.now(), capturedNewsItem.getPublicationDate());
    }

    @Test
    void registerNews_missingUrl_throwsException() {

        NewsRegisterDTO newsRegisterDTO = new NewsRegisterDTO();
        newsRegisterDTO.setText("Texto da notícia");
        newsRegisterDTO.setPublicationDate(LocalDate.now());


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                newsRegisterUseCase.registerNews(newsRegisterDTO)
        );
        assertEquals("The 'url' field is mandatory.", exception.getMessage());

        verifyNoInteractions(newsItemRepository);
    }

    @Test
    void registerNews_missingText_throwsException() {

        NewsRegisterDTO newsRegisterDTO = new NewsRegisterDTO();
        newsRegisterDTO.setUrl("https://www.example.com/news");
        newsRegisterDTO.setPublicationDate(LocalDate.now());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                newsRegisterUseCase.registerNews(newsRegisterDTO)
        );
        assertEquals("The 'text' field is mandatory.", exception.getMessage());

        verifyNoInteractions(newsItemRepository);
    }

    @Test
    void registerNews_invalidPublicationDate_throwsException() {

        NewsRegisterDTO newsRegisterDTO = new NewsRegisterDTO();
        newsRegisterDTO.setUrl("https://www.example.com/news");
        newsRegisterDTO.setText("Texto da notícia");
        newsRegisterDTO.setPublicationDate(LocalDate.now().plusDays(1));


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                newsRegisterUseCase.registerNews(newsRegisterDTO)
        );
        assertEquals("Invalid publication date.", exception.getMessage());

        verifyNoInteractions(newsItemRepository);
    }

}
