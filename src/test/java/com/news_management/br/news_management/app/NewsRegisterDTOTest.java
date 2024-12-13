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

    @Test
    void shouldThrowExceptionWhenTextIsBlank() {

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new NewsRegisterDTO("http://valid.url", "", LocalDate.now()));

        assertEquals("The 'text' field is mandatory.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPublicationDateIsInTheFuture() {

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new NewsRegisterDTO("http://valid.url", "Valid text", LocalDate.now().plusDays(1)));

        assertEquals("The publication date is invalid.", exception.getMessage());
    }

    @Test
    void shouldCreateValidNewsRegisterDTO() {

        NewsRegisterDTO dto = new NewsRegisterDTO("http://valid.url", "Valid text", LocalDate.now());

        assertEquals("http://valid.url", dto.getUrl());
        assertEquals("Valid text", dto.getText());
        assertEquals(LocalDate.now(), dto.getPublicationDate());
    }
}
