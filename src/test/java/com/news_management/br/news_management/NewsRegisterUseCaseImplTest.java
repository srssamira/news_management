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

}
