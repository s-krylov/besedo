package com.besedo.processing.el.expressions;


import com.besedo.processing.el.Expression;
import com.besedo.processing.el.expressions.util.EmptyContext;
import com.besedo.processing.el.expressions.util.ThrowExceptionOnEvaluation;
import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanAlgebraExpressionTest {

    private final Expression<Boolean> trueBoolean = new ConstantExpression<>(true);
    private final Expression<Boolean> falseBoolean = new ConstantExpression<>(false);
    private final Expression<Boolean> shouldNotBeEvaluated = new ThrowExceptionOnEvaluation<>(() ->
            new RuntimeException("shouldn't be evaluated"));

    @Test
    public void testAnd() {
        BooleanAlgebraExpression booleanAlgebraExpression = new BooleanAlgebraExpression("and", trueBoolean, trueBoolean);
        assertTrue(booleanAlgebraExpression.evaluate(EmptyContext.EMPTY));

        booleanAlgebraExpression = new BooleanAlgebraExpression("and", trueBoolean, falseBoolean);
        assertFalse(booleanAlgebraExpression.evaluate(EmptyContext.EMPTY));

        booleanAlgebraExpression = new BooleanAlgebraExpression("and", falseBoolean, shouldNotBeEvaluated);
        assertFalse(booleanAlgebraExpression.evaluate(EmptyContext.EMPTY));
    }

    @Test(expected = RuntimeException.class)
    public void testError() {
        BooleanAlgebraExpression booleanAlgebraExpression = new BooleanAlgebraExpression("or", falseBoolean, shouldNotBeEvaluated);
        booleanAlgebraExpression.evaluate(EmptyContext.EMPTY);
    }

    @Test
    public void testOr() {
        BooleanAlgebraExpression booleanAlgebraExpression = new BooleanAlgebraExpression("or", trueBoolean, shouldNotBeEvaluated);
        assertTrue(booleanAlgebraExpression.evaluate(EmptyContext.EMPTY));

        booleanAlgebraExpression = new BooleanAlgebraExpression("or", falseBoolean, falseBoolean);
        assertFalse(booleanAlgebraExpression.evaluate(EmptyContext.EMPTY));

        booleanAlgebraExpression = new BooleanAlgebraExpression("or", falseBoolean, trueBoolean);
        assertTrue(booleanAlgebraExpression.evaluate(EmptyContext.EMPTY));
    }
}
