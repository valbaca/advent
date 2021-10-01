package com.valbaca.advent.year2016;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class Day11 {

    public static final int NUM_FLOORS = 4;
    public static final int TOP_FLOOR = NUM_FLOORS - 1;

    public static void main(String[] args) {
        new Day11().runner(testInit());
        System.out.println("Test done.");
        System.out.println("Part 1 start...");
        new Day11().runner(part1Input());
        System.out.println("...Part 1 done");

        System.out.println("Part 2...");
        new Day11().runner(part2Input());
        System.out.println("...Part 2 done");
    }

    public static Building testInit() {
        Building init = new Building(NUM_FLOORS);
        init.floors.get(0).items.addAll(
                List.of(
                        new Item(Element.hydrogen, Type.chip),
                        new Item(Element.lithium, Type.chip)));
        init.floors.get(1).items.add(new Item(Element.hydrogen, Type.gen));
        init.floors.get(2).items.add(new Item(Element.lithium, Type.gen));
        return init;
    }


    private static Building part1Input() {
        Building init = new Building(NUM_FLOORS);
        var floorOneItems = List.of(
                new Item(Element.polonium, Type.gen),
                new Item(Element.thulium, Type.gen),
                new Item(Element.thulium, Type.chip),
                new Item(Element.promethium, Type.gen),
                new Item(Element.ruthenium, Type.gen),
                new Item(Element.ruthenium, Type.chip),
                new Item(Element.cobalt, Type.gen),
                new Item(Element.cobalt, Type.chip));
        init.floors.get(0).items.addAll(floorOneItems);

        var floorTwoItems = List.of(
                new Item(Element.polonium, Type.chip),
                new Item(Element.promethium, Type.chip)
        );
        init.floors.get(1).items.addAll(floorTwoItems);
        return init;
    }

    private static Building part2Input() {
        Building init = new Building(NUM_FLOORS);
        var floorOneItems = List.of(
                new Item(Element.polonium, Type.gen),
                new Item(Element.thulium, Type.gen),
                new Item(Element.thulium, Type.chip),
                new Item(Element.promethium, Type.gen),
                new Item(Element.ruthenium, Type.gen),
                new Item(Element.ruthenium, Type.chip),
                new Item(Element.cobalt, Type.gen),
                new Item(Element.cobalt, Type.chip),
                new Item(Element.elerium, Type.gen),
                new Item(Element.elerium, Type.chip),
                new Item(Element.dilithium, Type.gen),
                new Item(Element.dilithium, Type.chip)
        );
        init.floors.get(0).items.addAll(floorOneItems);

        var floorTwoItems = List.of(
                new Item(Element.polonium, Type.chip),
                new Item(Element.promethium, Type.chip)
        );
        init.floors.get(1).items.addAll(floorTwoItems);
        return init;
    }

    private void runner(Building init) {

        PriorityQueue<Building> pq = new PriorityQueue<>();
        pq.add(init);

        Set<Building> seen = Collections.synchronizedSet(new HashSet<>());
        seen.add(init);
        int minSteps = Integer.MAX_VALUE;
        while (!pq.isEmpty()) {
            Building b = pq.poll();
            if (b.steps >= minSteps) continue;
            if (b.isSolved()) {
                minSteps = b.steps;
                System.out.println("New Min!: " + minSteps);
                continue;
            }
            var options = b.generateOptions();
            var unseen = Sets.difference(options, seen).immutableCopy();
            pq.addAll(unseen);
            seen.addAll(unseen);
        }
    }


    @Data
    static class Building implements Comparable<Building> {
        @EqualsAndHashCode.Include
        List<Floor> floors;
        @EqualsAndHashCode.Include
        int elevatorPos;
        @EqualsAndHashCode.Exclude
        int steps;

        public Building(int numFloors) {
            this.floors = new ArrayList<>(numFloors);
            for (int i = 0; i < numFloors; i++) {
                floors.add(new Floor(i));
            }
            this.elevatorPos = 0;
            this.steps = 0;
        }

        public Building(Building other) {
            this.floors = new ArrayList<>(other.floors.size());
            for (var floor : other.floors) {
                this.floors.add(new Floor(floor));
            }
            this.elevatorPos = other.elevatorPos;
            this.steps = other.steps;
        }


        public boolean isSolved() {
            if (elevatorPos != TOP_FLOOR) {
                return false;
            }
            for (int i = 0; i < TOP_FLOOR; i++) {
                if (!floors.get(i).isEmpty()) return false;
            }
            return true;
        }

        public boolean isSafe() {
            return floors.stream().allMatch(Floor::isSafe);
        }

        @Override
        public int compareTo(Building o) {
            if (o == null) return -1;
            // compareTo+getScore are critical and drive the solution *toward* the solution and work the best options
            var stepsCompare = Integer.compare(this.steps, o.steps);
            if (stepsCompare != 0) return stepsCompare;
            return Integer.compare(this.getScore(), o.getScore());
        }

        public int getScore() {
            int score = 0;
            for (int i = 0; i < NUM_FLOORS; i++) {
                score += (floors.get(i).getItems().size() << i); // higher floors give more points
            }
            return -score; // invert the score since PriorityQueue is a min-heap
        }

        private Building move(int prev, int next, Item... items) {
            if (next < 0 || next > TOP_FLOOR) return null;
            Building moved = new Building(this);
            for (Item item : items) {
                moved.floors.get(prev).items.remove(item);
                moved.floors.get(next).items.add(item);
            }
            if (!moved.isSafe()) return null;
            moved.elevatorPos = next;
            moved.steps += 1;
            return moved;
        }

        public Set<Building> generateOptions() {
            var pos = elevatorPos;
            var currentFloor = floors.get(pos);
            var items = new ArrayList<>(currentFloor.items);
            var size = items.size();
            List<Building> options = new ArrayList<>(size * size);
            for (int i = 0; i < items.size(); i++) {
                var currItem = items.get(i);
                options.add(move(pos, pos + 1, currItem));
                options.add(move(pos, pos - 1, currItem));
                for (int j = i + 1; j < size; j++) {
                    var secItem = items.get(j);
                    options.add(move(pos, pos + 1, currItem, secItem));
                    options.add(move(pos, pos - 1, currItem, secItem));
                }
            }
            return options.stream().filter(Objects::nonNull).collect(Collectors.toSet());
        }

    }

    enum Element {
        hydrogen,
        lithium,
        polonium,
        thulium,
        promethium,
        ruthenium,
        cobalt,
        elerium,
        dilithium
    }

    enum Type {
        gen, chip;
    }

    record Item(Element elem, Type type) {
    }

    @Data
    static class Floor {
        @Getter
        Set<Item> items;
        int floorNumber;

        public Floor(int number) {
            this.items = new HashSet<>();
            this.floorNumber = number;
        }

        public Floor(Floor other) {
            this.items = new HashSet<>(other.items);
            this.floorNumber = other.floorNumber;
        }

        public boolean isEmpty() {
            return items.isEmpty();
        }

        public boolean isSafe() {
            if (isEmpty()) return true;
            Set<Element> chips = items.stream().filter(i -> i.type() == Type.chip).map(Item::elem).collect(Collectors.toSet());
            Set<Element> gens = items.stream().filter(i -> i.type() == Type.gen).map(Item::elem).collect(Collectors.toSet());
            var hasExposedChips = !Sets.difference(chips, gens).isEmpty();
            if (hasExposedChips) {
                return items.stream().noneMatch(i -> i.type() == Type.gen);
            }
            return true;
        }
    }
}
