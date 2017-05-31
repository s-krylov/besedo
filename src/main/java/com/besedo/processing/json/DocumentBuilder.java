package com.besedo.processing.json;

import java.util.Objects;


public class DocumentBuilder {

    private final Document document;

    public DocumentBuilder() {
        document = new Document();
    }

    public DocumentBuilder setId(int id) {
        document.setId(id);
        return this;
    }

    public DocumentBuilder setTitle(String title) {
        document.setTitle(title);
        return this;
    }

    public DocumentBuilder setPrice(float price) {
        document.setPrice(price);
        return this;
    }

    public DocumentBuilder setDescription(String description) {
        document.setDescription(description);
        return this;
    }

    public Document build() {
        Objects.requireNonNull(document.getId());
        Objects.requireNonNull(document.getDescription());
        Objects.requireNonNull(document.getPrice());
        Objects.requireNonNull(document.getTitle());
        return document;
    }
}
