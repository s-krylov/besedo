package com.besedo.processing.el.expressions.util;

import com.besedo.processing.el.Context;
import com.besedo.processing.el.Expression;

import java.util.function.Supplier;


public class ThrowExceptionOnEvaluation<T, E extends RuntimeException> implements Expression<T> {

    private final Supplier<E> exceptionSupplier;

    public ThrowExceptionOnEvaluation(Supplier<E> exceptionSupplier) {
        this.exceptionSupplier = exceptionSupplier;
    }

    @Override
    public T evaluate(Context context) {
        throw exceptionSupplier.get();
    }
}
