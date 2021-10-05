package com.valbaca.advent.elf;

import com.google.common.base.Stopwatch;

public class Timer {
    public static void measure(Runnable runnable) {
        var stopwatch = Stopwatch.createStarted();
        runnable.run();
        stopwatch.stop();
        System.out.printf("Took: %s%n", stopwatch);
    }
}
