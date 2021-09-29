package com.valbaca.advent.elf;

import com.google.common.collect.*;
import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Class to mess around in
 */
public class Playground {

    public static void main(String[] args) {
        System.out.println(new Bar("Annie", 1));
        var list = Lists.newArrayList("1", "2", "3");
        System.out.println(list);
        var kebabList = String.join("-", list);
        System.out.println(kebabList);

        var biMap = HashBiMap.<String, String>create();
        biMap.put("a", "alpha");
        System.out.println(biMap.get("a"));
        System.out.println(biMap.inverse().get("alpha"));

        var multiMap = HashMultimap.<String, String>create();
        multiMap.put("langs", "C++");
        multiMap.put("langs", "C");
        multiMap.put("langs", "Java");
        // order isn't guaranteed with HashMultimap, the values are put in a HashSet
        System.out.println(multiMap.get("langs"));

        // To keep insertion order:
        var listMultimap = ArrayListMultimap.create();
        listMultimap.put("langs", "C++");
        listMultimap.put("langs", "C");
        listMultimap.put("langs", "Java");
        listMultimap.put("langs", "Ruby");
        System.out.println(listMultimap.get("langs"));

    }

    // show lombok is working
    @Value
    static class Bar {
        String name;
        Integer id;
    }


}
