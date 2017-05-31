package com.besedo.processing.json;


import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class StatisticWriterTest {

    private StringWriter stringWriter;

    @Before
    public void setup() {
        stringWriter = new StringWriter();
    }

    @Test
    public void testNoStatistic() throws IOException {
        try (StatisticWriter statisticWriter = new StatisticWriter(stringWriter)) {
        }
        assertEquals("[]", stringWriter.toString());
    }

    @Test
    public void testStatistic() throws IOException {
        try (StatisticWriter statisticWriter = new StatisticWriter(stringWriter)) {
            statisticWriter.accept(createDocument(1, 200, "first"), "result");
            statisticWriter.accept(createDocument(2, 300, "second"), "another one");
        }
        assertEquals("[{\"documentId\":1,\"flag\":\"result\"},{\"documentId\":2,\"flag\":\"another one\"}]", stringWriter.toString());
    }

    private Document createDocument(int id, float price, String title) {
        return new DocumentBuilder()
                .setId(id)
                .setPrice(price)
                .setTitle(title)
                .setDescription("none")
                .build();
    }
}
