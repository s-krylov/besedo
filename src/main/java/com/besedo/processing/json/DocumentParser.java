package com.besedo.processing.json;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;



public class DocumentParser {

    private List<Consumer<Document>> documentConsumers = new ArrayList<>();

    public void parse(Reader reader)  {
        JsonFactory jsonFactory = new JsonFactory();
        try {
            JsonParser jsonParser = jsonFactory.createParser(reader);
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                return;
            }
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                if (jsonParser.currentToken() != JsonToken.START_OBJECT) {
                    continue;
                }
                Document document = parseDocument(jsonParser);
                notifyConsumers(document);
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Error occurred during parsing json", ioe);
        }
    }

    private Document parseDocument(JsonParser jsonParser) throws IOException {
        DocumentBuilder documentBuilder = new DocumentBuilder();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            switch (fieldName.toUpperCase()) {
                case "ID":
                    jsonParser.nextToken();
                    documentBuilder.setId(jsonParser.getIntValue());
                    break;
                case "PRICE":
                    jsonParser.nextToken();
                    documentBuilder.setPrice(jsonParser.getFloatValue());
                    break;
                case "DESCRIPTION":
                    jsonParser.nextToken();
                    documentBuilder.setDescription(jsonParser.getText());
                    break;
                case "TITLE":
                    jsonParser.nextToken();
                    documentBuilder.setTitle(jsonParser.getText());
                    break;
                default: throw new IllegalStateException("Illegal field name: " + fieldName);
            }
        }
        return documentBuilder.build();
    }

    public boolean addDocumentConsumer(Consumer<Document> documentConsumer) {
        return documentConsumers.add(documentConsumer);
    }

    private void notifyConsumers(Document document) {
        documentConsumers.forEach(consumer -> consumer.accept(document));
    }
}
