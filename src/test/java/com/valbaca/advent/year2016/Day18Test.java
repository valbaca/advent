package com.valbaca.advent.year2016;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day18Test {

    @Test
    void nextLine() {
        var day = new Day18();
        Assertions.assertThat(day.nextLine("..^^.")).isEqualTo(".^^^^");
        Assertions.assertThat(day.nextLine(".^^^^")).isEqualTo("^^..^");
    }

    @Test
    void safeTiles() {
        Assertions.assertThat(new Day18().safeTiles(10, ".^^.^.^^^^")).isEqualTo(38);
    }
}