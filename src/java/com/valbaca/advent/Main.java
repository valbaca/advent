package com.valbaca.advent;

import javax.lang.model.SourceVersion;
import com.valbaca.advent.year2015.Year2015;
import com.valbaca.advent.year2016.Year2016;

public class Main {
    public static void main(String[] args) {
        System.out.println("🎄🎄🎄 ADVENT OF CODE 🎄🎄🎄");
        printJavaRuntimeInfo();

        System.out.println("🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄");
        Year2015.main(args);

        System.out.println("🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄");
        Year2016.main(args);

        System.out.println("🎄🎄🎄...and to all a good night 🎄🎄🎄");
    }

    private static void printJavaRuntimeInfo() {
        System.out.println("Runtime: " + Runtime.version());
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("java.specification.version"));
        System.out.println(SourceVersion.latest());
        System.out.println(SourceVersion.latestSupported());
    }

    public String saysHi() {
        return "HI!"; // for sanity unit test
    }
}
