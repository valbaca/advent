package com.valbaca.advent.elf.optimizer;

public class IntegerMinimizer extends Minimizer<Integer> {

    public IntegerMinimizer() {
        this(Integer.MAX_VALUE);
    }

    private IntegerMinimizer(Integer start) {
        super(start);
    }
}
