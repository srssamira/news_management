package com.news_management.br.news_management.app;

import com.news_management.br.news_management.app.adapters.NewsRegisterAdapter;
import com.news_management.br.news_management.domain.dtos.NewsRegisterDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import com.news_management.br.news_management.domain.usecases.NewsItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class NewsRegisterAdapterTest {

    public NewsRegisterAdapterTest(NewsItemRepository mockRepository) {
    }

    @Test
    void shouldRegisterNewsSuccessfully() {

        NewsItemRepository mockRepository = Mockito.mock(NewsItemRepository.class);

        NewsRegisterAdapterTest adapter = new NewsRegisterAdapterTest(mockRepository);

        NewsRegisterDTO dto = new NewsRegisterDTO("http://valid.url", "Valid text", LocalDate.now());

        NewsItem mockNewsItem = new NewsItem();
        mockNewsItem.setId(1L); // Define um ID para simular o retorno do banco.
        mockNewsItem.setUrl(dto.getUrl());
        mockNewsItem.setText(dto.getText());
        mockNewsItem.setPublicationDate(dto.getPublicationDate());

        when(mockRepository.save(any(NewsItem.class))).thenReturn(mockNewsItem);

        Long id = adapter.registerNews(dto);

        assertNotNull(id);
        assertEquals(1L, id);

        verify(mockRepository, times(1)).save(any(NewsItem.class));
    }

    private Long registerNews(NewsRegisterDTO dto) {
    }

    @Test
    void shouldThrowExceptionWhenRepositoryFails() {

        NewsItemRepository mockRepository = Mockito.mock(NewsItemRepository.class);

        NewsRegisterAdapter adapter = new NewsRegisterAdapter(mockRepository);

        NewsRegisterDTO dto = new NewsRegisterDTO("http://valid.url", "Valid text", LocalDate.now());

        when(mockRepository.save(any(NewsItem.class))).thenReturn(null);

        Exception exception = assertThrows(NullPointerException.class, () ->
                adapter.registerNews(dto));

        assertEquals("Failed to save the news item.", exception.getMessage());
    }

}
