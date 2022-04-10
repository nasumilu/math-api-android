package com.nasumilu.calculator.data.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An InputToken which MUST hold a numeric value
 *
 * @param <T>
 */
final public class NumericToken<T extends Number> extends AbstractInputToken<T> {

    /**
     * Constructs an immutable NumericToken
     * @param value
     */
    public NumericToken(T value) {
        super(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element build(Document document) {
        var element = document.createElement("mn");
        element.appendChild(document.createTextNode(this.value.toString()));
        return element;
    }
}
