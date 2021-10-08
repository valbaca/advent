package com.valbaca.advent.elf.optimizer;

public class IntegerMaximizer extends Maximizer<Integer> {
    public IntegerMaximizer() {
        this(Integer.MIN_VALUE);
    }

    public IntegerMaximizer(Integer start) {
        super(start);
    }
}
