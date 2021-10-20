package com.valbaca.advent;

/**
 * Just showing Clojure-Java interop
 */
public class Howdy {
    public static void main(String[] args) {
        System.out.println(Howdy.say());
    }

    public static String say() {
        return "Howdy from Java!";
    }
}
