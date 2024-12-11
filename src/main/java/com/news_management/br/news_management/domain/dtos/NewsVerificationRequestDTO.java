package com.news_management.br.news_management.domain.dtos;

public class NewsVerificationRequestDTO {

    private boolean haveCommunicationVehicle;
    private boolean haveAuthor;
    private boolean havePublicationDate;
    private boolean haveTrustedSource;
    private boolean haveSensacionalistLanguage;

    public NewsVerificationRequestDTO() {
    }

    public boolean isHaveCommunicationVehicle() {
        return haveCommunicationVehicle;
    }

    public void setHaveCommunicationVehicle(boolean haveCommunicationVehicle) {
        this.haveCommunicationVehicle = haveCommunicationVehicle;
    }

    public boolean isHaveAuthor() {
        return haveAuthor;
    }

    public void setHaveAuthor(boolean haveAuthor) {
        this.haveAuthor = haveAuthor;
    }

    public boolean isHavePublicationDate() {
        return havePublicationDate;
    }

    public void setHavePublicationDate(boolean havePublicationDate) {
        this.havePublicationDate = havePublicationDate;
    }

    public boolean isHaveTrustedSource() {
        return haveTrustedSource;
    }

    public void setHaveTrustedSource(boolean haveTrustedSource) {
        this.haveTrustedSource = haveTrustedSource;
    }

    public boolean isHaveSensacionalistLanguage() {
        return haveSensacionalistLanguage;
    }

    public void setHaveSensacionalistLanguage(boolean haveSensacionalistLanguage) {
        this.haveSensacionalistLanguage = haveSensacionalistLanguage;
    }
}