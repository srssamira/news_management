package com.news_management.br.news_management.app;

import com.news_management.br.news_management.domain.dtos.NewsRegisterDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewsRegisterDTOTest {

    @Test
    void shouldThrowExceptionWhenUrlIsNull() {

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new NewsRegisterDTO(null, "Valid text", LocalDate.now()));


        assertEquals("The 'url' field is mandatory.", exception.getMessage());
    }
    g
}
