package com.valbaca.advent.elf.optimizer;

public class Minimizer<T extends Comparable<T>> implements Optimizer<T> {
    private T value;

    public Minimizer(T start) {
        this.value = start;
    }

    @Override
    public boolean optimize(T potential) {
        if (value.compareTo(potential) > 0) {
            value = potential;
            return true;
        }
        return false;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    protected void setValue(T value) {
        this.value = value;
    }
}
