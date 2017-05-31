package com.besedo.processing;

import com.besedo.processing.el.Context;
import com.besedo.processing.json.Document;

import java.util.function.Function;


public class DocumentContext implements Context {

    private final Document document;

    public DocumentContext(Document document) {
        this.document = document;
    }

    @Override
    public Object getValue(String name) {
        return Property.valueOf(name.toUpperCase()).apply(document);
    }

    private enum Property implements Function<Document, Object> {
        ID(Document::getId),
        TITLE(Document::getTitle),
        PRICE(Document::getPrice),
        DESCRIPTION(Document::getDescription);

        private final Function<Document, Object> method;

        Property(Function<Document, Object> method) {
            this.method = method;
        }

        @Override
        public Object apply(Document document) {
            return method.apply(document);
        }

    }
}
