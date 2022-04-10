package com.nasumilu.calculator.event;

import com.nasumilu.calculator.data.model.Equation;
import com.nasumilu.calculator.data.model.InputToken;

public class EquationEvent {

    public enum ChangeType {
        VALUE_APPENDED,
        VALUE_POPPED,
        VALUE_CLEARED
    }

    private final ChangeType changeType;
    private final Equation source;
    private InputToken token;

    public EquationEvent(ChangeType changeType, Equation source) {
        this.changeType = changeType;
        this.source = source;
    }

    public EquationEvent(ChangeType changeType, Equation source, InputToken token) {
        this(changeType, source);
        this.token = token;
    }

    public static EquationEvent createValueClearedEvent(Equation source) {
        return new EquationEvent(ChangeType.VALUE_CLEARED, source);
    }

    public static EquationEvent createValueAppendedEvent(Equation source, InputToken token) {
        return new EquationEvent(ChangeType.VALUE_APPENDED, source, token);
    }

    public static EquationEvent createValuePoppedEvent(Equation source, InputToken token) {
        return new EquationEvent(ChangeType.VALUE_POPPED, source, token);
    }

    public Equation getSource() {
        return source;
    }

    public InputToken getToken() {
        return token;
    }

    public ChangeType getChangeType() {
        return changeType;
    }
}
