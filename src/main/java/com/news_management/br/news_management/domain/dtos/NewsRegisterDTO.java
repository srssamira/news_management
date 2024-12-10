package com.news_management.br.news_management.domain.dtos;

import java.time.LocalDate;

public class NewsRegisterDTO {
    private String url;
    private String text;
    private LocalDate publicationDate;

    public NewsRegisterDTO() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
}
