package com.valbaca.advent.year2016.day11;

import lombok.Data;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

import static com.valbaca.advent.year2016.day11.Type.chip;
import static com.valbaca.advent.year2016.day11.Type.gen;

@Data
class Floor {
    EnumSet<Element> chips;
    EnumSet<Element> gens;

    public Floor() {
        chips = EnumSet.noneOf(Element.class);
        gens = EnumSet.noneOf(Element.class);
    }

    public Floor(Floor other) {
        this.chips = EnumSet.copyOf(other.chips);
        this.gens = EnumSet.copyOf(other.gens);
    }

    public void add(Item item) {
        if (item.type() == chip) chips.add(item.elem());
        else gens.add(item.elem());
    }

    public void addAll(Collection<Item> items) {
        for (var item : items) add(item);
    }

    public void remove(Item item) {
        if (item.type() == chip) chips.remove(item.elem());
        else gens.remove(item.elem());
    }

    public List<Item> getItems() {
        var chipItemStream = chips.stream().map(c -> new Item(c, chip));
        var genItemStream = gens.stream().map(g -> new Item(g, gen));
        return Stream.concat(chipItemStream, genItemStream).toList();
    }

    public boolean isEmpty() {
        return chips.isEmpty() && gens.isEmpty();
    }

    public int size() {
        return chips.size() + gens.size();
    }


    public boolean isSafe() {
        if (chips.isEmpty() || gens.isEmpty()) return true;
        var chips = EnumSet.copyOf(this.chips);
        chips.removeAll(gens);
        return chips.isEmpty();
    }
}
