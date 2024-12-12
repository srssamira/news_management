package com.news_management.br.news_management.app;

import com.news_management.br.news_management.app.adapters.NewsItemSearchAdapter;
import com.news_management.br.news_management.app.infra.NewsNotFoundException;
import com.news_management.br.news_management.app.ports.NewsController;
import com.news_management.br.news_management.domain.dtos.NewsItemSearchDTO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class NewsControllerTest {

    @InjectMocks
    private NewsController newsController;

    @Mock
    private NewsItemSearchAdapter newsItemSearchAdapter;

    private NewsItemSearchDTO newsItemSearchDTO;

    @BeforeEach
    public void setUp() {
        newsItemSearchDTO = new NewsItemSearchDTO();

        newsItemSearchDTO.setId(1L);
        newsItemSearchDTO.setText("Pela segunda vez no ano, presidente Lula recebe o presidente do IBGE");
        newsItemSearchDTO.setUrl("http://agenciadenoticias.ibge.gov.br/agencia-noticias/2012-agencia-de-noticias/noticias/42084-pela-segunda-vez-no-ano-presidente-lula-recebe-em-audiencia-o-presidente-do-ibge");
        newsItemSearchDTO.setPublicationDate(LocalDate.now());
    }

    @Test
    void testWhenNewsHasFoundWithSucess() {
        when(newsItemSearchAdapter.findByKeyword("segunda")).thenReturn(newsItemSearchDTO);

        ResponseEntity<?> newsSearched = newsController.searchNews("segunda");

        assertEquals(HttpStatus.OK, newsSearched.getStatusCode());
        assertNotNull(newsSearched.getBody());
        assertEquals(newsSearched.getBody(), newsItemSearchDTO);

        verify(newsItemSearchAdapter, times(1)).findByKeyword("segunda");
    }

    @Test
    void testWhenNewsHasNotFound() {
        when(newsItemSearchAdapter.findByKeyword("segunda"))
                .thenThrow(new NewsNotFoundException("news not found"));

        ResponseEntity<?> newsSearched = newsController.searchNews("segunda");

        assertEquals(HttpStatus.NOT_FOUND, newsSearched.getStatusCode());
        assertNotNull(newsSearched.getBody());
        assertEquals(Map.of("error", "news not found"), newsSearched.getBody());
        verify(newsItemSearchAdapter, times(1)).findByKeyword("segunda");
    }


}
