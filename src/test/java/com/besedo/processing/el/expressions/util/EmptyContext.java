package com.besedo.processing.el.expressions.util;

import com.besedo.processing.el.Context;


public enum EmptyContext implements Context {
    EMPTY;

    @Override
    public Object getValue(String name) {
        return null;
    }
}
