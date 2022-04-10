package com.nasumilu.calculator.data.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An InputToken which represents an operation. In the context of this application
 * an operation is any valid non-numeric value.
 *
 */
public class OperationToken extends AbstractInputToken<String> {

    /**
     * Constructs an immutable OperationToken
     *
     * @param value
     */
    public OperationToken(String value) {
        super(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element build(Document document) {
        var element = document.createElement("mo");
        element.appendChild(document.createTextNode(this.value));
        return element;
    }
}
