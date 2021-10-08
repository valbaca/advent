package com.valbaca.advent.elf;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Class to mess around in
 */
public class Playground {

    @SneakyThrows
    public static void main(String[] args) {
        playground3();
    }

    private static void playground3() {
        String str = "abc18";
        var md5Hasher = new DigestUtils(MessageDigestAlgorithms.MD5);
        var hash = md5Hasher.digestAsHex(str.getBytes(StandardCharsets.UTF_8));
        System.out.println(hash);

        System.out.println(MD5Hasher.hexHash("abc18"));

        System.out.println(MD5Hasher.hexHash("hijkl"));

    }

    private static void playground2() {
        ForkJoinPool pool = (ForkJoinPool) Executors.newWorkStealingPool();
        System.out.println(pool.getParallelism());
        System.out.println(pool.getPoolSize());
    }

    private static void playground1() {
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
