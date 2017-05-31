package com.besedo.processing.el.expressions;

import com.besedo.processing.el.Context;
import com.besedo.processing.el.Expression;

import java.util.Objects;


public class VariableBindingExpression<T> implements Expression<T> {

    private final String name;

    public VariableBindingExpression(String name) {
        this.name = name;
    }

    @Override
    public T evaluate(Context context) {
        return (T) context.getValue(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariableBindingExpression<?> that = (VariableBindingExpression<?>) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "VariableBindingExpression{" +
                "name='" + name + '\'' +
                '}';
    }
}
