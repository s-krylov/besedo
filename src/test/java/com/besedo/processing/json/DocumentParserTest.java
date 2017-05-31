package com.besedo.processing.json;


import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DocumentParserTest {

    private final String jsonSource = "[\n" +
            "{\"price\":3800,\"description\":\"strangers bmw conspiracy\",\"id\":1,\"title\":\"up is up courtesy correct\"},\n" +
            "{\"price\":5623,\"description\":\"woods husband. strangers\",\"id\":2,\"title\":\"hearing message depending war public\"}\n" +
            "]";

    @Test
    public void testEmptyArray() {
        DocumentParser documentParser = new DocumentParser();
        documentParser.addDocumentConsumer(document -> {throw new RuntimeException("No documents allowed");});
        documentParser.parse(new StringReader("[]"));
    }

    @Test
    public void testEmptySource() {
        DocumentParser documentParser = new DocumentParser();
        documentParser.addDocumentConsumer(document -> {throw new RuntimeException("No documents allowed");});
        documentParser.parse(new StringReader(""));
    }


    @Test
    public void testParse() {
        List<Document> documents = new ArrayList<>();
        DocumentParser documentParser = new DocumentParser();
        documentParser.addDocumentConsumer(documents::add);
        documentParser.parse(new StringReader(jsonSource));
        assertEquals(
                Arrays.asList(
                        createDocument(1, 3800, "up is up courtesy correct", "strangers bmw conspiracy"),
                        createDocument(2, 5623, "hearing message depending war public", "woods husband. strangers")
                        ), documents);
    }

    private Document createDocument(int id, float price, String title, String description) {
        return new DocumentBuilder()
                .setId(id)
                .setPrice(price)
                .setTitle(title)
                .setDescription(description)
                .build();
    }
}
