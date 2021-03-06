package com.valbaca.advent.year2016.day11;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import static com.valbaca.advent.elf.Timer.measure;
import static com.valbaca.advent.year2016.day11.Element.*;
import static com.valbaca.advent.year2016.day11.Type.chip;
import static com.valbaca.advent.year2016.day11.Type.gen;
import static java.lang.Integer.MAX_VALUE;

/**
 * <a href="https://adventofcode.com/2016/day/11">Problem</a>
 * <p>
 * Solved this by creating a Building class, which represents the full state of the building.
 * The buildings have "scores" which indicate how much closer they are to being the solution.
 * <p>
 * Each building can generate options for the "next step" by giving all viable building states one step away.
 * Buildings are all put into a PriorityQueue, where the priority sort-order is the building's score.
 * (The priority queue is a min-heap, so the score is actually inverted; lowest score is best).
 * This queue is worked. The priority ensures we're working on the "closest" solution.
 * Each building that's pulled off, generates [0, n^2) new building options, where n is the # of items on the current floor.
 * Because the solution space grows exponentially, the priority queue keeps us working on the best option.
 * <p>
 * Runtime:
 * Part 1 done in 1.344 s
 * Part 2 done in 56.14 s
 * <p>
 * - This was a HARD one, but I did eventually manage to get the runtime down to ~1 sec for part 1 and ~1 min for part 2
 * - PriorityQueue and a good compareTo method was key to solving this. Even so, part 2 took 5-10 mins to run
 * - Using Java VisualVM, I was able to profile CPU and memory and optimize further:
 * - getScore was getting called too much, made that stored
 * - Building HashCode was slow, had it ignore steps and score
 * - Moved Floor from using a set to using a pair of EnumSets, which are basically bit-sets in a long
 * - I would like to parallelize and see how much that improves perf, but my attempts haven't been very successful
 * See {@link Day11Parallel}
 */
public class Day11 {

    public static final int NUM_FLOORS = 4;

    public static void main(String[] args) {
        System.out.println("Day 11 start...");

        System.out.println("Test start...");
        measure(() -> new Day11().runner(testInput()));

        System.out.println("Part 1 start...");
        measure(() -> new Day11().runner(part1Input()));

        System.out.println("Part 2...");
        measure(() -> new Day11().runner(part2Input()));

        System.out.println("Day 11 done!");
    }

    public void runner(Building init) {
        PriorityQueue<Building> pq = new PriorityQueue<>();
        pq.add(init);

        Set<Building> seen = new HashSet<>();
        seen.add(init);

        int minSteps = MAX_VALUE;
        while (!pq.isEmpty()) {
            Building b = pq.poll();
            if (minSteps <= b.getSteps()) continue;
            if (b.isSolved()) {
                minSteps = b.getSteps();
                System.out.println("New Min!: " + minSteps);
                continue;
            }
            var options = b.generateOptions().collect(Collectors.toSet());
            var unseen = Sets.difference(options, seen).immutableCopy();
            seen.addAll(unseen);
            pq.addAll(unseen);
        }
    }

    public static Building testInput() {
        Building init = new Building(NUM_FLOORS);
        init.getFloors().get(0).addAll(
                List.of(
                        new Item(hydrogen, chip),
                        new Item(lithium, chip)));
        init.getFloors().get(1).add(new Item(hydrogen, gen));
        init.getFloors().get(2).add(new Item(lithium, gen));
        return init;
    }


    public static Building part1Input() {
        Building init = new Building(NUM_FLOORS);
        var floorOneItems = List.of(
                new Item(polonium, gen),
                new Item(thulium, gen),
                new Item(thulium, chip),
                new Item(promethium, gen),
                new Item(ruthenium, gen),
                new Item(ruthenium, chip),
                new Item(cobalt, gen),
                new Item(cobalt, chip));
        init.getFloors().get(0).addAll(floorOneItems);

        var floorTwoItems = List.of(
                new Item(polonium, chip),
                new Item(promethium, chip)
        );
        init.getFloors().get(1).addAll(floorTwoItems);
        return init;
    }

    public static Building part2Input() {
        var building = part1Input();
        var floorOneItems = List.of(
                new Item(elerium, gen),
                new Item(elerium, chip),
                new Item(dilithium, gen),
                new Item(dilithium, chip)
        );
        building.getFloors().get(0).addAll(floorOneItems);
        return building;
    }


}
