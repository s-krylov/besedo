package com.besedo.processing.el.expressions.util;


import com.besedo.processing.el.Context;

public class ConstantContext implements Context {

    private final Object object;

    public ConstantContext(Object object) {
        this.object = object;
    }

    @Override
    public Object getValue(String name) {
        return object;
    }
}
