package com.valbaca.advent.elf.optimizer;

public class Maximizer<T extends Comparable<T>> implements Optimizer<T> {
    private T value;

    public Maximizer(T start) {
        this.value = start;
    }

    // TODO accept a Comparator to allow flexible extensions

    @Override
    public boolean optimize(T potential) {
        if (value.compareTo(potential) < 0) {
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
