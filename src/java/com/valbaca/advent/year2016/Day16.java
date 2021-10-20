package com.valbaca.advent.year2016;

import org.apache.commons.lang3.StringUtils;

import static com.valbaca.advent.elf.Timer.measure;

/**
 * TIL:
 * - This one was really easy, just some pretty basic string manipulations
 */
public class Day16 {
    public static void main(String[] args) {
        System.out.println("Day 16");
        System.out.println("Testing");
        var day16 = new Day16();
        measure(() -> day16.runner("10000", 20));

        System.out.println("Part 1");
        measure(() -> day16.runner("10111100110001111", 272)); // ~200 MICROseconds!

        System.out.println("Part 2");
        measure(() -> day16.runner("10111100110001111", 35651584));
    }

    public void runner(String input, int length) {
        System.out.println(checksum(fill(input, length)));
    }

    public String fill(String s, int n) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < n) {
            var rev = StringUtils.reverse(sb.toString());
            sb.append('0');
            for (char c : rev.toCharArray()) {
                sb.append(c == '0' ? '1' : '0');
            }
        }
        return sb.substring(0, n);
    }

    public String checksum(String s) {
        String sum = s;
        while (sum.length() % 2 == 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < sum.length(); i += 2) {
                String pair = sum.substring(i, i + 2);
                if (pair.equals("00") || pair.equals("11")) {
                    sb.append('1');
                } else {
                    sb.append('0');
                }
            }
            sum = sb.toString();
        }
        return sum;
    }
}
