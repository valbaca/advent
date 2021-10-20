package com.valbaca.advent.elf.optimizer;

public class StrengthLengthMinimizer extends Minimizer<String> {
    public StrengthLengthMinimizer() {
        super(null);
    }

    public StrengthLengthMinimizer(String start) {
        super(start);
    }

    @Override
    public boolean optimize(String value) {
        var prev = getValue();
        if (prev == null || prev.length() > value.length()) {
            setValue(value);
            return true;
        }
        return false;
    }
}
