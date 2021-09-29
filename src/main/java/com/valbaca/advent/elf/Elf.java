package com.valbaca.advent.elf;

import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * Santa's little helper!
 * The main class to throw helper methods that don't really belong anywhere else.
 */
public class Elf {

    public static int sum(List<Integer> list) {
        return list.stream().mapToInt(i -> i).sum();
    }

    public static BigDecimal product(List<Integer> list) {
        return list.stream().map(BigDecimal::valueOf).reduce(BigDecimal.ONE, BigDecimal::multiply);
    }

    public static List<String> getInputLines(int year, int day) {
        return getInputLines(year, day, false);
    }

    public static List<String> getTestInputLines(int year, int day) {
        return getInputLines(year, day, true);
    }

    @SneakyThrows
    private static List<String> getInputLines(int year, int day, boolean test) {
        return Files.readAllLines(getPath(year, day, test));
    }


    public static Stream<String> getInputStream(int year, int day) {
        return getInputStream(year, day, false);
    }

    public static Stream<String> getTestInputStream(int year, int day) {
        return getInputStream(year, day, true);
    }

    @SneakyThrows
    private static Stream<String> getInputStream(int year, int day, boolean test) {
        return Files.lines(getPath(year, day, test));
    }


    private static Path getPath(int year, int day, boolean test) {
        String str = String.format("input/year%d/day%d%s.txt", year, day, test ? "-test": "");
        var path = Path.of(str);
        return path;
    }

}
