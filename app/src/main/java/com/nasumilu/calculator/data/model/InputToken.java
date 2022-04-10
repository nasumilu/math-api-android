package com.nasumilu.calculator.data.model;

/**
 * An extremely basic container for holding the user's input
 *
 * @param <T>
 */
public interface InputToken<T> {

    /**
     * Get the InputToken value
     * @return T
     */
    T getValue();
}
