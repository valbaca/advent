package com.valbaca.advent;

import com.valbaca.advent.year2015.Year2015Main;
import com.valbaca.advent.year2016.Year2016Main;

import javax.lang.model.SourceVersion;

public class Main {
    public static void main(String[] args) {
        System.out.println("🎄🎄🎄 ADVENT OF CODE 🎄🎄🎄");
        printJavaRuntimeInfo();
        Year2015Main.main(args);
        System.out.println("🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄🎄");
        Year2016Main.main(args);
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
