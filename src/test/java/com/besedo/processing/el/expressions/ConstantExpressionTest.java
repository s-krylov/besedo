package com.besedo.processing.el.expressions;


import com.besedo.processing.el.expressions.util.EmptyContext;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConstantExpressionTest {

    @Test
    public void testConstant() {
        ConstantExpression<String> constantExpression = new ConstantExpression<>("temporal");
        assertEquals("temporal", constantExpression.evaluate(EmptyContext.EMPTY));
    }
}
