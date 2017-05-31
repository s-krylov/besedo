package com.besedo.processing.json;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.function.BiConsumer;


public class StatisticWriter implements BiConsumer<Document, String>, Closeable {

    private final JsonGenerator jsonGenerator;

    public StatisticWriter(Writer writer) throws IOException {
        jsonGenerator = new JsonFactory().createGenerator(writer);
        jsonGenerator.writeStartArray();
    }


    @Override
    public void accept(Document document, String s) {
        try {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("documentId", document.getId());
            jsonGenerator.writeStringField("flag", s);
            jsonGenerator.writeEndObject();
        } catch (IOException ioe) {
            throw new RuntimeException("Can't serialize document into json: " + document, ioe);
        }
    }

    @Override
    public void close() throws IOException {
        jsonGenerator.writeEndArray();
        jsonGenerator.close();
    }
}
