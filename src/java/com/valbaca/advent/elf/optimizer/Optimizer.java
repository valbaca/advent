package com.valbaca.advent.elf.optimizer;

public interface Optimizer<T extends Comparable<T>> {
    /**
     * Given a potential value, attempts to update the current value.
     * @return true if the value is updated
     */
    boolean optimize(T value);

    /**
     * Returns the latest optimized value
     */
    T getValue();
}
