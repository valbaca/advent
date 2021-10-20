package com.valbaca.advent.year2015;

import java.math.BigInteger;

public class Day25 {

    public static void main(String[] args) {
        System.out.println("Day 25");
        new Day25().solve(6, 6);
        new Day25().solve(2978, 3083);
    }

    private void solve(final int row, final int col) {
        int r = 1, c = 1;
        BigInteger curr = BigInteger.valueOf(20151125);

        final BigInteger mult = BigInteger.valueOf(252533);
        final BigInteger div = BigInteger.valueOf(33554393);

        while (r != row || c != col) {
//            System.out.printf("r=%d c=%d curr=%s%n", r, c, curr);
            if (r == 1) {
                r = c+1;
                c = 1;
            } else {
                r--;
                c++;
            }
            curr = curr.multiply(mult).divideAndRemainder(div)[1];
        }
        System.out.printf("r=%d c=%d curr=%s%n", r, c, curr);
    }
}
