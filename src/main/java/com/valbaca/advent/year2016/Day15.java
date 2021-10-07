package com.valbaca.advent.year2016;

import java.util.List;

import static com.valbaca.advent.elf.Timer.measure;

/**
 * TIL:
 * - This was probably one of the easiest problems once I could understand and visualize the problem and
 * translate it into terms of mod and lists
 * - Sometimes, don't even bother writing a parser, just type out the input
 * - This day's problem is an example of one I'd probably struggle to write in Clojure
 */
public class Day15 {
    public static void main(String[] args) {
        System.out.println("Day 15");
        {
            System.out.println("Testing");
            List<Disc> discs = List.of(
                    new Disc(5, 4),
                    new Disc(2, 1)
            );

            measure(() -> new Day15().runner(discs));
        }
        {
            System.out.println("Part 1");
            List<Disc> discs = List.of(
                    new Disc(13, 10),
                    new Disc(17, 15),
                    new Disc(19, 17),
                    new Disc(7, 1),
                    new Disc(5, 0),
                    new Disc(3, 1)
            );

            measure(() -> new Day15().runner(discs));
        }
        {
            System.out.println("Part 2");
            List<Disc> discs = List.of(
                    new Disc(13, 10),
                    new Disc(17, 15),
                    new Disc(19, 17),
                    new Disc(7, 1),
                    new Disc(5, 0),
                    new Disc(3, 1),
                    new Disc(11, 0)
            );

            measure(() -> new Day15().runner(discs));
        }
    }

    private void runner(List<Disc> discs) {
        int openTime = -1;
        int t = -1;
        while (openTime == -1) {
            t++;
            boolean allOpen = true;
            for (int i = 0; i < discs.size() && allOpen; i++) {
                allOpen = discs.get(i).isOpen(t + i + 1);
            }
            if (allOpen) openTime = t;
        }
        System.out.println("openTime = " + openTime);
    }

    record Disc(int positions, int start) {
        boolean isOpen(int time) {
            return ((start + time) % positions) == 0;
        }
    }
}
