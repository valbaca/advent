package com.valbaca.advent.year2015;

import com.valbaca.advent.elf.Combinator;
import com.valbaca.advent.elf.Elf;

import java.math.BigDecimal;
import java.util.List;

import static com.valbaca.advent.elf.Elf.product;
import static com.valbaca.advent.elf.Elf.sum;
import static com.valbaca.advent.elf.Timer.measure;
import static java.util.stream.Collectors.toList;

/**
 * TIL:
 * Path.of(str) to get path, which can then be given to Files
 * Files.lines for Stream of lines (Strings)
 * Files.readAllLines for List of lines (Strings)
 * Sometimes you don't actually need to solve the whole problem perfectly; just get what you need.
 * How Apache Combinations works: n-choose-k, gives k indexes of [0,n). Need to correspond with list.
 * Wrote some helper methods: sum, prod, getInputLines/Stream
 * minPackages=6 qe=11846773891
 */
public class Day24 {

    public static final int YEAR = 2015;
    public static final int DAY = 24;
    private int minPackages;
    private BigDecimal entanglement;

    public static void main(String[] args) {
        System.out.println("Day 24, Part 1");
        {
            var lines = Elf.getTestInputStream(YEAR, DAY);
            var ints = lines.map(Integer::valueOf).collect(toList());
            measure(() -> new Day24().solve(ints, 3));
        }
        {
            var lines = Elf.getInputStream(YEAR, DAY);
            var ints = lines.map(Integer::valueOf).collect(toList());
            measure(() -> new Day24().solve(ints, 3));
        }
        System.out.println("Day 24, Part 2");
        {
            var lines = Elf.getTestInputStream(YEAR, DAY);
            var ints = lines.map(Integer::valueOf).collect(toList());
            measure(() -> new Day24().solve(ints, 4));
        }
        {
            var lines = Elf.getInputStream(YEAR, DAY);
            var ints = lines.map(Integer::valueOf).collect(toList());
            measure(() -> new Day24().solve(ints, 4));
        }
    }

    public void solve(List<Integer> ints, int compartments) {
        minPackages = Integer.MAX_VALUE;
        entanglement = BigDecimal.ZERO;
        var target = sum(ints) / compartments;
        for (int i = 1; i < ints.size() - 2 && i < minPackages; i++) {
            for (List<Integer> selected : Combinator.nChooseK(ints, i)) {
                if (sum(selected) == target) {
                    analyzeBalanced(selected);
                }
            }
        }
        System.out.printf("minPackages=%d entanglement=%s%n", minPackages, entanglement);
    }

    private void analyzeBalanced(List<Integer> selected) {
        if (selected.size() < minPackages) {
            minPackages = selected.size();
            entanglement = product(selected);
//            System.out.printf("minPackages=%d entanglement=%s%n", minPackages, entanglement);
        } else if (selected.size() == minPackages) {
            var otherQE = product(selected);
            if (otherQE.compareTo(entanglement) < 0) {
                entanglement = otherQE;
//                System.out.printf("minPackages=%d entanglement=%s%n", minPackages, entanglement);
            }
        }
    }
}
