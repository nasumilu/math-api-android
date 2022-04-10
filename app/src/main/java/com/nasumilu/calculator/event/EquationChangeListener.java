package com.nasumilu.calculator.event;

@FunctionalInterface
public interface EquationChangeListener {

    void onEquationChanged(EquationEvent event);

}
