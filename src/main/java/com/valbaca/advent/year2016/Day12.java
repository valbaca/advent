package com.valbaca.advent.year2016;

import com.valbaca.advent.elf.Elf;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day12 {
    public static void main(String[] args) {
        System.out.println("Day 12");
        System.out.println("Test");
        new Day12().run(Elf.getPath(2016, 12, true), new Registers());

        System.out.println("Part 1");
        new Day12().run(Elf.getPath(2016, 12, false), new Registers());

        System.out.println("Part 2");
        var part2Registers = new Registers();
        part2Registers.setValue("c", 1);
        new Day12().run(Elf.getPath(2016, 12, false), part2Registers);
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
                case "cpy":
                    registers.setValue(scanner.next(), registers.getValue(x));
                    break;
                case "inc":
                    registers.setValue(x, registers.getValue(x) + 1);
                    break;
                case "dec":
                    registers.setValue(x, registers.getValue(x) - 1);
                    break;
                case "jnz":
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
                    break;
                default:
                    throw new IllegalStateException(String.format("lineNum=%d inst=%s x=%s", lineNum, inst, x));
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
