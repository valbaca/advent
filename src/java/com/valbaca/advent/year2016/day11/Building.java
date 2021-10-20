package com.valbaca.advent.year2016.day11;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Data
class Building implements Comparable<Building> {
    List<Floor> floors;
    int elevatorPos;
    transient int steps;

    public Building(int numFloors) {
        this.floors = new ArrayList<>(numFloors);
        for (int i = 0; i < numFloors; i++) {
            floors.add(new Floor());
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

    private int getTopFloor() {
        return floors.size() - 1;
    }

    public boolean isSolved() {
        if (elevatorPos != getTopFloor()) {
            return false;
        }
        for (int i = 0; i < getTopFloor(); i++) {
            if (!floors.get(i).isEmpty()) return false;
        }
        return true;
    }

    public boolean isSafe() {
        for (Floor floor : floors) {
            if (!floor.isSafe()) return false;
        }
        return true;
    }

    @Override
    public int compareTo(Building o) {
        var compareSteps = Integer.compare(this.steps, o.steps);
        if (compareSteps != 0) return compareSteps;
        // compareTo+getScore are critical for the priority queue to drive *toward* the solution
        // and work the best options first
        return Integer.compare(getScore(), o.getScore());
    }

    @Getter(lazy = true)
    @HashCodeExclude
    private final int score = calcScore();

    public int calcScore() {
        int score = 0;
        for (int i = 0; i < floors.size(); i++) {
            score += (floors.get(i).size() << i); // higher floors give way more points
        }
        return -score; // invert the score since PriorityQueue is a min-heap
    }

    private Building move(int prev, int next, Item... items) {
        if (next < 0 || next >= floors.size()) return null;
        Building moved = new Building(this);
        for (Item item : items) {
            moved.floors.get(prev).remove(item);
            moved.floors.get(next).add(item);
        }
        if (!moved.isSafe()) return null;
        moved.elevatorPos = next;
        moved.steps += 1;
        return moved;
    }

    public Stream<Building> generateOptions() {
        var pos = elevatorPos;
        var currentFloor = floors.get(pos);
        var items = currentFloor.getItems();
        List<Building> options = new ArrayList<>(4);
        for (var currItem : items) {
            options.add(move(pos, pos + 1, currItem));
            options.add(move(pos, pos - 1, currItem));
            for (var secItem : items) {
                if (currItem == secItem) continue;
                options.add(move(pos, pos + 1, currItem, secItem));
                options.add(move(pos, pos - 1, currItem, secItem));
            }
        }
        return options.stream().filter(Objects::nonNull);
    }

}
