package com.valbaca.advent.elf;

import com.google.common.base.Stopwatch;
import lombok.SneakyThrows;

import java.util.concurrent.Callable;

public class Timer {
    public static String measure(Runnable runnable) {
        var stopwatch = Stopwatch.createStarted();
        runnable.run();
        stopwatch.stop();
        System.out.printf("Took: %s%n", stopwatch);
        return null;
    }

    @SneakyThrows
    public static <T> T measure(Callable<T> callable) {
        var stopwatch = Stopwatch.createStarted();
        T result = callable.call();
        stopwatch.stop();
        System.out.printf("Took: %s%n", stopwatch);
        return result;
    }
}
