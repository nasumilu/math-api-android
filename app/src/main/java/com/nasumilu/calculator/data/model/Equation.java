package com.nasumilu.calculator.data.model;

import com.nasumilu.calculator.data.NodeBuilder;
import com.nasumilu.calculator.event.EquationChangeListener;
import com.nasumilu.calculator.event.EquationEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Represents an equation in a FILO list
 */
public class Equation implements NodeBuilder {

    /**
     * List to hold each of the InputToken objects
     */
    private final List<AbstractInputToken> tokens = Collections.synchronizedList(new LinkedList<>());

    private final Set<EquationChangeListener> listeners = Collections.synchronizedSet(new HashSet<>());

    /**
     * Appends an AbstractInputToken to the list and fires an EquationChangeEvent
     *
     * @param token
     * @return
     */
    private boolean append(AbstractInputToken token) {
        if(this.tokens.add(token)) {
            this.fireValueAppendedChangeEvent(token);
            return true;
        }
        return false;
    }

    /**
     * Appends the value as a NumericToken to the list of input tokens
     *
     * @param value
     * @param <T>
     * @return
     */
    public <T extends Number> boolean append(T value) {
        return this.append(new NumericToken<T>(value));
    }

    /**
     * Appends the value as a OperationToken to the list of input tokens
     *
     * @param value
     * @return
     */
    public boolean append(String value) {
        return this.append(new OperationToken(value));
    }

    /**
     * Clears the list of input tokens
     */
    public void clear() {
        this.tokens.clear();
        this.fireValueClearedChangeEvent();
    }

    /**
     * Removes and returns the last InputToken from the list
     * @return
     */
    public InputToken pop()
    {
        var token = this.tokens.remove(this.tokens.size() - 1);
        if(null != token) {
            this.fireValuePoppedChangeEvent(token);
        }
        return token;
    }

    /**
     * Builds an &lt;mrow&gt; element with each of the InputTokens as children
     * based upon its implementation of NodeBuilder::build.
     *
     * @param document
     * @return
     */
    @Override
    public Element build(Document document) {
        final var element = document.createElement("mrow");
        this.tokens.forEach(token -> {
            element.appendChild(token.build(document));
        });
        return element;
    }

    private void fireValueClearedChangeEvent() {
        this.listeners.forEach(l -> l.onEquationChanged(EquationEvent.createValueClearedEvent(this)));
    }

    /**
     * Fires an EquationEvent when an InputValue is popped
     * @param token
     */
    private void fireValuePoppedChangeEvent(final InputToken token) {
        this.listeners.forEach(l -> l.onEquationChanged(EquationEvent.createValuePoppedEvent(this, token)));
    }

    /**
     * Fires an EquationEvent when an InputValue is appended
     * @param token
     */
    private void fireValueAppendedChangeEvent(final InputToken token) {
        this.listeners.forEach(l -> l.onEquationChanged(EquationEvent.createValueAppendedEvent(this, token)));
    }

    /**
     * Add an EquationChangeListener
     * @param listener
     */
    public void addEquationChangeListener(EquationChangeListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Remove an EquationChangeListener
     *
     * @param listener
     */
    public void removeEquationChangeListener(EquationChangeListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public String toString() {
        final var builder = new StringBuilder();
        this.tokens.forEach(t -> builder.append(t.getValue()));
        return builder.toString();
    }
}
