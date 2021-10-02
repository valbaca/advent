package com.valbaca.advent.year2016.day11;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.Integer.MAX_VALUE;

/**
 * Managed to reduce the runtime by about 10%
 * <p>
 * Runtime:
 * Part 1 done in 1.299 s
 * Part 2 done in 28.63 s
 */
public class Day11Parallel {

    public static void main(String[] args) {
        System.out.println("Day 11 *parallel* start...");

        System.out.println("Test start...");
        var stopwatch = Stopwatch.createStarted();
        new Day11Parallel().runner(Day11.testInput());
        System.out.println("...Test done in " + stopwatch.stop());

        System.out.println("Part 1 start...");
        stopwatch = Stopwatch.createStarted();
        new Day11Parallel().runner(Day11.part1Input());
        System.out.println("...Part 1 done in " + stopwatch.stop());

        System.out.println("Part 2...");
        stopwatch = Stopwatch.createStarted();
        new Day11Parallel().runner(Day11.part2Input());
        System.out.println("...Part 2 done in " + stopwatch.stop());
        System.out.println("Day 11 *parallel* done!");
    }

    static PriorityBlockingQueue<Building> pq;
    static Set<Building> seen;
    static AtomicInteger minSteps;
    static ExecutorService pool;

    public void runner(Building init) {
        pq = new PriorityBlockingQueue<>();
        pq.add(init);

        seen = Sets.newConcurrentHashSet();
        seen.add(init);

        minSteps = new AtomicInteger(MAX_VALUE);
        /*
        WorkStealingPool performs better than FixedThreadPool, regardless of how many threads the pool is set to
         */
        pool = Executors.newWorkStealingPool();
        pool.execute(buildConsumer()); // KICK OFF

        while (!pool.isShutdown()) {
        }
    }

    private Runnable buildConsumer() {
        return () -> {
            Building b = pq.poll();
            if (minSteps.get() <= b.getSteps()) return;
            // Technically there can be a race-condition here around minStep, but doesn't actually happen
            if (b.isSolved()) {
                minSteps.set(b.getSteps());
                System.out.println("New Min!: " + minSteps.get());
                pool.shutdown();
                return;
            }
            pool.execute(buildProducer(b));
        };
    }

    private Runnable buildProducer(final Building b) {
        return () -> {
            var options = b.generateOptions().collect(Collectors.toSet());
            var unseen = Sets.difference(options, seen).immutableCopy();
            seen.addAll(unseen);
            pq.addAll(unseen);
            for (int i = 0; i < unseen.size(); i++) {
                pool.execute(buildConsumer());
            }
        };
    }
}
