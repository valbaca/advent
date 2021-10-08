package com.valbaca.advent.elf.optimizer;

public class StrengthLengthMaximizer extends Maximizer<String> {
    public StrengthLengthMaximizer() {
        super(null);
    }

    public StrengthLengthMaximizer(String start) {
        super(start);
    }

    @Override
    public boolean optimize(String value) {
        var prev = getValue();
        if (prev == null || prev.length() < value.length()) {
            setValue(value);
            return true;
        }
        return false;
    }
}
