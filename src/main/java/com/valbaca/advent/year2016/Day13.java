package com.valbaca.advent.year2016;

import com.valbaca.advent.elf.Timer;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.function.Function;

import static com.valbaca.advent.year2016.Day13.Spot.Open;

/**
 * TIL:
 * - A* with PriorityQueue triumphs again (at least I'm pretty sure this is A*)
 * - Not sure if the Google Cache was needed, but also didn't test without it
 * - UPDATE: it runs faster without the cache! I should've known: avoid premature optimization!
 * - Records were great to work with. Probably should've had Path have a Coord, rather than each have x y fields
 * - Enums help with readability too
 */
public class Day13 {
    private PriorityQueue<Path> pq;
    private HashSet<Coord> seen;
    private HashSet<Coord> visited;
    private Function<Coord, Spot> getSpotTypeForCoord;

    public static void main(String[] args) {
        System.out.println("Day 13");
//        testing();
        /*

        Part 1 Pseudocode:
        1. wrap the isWall(x,y) in a cache?
        2. A* toward the goal?
            q = Queue<Items>.of(start) // Items is sortable by steps, then by "best"
            while (q) {
              curr = q.pop
              if (curr.solution) handleSolution(curr)
              visited = curr
              q.add(curr.options)
            }
         */

        System.out.println("Test");
        {
            Coord target = new Coord(7, 4);
            int input = 10;
            Timer.measure(() -> new Day13().run(target, input));
        }
        System.out.println("Part 1");
        {
            Coord target = new Coord(31, 39);
            int input = 1350;
            Timer.measure(() -> new Day13().run(target, input));
        }

        System.out.println("Part 2");
        {
            int input = 1350;
            Timer.measure(() -> new Day13().run2(input));
        }
    }


    private void run(final Coord target, final int input) {
        pq = new PriorityQueue<>(Comparator.comparingInt(Path::steps).thenComparingInt(o -> o.distTo(target)));
        seen = new HashSet<>();
        getSpotTypeForCoord = (Coord c) -> Spot.getSpotType(c.x(), c.y(), input);

        var init = new Path(1, 1, 0);
        pq.add(init);
        seen.add(init.toCoord());

        while (!pq.isEmpty()) {
            var curr = pq.poll();
            if (curr.distTo(target) == 0) {
                System.out.println("Steps=" + curr.steps);
                break;
            }
            var steps = curr.steps() + 1;
            visit(new Path(curr.x(), curr.y() - 1, steps)); // up
            visit(new Path(curr.x() + 1, curr.y(), steps)); // right
            visit(new Path(curr.x(), curr.y() + 1, steps)); // down
            visit(new Path(curr.x() - 1, curr.y(), steps)); // left
        }

    }

    private void visit(Path path) {
        if (path.x() < 0 || path.y() < 0) return;
        var coord = path.toCoord();

        if (seen.contains(coord)) return;
        seen.add(coord);

        if (getSpotTypeForCoord.apply(coord) == Spot.Wall) return;
        pq.add(path);
    }


    private void run2(final int input) {
        pq = new PriorityQueue<>(Comparator.comparingInt(Path::steps));
        seen = new HashSet<>();
        visited = new HashSet<>();
        getSpotTypeForCoord = (Coord c) -> Spot.getSpotType(c.x(), c.y(), input);

        var init = new Path(1, 1, 0);
        pq.add(init);
        seen.add(init.toCoord());
        visited.add(init.toCoord());

        while (!pq.isEmpty()) {
            var curr = pq.poll();
            var steps = curr.steps() + 1;
            visit2(new Path(curr.x(), curr.y() - 1, steps)); // up
            visit2(new Path(curr.x() + 1, curr.y(), steps)); // right
            visit2(new Path(curr.x(), curr.y() + 1, steps)); // down
            visit2(new Path(curr.x() - 1, curr.y(), steps)); // left
        }
        System.out.println(visited.size());
    }


    private void visit2(Path path) {
        if (path.x() < 0 || path.y() < 0 || path.steps > 50) return;
        var coord = path.toCoord();

        if (seen.contains(coord)) return;
        seen.add(coord);

        if (getSpotTypeForCoord.apply(coord) == Spot.Wall) return;
        pq.add(path);
        visited.add(coord);
    }

    private static void testing() {
        System.out.println("Testing");
        Function<Coord, Spot> getSpotTypeForCoord = (Coord c) -> Spot.getSpotType(c.x(), c.y(), 10);
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                var spotType = getSpotTypeForCoord.apply(new Coord(x, y));
                System.out.print(spotType == Open ? '.' : '#');
            }
            System.out.println();
        }
    }

    record Coord(int x, int y) {
    }

    record Path(int x, int y, int steps) {
        public Path fromCoord(Coord c, int steps) {
            return new Path(c.x(), c.y(), steps);
        }

        public static Coord fromPath(Path p) {
            return new Coord(p.x(), p.y());
        }

        public Coord toCoord() {
            return fromPath(this);
        }

        public int distTo(Coord c) {
            return Math.abs(x - c.x()) + Math.abs(y - c.y());
        }
    }

    enum Spot {
        Open, Wall;

        public static Spot getSpotType(int x, int y, int input) {
            long calc = x * x + 3 * x + 2 * x * y + y + y * y + input;
            var bitCount = Long.bitCount(calc);
            return ((bitCount & 1) != 1) ? Open : Wall;
        }
    }
}