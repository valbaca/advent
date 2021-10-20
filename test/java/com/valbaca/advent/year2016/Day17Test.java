package com.valbaca.advent.year2016;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test {

    @Test
    void shortestPath() {

        assertThat(new Day17().part1("hijkl")).isNull();
        assertThat(new Day17().part1("ihgpwlah")).isEqualTo("DDRRRD");
        assertThat(new Day17().part1("kglvqrro")).isEqualTo("DDUDRLRRUDRD");
        assertThat(new Day17().part1("ulqzkmiv")).isEqualTo("DRURDRUDDLLDLUURRDULRLDUUDDDRR");
    }

    @Test
    void longestPath() {
//        assertThat(new Day17().longestPath("hijkl")).isNull();
        assertThat(new Day17().part2("ihgpwlah")).isEqualTo(370);
        assertThat(new Day17().part2("kglvqrro")).isEqualTo(492);
        assertThat(new Day17().part2("ulqzkmiv")).isEqualTo(830);
    }
}