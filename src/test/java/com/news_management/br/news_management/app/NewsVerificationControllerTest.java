package com.news_management.br.news_management.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_management.br.news_management.app.adapters.NewsVerificationAdapter;
import com.news_management.br.news_management.app.ports.NewsVerificationController;
import com.news_management.br.news_management.domain.dtos.NewsVerificationRequestDTO;
import com.news_management.br.news_management.domain.dtos.NewsVerificationResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class NewsVerificationControllerTest {

    @InjectMocks
    private NewsVerificationController newsVerificationController;

    @Mock
    private NewsVerificationAdapter newsVerificationAdapter;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private NewsVerificationRequestDTO newsVerificationRequestDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(newsVerificationController).build();
        objectMapper = new ObjectMapper();

        newsVerificationRequestDTO = new NewsVerificationRequestDTO();
        newsVerificationRequestDTO.setHaveCommunicationVehicle(true);
        newsVerificationRequestDTO.setHaveAuthor(true);
        newsVerificationRequestDTO.setHavePublicationDate(true);
        newsVerificationRequestDTO.setHaveTrustedSource(true);
        newsVerificationRequestDTO.setHaveSensacionalistLanguage(false);
    }

    @Test
    public void shouldReturnHighConfidenceWhenNewsIsValid() throws Exception {
        NewsVerificationResponseDTO response = new NewsVerificationResponseDTO();
        response.setClassification("HIGH CONFIDENCE");
        response.setScore(11);
        response.setUrl("https://www.example.com/news");

        when(newsVerificationAdapter.verifyNews(newsVerificationRequestDTO, "https://www.example.com/news"))
                .thenReturn(response);

        mockMvc.perform(post("/news/verification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsVerificationRequestDTO))
                        .param("url", "https://www.example.com/news"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    NewsVerificationResponseDTO actualResponse = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            NewsVerificationResponseDTO.class
                    );
                    assertEquals("HIGH CONFIDENCE", actualResponse.getClassification());
                    assertEquals(11, actualResponse.getScore());
                    assertEquals("https://www.example.com/news", actualResponse.getUrl());
                });
    }
}

