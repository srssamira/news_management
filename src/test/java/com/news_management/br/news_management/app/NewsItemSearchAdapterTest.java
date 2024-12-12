package com.news_management.br.news_management.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_management.br.news_management.app.adapters.ExternalApiNewsAdapter;
import com.news_management.br.news_management.app.infra.NewsItemRepository;
import com.news_management.br.news_management.app.infra.NewsNotFoundException;
import com.news_management.br.news_management.domain.dtos.NewsItemSearchDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import com.news_management.br.news_management.app.adapters.NewsItemSearchAdapter;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class NewsItemSearchAdapterTest {

    @Mock
    private NewsItemRepository newsItemRepository;

    @Mock
    private ExternalApiNewsAdapter externalApiNewsAdapter;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private NewsItemSearchAdapter newsItemSearchAdapter;


    NewsItem newsItem;

    NewsItemSearchDTO newsItemSearchDTO;


    @BeforeEach
    void setUp() {

        newsItemSearchAdapter = new NewsItemSearchAdapter(newsItemRepository, externalApiNewsAdapter, mapper);

        newsItemSearchDTO = new NewsItemSearchDTO();

        newsItem = new NewsItem();

        newsItem.setText("Quinze pessoas foram presas pela Polícia Federal, na terça-feira (10), " +
                "na Bahia, em São Paulo e em Goiás, durante uma operação contra uma organização " +
                "criminosa suspeita de atuar em fraudes licitatórias, desvio de recursos públicos, " +
                "corrupção e lavagem de dinheiro.");

        newsItemSearchDTO.setText("Quinze pessoas foram presas pela Polícia Federal, na terça-feira (10), " +
                "na Bahia, em São Paulo e em Goiás, durante uma operação contra uma organização " +
                "criminosa suspeita de atuar em fraudes licitatórias, desvio de recursos públicos, " +
                "corrupção e lavagem de dinheiro.");

    }


    @Test
    public void testWhenNewsHasFoundInDataBase() {

        String keyword = "lavagem de dinheiro";

        when(newsItemRepository.findByTextContainingIgnoreCase(keyword))
                .thenReturn(Optional.of(newsItem));

        when(mapper.convertValue(newsItem, NewsItemSearchDTO.class))
                .thenReturn(newsItemSearchDTO);

        newsItemSearchDTO = newsItemSearchAdapter.findByKeyword(keyword);


        assertNotNull(newsItemSearchDTO);
        assertEquals(newsItem.getText(), newsItemSearchDTO.getText());

        verify(newsItemRepository, times(1)).findByTextContainingIgnoreCase(keyword);
        verify(externalApiNewsAdapter, times(0)).findNewsItemByKeyword(anyString());
    }

    @Test
    public void testWhenNewsHasNotFoundInDataBaseButFoundInExternalApi() {
        String keyword = "aquecimento global";

        NewsItem apiNewsItem = new NewsItem();

        apiNewsItem.setText("notícias sobre o aquecimento global");

        NewsItemSearchDTO apiNewsItemSearchDTO = new NewsItemSearchDTO();
        apiNewsItemSearchDTO.setText("notícias sobre o aquecimento global");

        when(newsItemRepository.findByTextContainingIgnoreCase(keyword))
                .thenReturn(Optional.empty());

        when(externalApiNewsAdapter.findNewsItemByKeyword(keyword))
                .thenReturn(Optional.of(apiNewsItem));

        when(newsItemRepository.save(apiNewsItem)).thenReturn(apiNewsItem);
        when(mapper.convertValue(apiNewsItem, NewsItemSearchDTO.class))
                .thenReturn(apiNewsItemSearchDTO);

        NewsItemSearchDTO newsFound = newsItemSearchAdapter.findByKeyword(keyword);

        assertNotNull(newsFound);
        assertEquals(apiNewsItem.getText(), newsFound.getText());

        verify(newsItemRepository, times(1)).findByTextContainingIgnoreCase(keyword);
        verify(externalApiNewsAdapter, times(1)).findNewsItemByKeyword(keyword);
        verify(newsItemRepository, times(1)).save(apiNewsItem);
    }

    @Test
    public void testWhenHasNotFoundInDataBaseAndNotFoundInExternalApi() {
        String keyword = "abiabdhsbdasidbas";

        when(newsItemRepository.findByTextContainingIgnoreCase(keyword))
                .thenReturn(Optional.empty());

        when(externalApiNewsAdapter.findNewsItemByKeyword(keyword))
                .thenReturn(Optional.empty());

        NewsNotFoundException newsNotFoundException = assertThrows(NewsNotFoundException.class,
                () -> {
                    newsItemSearchAdapter.findByKeyword(keyword);
                });

        assertEquals("newsItem not found", newsNotFoundException.getMessage());

        verify(newsItemRepository, times(1)).findByTextContainingIgnoreCase(keyword);
        verify(externalApiNewsAdapter, times(1)).findNewsItemByKeyword(keyword);
    }


}
