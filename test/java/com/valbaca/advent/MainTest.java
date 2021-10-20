package com.valbaca.advent;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    public void testSaysHi() {
        Assertions.assertThat(new Main().saysHi()).isEqualTo("HI!");
    }

}