package com.valbaca.advent.year2016;

import com.valbaca.advent.elf.Elf;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.valbaca.advent.elf.Timer.measure;

/**
 * TIL:
 * - Working with Scanner. First iteration used a file scanner (which given a file, would give lines) and a line scanner
 * which given lines, would tokenize the "words".
 * - Turned out to run faster (probably saving an allocation of a line scanner on each execution) to use a single scanner
 * - Handling the "jump back" instructions was challenging b/c of off-by-one and having to close the executor and start over
 * - There's probably a more optimal way to move a bout a file that easily fits in memory...
 */
public class Day12 {
    public static void main(String[] args) {
        System.out.println("Day 12");
        System.out.println("Test");
        measure(() -> new Day12().run(Elf.getPath(2016, 12, true), new Registers()));

        System.out.println("Part 1");
        measure(() -> new Day12().run(Elf.getPath(2016, 12, false), new Registers()));

        System.out.println("Part 2");
        var part2Registers = new Registers();
        part2Registers.setValue("c", 1);
        measure(() -> new Day12().run(Elf.getPath(2016, 12, false), part2Registers));
    }

    @SneakyThrows
    private void run(Path path, Registers registers) {
        var scanner = new Scanner(path);
        int lineNum = 0;
        while (scanner.hasNextLine()) {
            lineNum++;
            var inst = scanner.next();
            var x = scanner.next();
            switch (inst) {
                case "cpy" -> registers.setValue(scanner.next(), registers.getValue(x));
                case "inc" -> registers.setValue(x, registers.getValue(x) + 1);
                case "dec" -> registers.setValue(x, registers.getValue(x) - 1);
                case "jnz" -> {
                    var value = registers.getValue(x);
                    var offset = scanner.next();
                    if (value != 0) {
                        var offsetValue = registers.getValue(offset);
                        if (offsetValue == 0) throw new IllegalStateException("INF LOOP");
                        lineNum = lineNum - 1 + offsetValue;
                        scanner.close();
                        if (lineNum >= 0) {
                            scanner = new Scanner(path);
                            for (int i = 0; i < lineNum && scanner.hasNextLine(); i++) {
                                scanner.nextLine();
                            }
                        }
                    }
                }
                default -> throw new IllegalStateException(String.format("lineNum=%d inst=%s x=%s", lineNum, inst, x));
            }
        }
        System.out.println("registers[a] = " + registers.getValue("a"));
    }

    static class Registers {
        Map<String, Integer> registers;

        public Registers() {
            this.registers = new HashMap<>(Map.of("a", 0, "b", 0, "c", 0, "d", 0));
        }

        public int getValue(String input) {
            var value = registers.get(input);
            return value != null ? value : Integer.valueOf(input);
        }


        public void setValue(String register, int value) {
            registers.put(register, value);
        }

        @Override
        public String toString() {
            return "Registers{" +
                    "registers=" + registers +
                    '}';
        }
    }
}
