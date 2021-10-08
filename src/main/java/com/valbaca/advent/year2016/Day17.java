package com.valbaca.advent.year2016;

import com.valbaca.advent.elf.MD5Hasher;
import com.valbaca.advent.elf.optimizer.Optimizer;
import com.valbaca.advent.elf.optimizer.StrengthLengthMaximizer;
import com.valbaca.advent.elf.optimizer.StrengthLengthMinimizer;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import static com.valbaca.advent.elf.Timer.measure;

/**
 * I really enjoyed this one as it built off the previous questions (A*, MD5 hash) and had a unique twist for the part 2
 * by switching the optimization altogether.
 * To do this, and for future problems, I finally made an {@link Optimizer} class which allows for quickly switching out
 * what's being optimized, and consolidates the constant if-less-than-update code that's common in Advent problems.
 * <p>
 * This was also the first problem I really started with unit tests, which really sped up the coding.
 * Everytime I use TDD I re-learn how much faster it makes coding (when the inputs & outputs are clearly defined).
 * <p>
 * TIL:
 * - trick to remove nulls from a list: while(list.remove(null));
 */
public class Day17 {
    public static void main(String[] args) {
        /*
        hash(input-passcode + path-so-far).substring(0,4)
        0=UP 1=DOWN 2=LEFT 3=RIGHT
        door is open if char is bcdef
        don't forget to limit choices by the physical bounds
        optimize for shortest path
         */
        System.out.println("Day 17");
        System.out.println("Part 1");
        System.out.println(measure(() -> new Day17().part1("veumntbg")));

        System.out.println("Part 2");
        System.out.println(measure(() -> new Day17().part2("veumntbg")));
    }

    public String part1(String passcode) {
        return optimizePath(passcode, new StrengthLengthMinimizer());
    }

    public int part2(String passcode) {
        return optimizePath(passcode, new StrengthLengthMaximizer()).length();
    }

    private String optimizePath(String passcode, Optimizer<String> optimizer) {
        Loc.passcode = passcode;
        var pq = new PriorityQueue<Loc>();
        pq.add(new Loc(0, 0, ""));
        while (!pq.isEmpty()) {
            var loc = pq.poll();
            if (loc.isFinished()) {
                optimizer.optimize(loc.getPath());
            } else {
                pq.addAll(loc.nextSteps());
            }
        }
        return optimizer.getValue();
    }


    @Value
    static class Loc implements Comparable<Loc> {
        public static String passcode;
        private static final int MAX_Y = 3;
        private static final int MAX_X = 3;
        private static final MD5Hasher hasher = new MD5Hasher();
        private static final Set<Character> openSet = Set.of('b', 'c', 'd', 'e', 'f');
        int x, y;
        String path;

        private Loc moveUp() {
            return y == 0 ? null : new Loc(x, y - 1, path + "U");
        }

        private Loc moveDown() {
            return y == MAX_Y ? null : new Loc(x, y + 1, path + "D");
        }

        private Loc moveLeft() {
            return x == 0 ? null : new Loc(x - 1, y, path + "L");
        }

        private Loc moveRight() {
            return x == MAX_X ? null : new Loc(x + 1, y, path + "R");
        }

        public boolean isFinished() {
            return x == MAX_X && y == MAX_Y;
        }

        @Override
        public int compareTo(Loc o) {
            return Integer.compare(this.path.length(), o.getPath().length());
        }

        public List<Loc> nextSteps() {
            var s = passcode + path;
            var prefix = hasher.hex(s).substring(0, 4).toCharArray();
            List<Loc> next = new ArrayList<>();
            // 0=UP 1=DOWN 2=LEFT 3=RIGHT
            if (isOpen(prefix[0])) {
                next.add(moveUp());
            }
            if (isOpen(prefix[1])) {
                next.add(moveDown());
            }
            if (isOpen(prefix[2])) {
                next.add(moveLeft());
            }
            if (isOpen(prefix[3])) {
                next.add(moveRight());
            }
            while (next.remove(null)) ;
            return next;
        }

        private boolean isOpen(char c) {
            return openSet.contains(c);
        }
    }
}
