package com.besedo.processing.el.expressions;

import com.besedo.processing.el.Context;
import com.besedo.processing.el.Expression;

import java.util.Objects;


public class ConstantExpression<T extends Comparable> implements Expression<T> {

    private final T value;

    public ConstantExpression(T value) {
        this.value = value;
    }

    @Override
    public T evaluate(Context context) {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstantExpression<?> that = (ConstantExpression<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ConstantExpression{" +
                "value=" + value +
                '}';
    }
}
