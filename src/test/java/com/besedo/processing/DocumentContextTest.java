package com.besedo.processing;


import com.besedo.processing.el.Context;
import com.besedo.processing.json.DocumentBuilder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;


public class DocumentContextTest {

    private Context context;

    @Before
    public void setup() {
        context = new DocumentContext(
                new DocumentBuilder()
                        .setId(1)
                        .setPrice(100)
                        .setTitle("dummy")
                        .setDescription("none")
                        .build());
    }

    @Test(expected = IllegalArgumentException .class)
    public void testGetValueForNotExistingKey() {
        context.getValue("_notExist");
    }

    @Test
    public void testGetDocumentFields() {
        assertEquals(1, context.getValue("id"));
        assertEquals(0, new BigDecimal(100).compareTo((BigDecimal) context.getValue("price")));
        assertEquals("dummy", context.getValue("title"));
        assertEquals("none", context.getValue("description"));
    }
}
