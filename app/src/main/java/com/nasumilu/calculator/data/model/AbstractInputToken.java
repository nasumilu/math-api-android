package com.nasumilu.calculator.data.model;

import com.nasumilu.calculator.data.NodeBuilder;

/**
 * An immutable InputToken
 *
 * @param <T>
 */
abstract public class AbstractInputToken<T> implements InputToken<T>, NodeBuilder {

    /**
     * The InputToken 's value
     */
    protected T value;

    /**
     * Immutable constructor;
     * @param value
     */
    public AbstractInputToken(T value) { this.value = value; }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getValue() {
        return this.value;
    }

}
