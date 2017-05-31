package com.besedo.processing.el.expressions;


import com.besedo.processing.DocumentContext;
import com.besedo.processing.json.Document;
import com.besedo.processing.json.DocumentBuilder;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class VariableBindingExpressionTest {

    private VariableBindingExpression<String> title = new VariableBindingExpression<>("title");
    private VariableBindingExpression<BigDecimal> price = new VariableBindingExpression<>("price");

    @Test
    public void testBinding() {
        assertEquals("the Article", title.evaluate(new DocumentContext(createDocument(100, "the Article"))));
        assertNotEquals("the Article", price.evaluate(new DocumentContext(createDocument(100, "the Article"))));

        assertEquals(0, new BigDecimal(100).compareTo(price.evaluate(new DocumentContext(createDocument(100, "the Article")))));
        assertNotEquals(0, title.evaluate(new DocumentContext(createDocument(100, "the Article"))));
    }

    private Document createDocument(float price, String title) {
        return new DocumentBuilder()
                .setId(1)
                .setPrice(price)
                .setTitle(title)
                .setDescription("none")
                .build();
    }
}
