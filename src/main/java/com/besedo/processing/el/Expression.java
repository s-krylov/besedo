package com.besedo.processing.el;



public interface Expression<R> {

    R evaluate(Context context);

}
