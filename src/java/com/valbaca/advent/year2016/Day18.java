package com.valbaca.advent.year2016;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * A pretty easy one, the "hard" part is really the (literal) edge cases. It was nice that the trap logic could just be
 * string in a set. It was also nice that the numbers all fit within int (usually the case with Advent).
 * TIL:
 */
public class Day18 {
    private final Set<String> TRAPS = Set.of(
            "^^.", //Its left and center tiles are traps, but its right tile is not.
            ".^^", //Its center and right tiles are traps, but its left tile is not.
            "^..", //Only its left tile is a trap.
            "..^"  //Only its right tile is a trap.
    );

    public static void main(String[] args) {
        System.out.println("Day 18");
        var day = new Day18();
        var in = "^.^^^.^..^....^^....^^^^.^^.^...^^.^.^^.^^.^^..^.^...^.^..^.^^.^..^.....^^^.^.^^^..^^...^^^...^...^.";
        System.out.println("Part 1");
        System.out.println(day.safeTiles(40, in));

        System.out.println("Part 2");
        System.out.println(day.safeTiles(400000, in));
    }

    public String nextLine(String prev) {
        var sb = new StringBuilder();
        for (int i = 0; i < prev.length(); i++) {
            sb.append(determine(prevSubstring(prev, i)));
        }
        return sb.toString();
    }

    private Character determine(String substring) {
        return TRAPS.contains(substring) ? '^' : '.';
    }

    private String prevSubstring(String prev, int i) {
        if (i == 0) return "." + prev.substring(0, 2);
        else if (i == prev.length() - 1) return prev.substring(i - 1) + ".";
        return prev.substring(i - 1, i + 2);
    }

    public int safeTiles(int row, String start) {
        String tiles = start;
        int count = countSafe(tiles);
        for (int i = 1; i < row; i++) {
            tiles = nextLine(tiles);
            count += countSafe(tiles);
        }
        return count;
    }

    public int countSafe(String s) {
        return StringUtils.countMatches(s, '.');
    }
}
