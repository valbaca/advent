package com.valbaca.advent.year2016;

import com.valbaca.advent.elf.MD5Hasher;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static com.valbaca.advent.elf.Timer.measure;

/**
 * TIL:
 * - Glad Apache had a nice wrapper around MD5
 * - Using a tad of FP via the "buildHasher" method, which returns a function that acts like a dynamic constructor, was
 * natural and perfect for this. Made extending to Part 2 very easy by just moving the "hasher" up
 * - Definitely overused the terms "hash" and "hasher"
 * - Learned you cannot have a static method and instance method share a signature. It's never come up before and it's
 * probably better that way...but also not something I'd expect in, say, Ruby or Python
 */
public class Day14 {
    public static void main(String[] args) {
        System.out.println("Day 14");
        /*
        Plan:
        load 1001 hashes into a deque, then iterate.
        Could instead load the hash, any triples found and any quintuples found
         */
        System.out.println("Testing...");
        measure(() -> new Day14().run(buildSingleHasher("abc")));

        System.out.println("Part 1...");
        measure(() -> new Day14().run(buildSingleHasher("yjdafjpo")));

        System.out.println("Part 2 testing");
        measure(() -> new Day14().run(buildStretchHasher("abc"))); // gives 22551

        System.out.println("Part 2...");
        measure(() -> new Day14().run(buildStretchHasher("yjdafjpo"))); // but 22034 is wrong?
    }

    public static Function<Integer, Hash> buildSingleHasher(String salt) {
        var md5Hasher = new MD5Hasher();
        return (Integer i) -> new Hash(i, md5Hasher.hex(salt + i));
    }

    public static Function<Integer, Hash> buildStretchHasher(String salt) {
        var md5Hasher = new MD5Hasher();
        return (Integer i) -> {
            String s = salt + i;
            for (int j = 0; j <= 2016; j++){
                s = md5Hasher.hex(s);
            }
            return new Hash(i, s);
        };

    }

    private void run(Function<Integer, Hash> hasher) {
        Deque<Hash> deque = new ArrayDeque<>();
        for (int i = 0; i < 1000; i++) {
            deque.add(hasher.apply(i));
        }
        int keysFound = 0;
        int indexOfLastFound = -1;
        int index = 0;
        int endIndex = 1000;
        while (keysFound < 64) {
            deque.add(hasher.apply(endIndex));
            endIndex++;

            var currHash = deque.pollFirst();
            assert currHash != null;
            if (currHash.trip != null) {
                var iterator = deque.iterator();
                boolean foundQuint = false;
                while (iterator.hasNext() && !foundQuint) {
                    var next = iterator.next();
                    if (next.getQuints().contains(currHash.trip)) {
                        foundQuint = true;
                        keysFound++;
                        indexOfLastFound = index;
                    }
                }
            }
            index++;
        }
        System.out.println("Index of 64th key=" + indexOfLastFound);
    }

    static class Hash {
        @Getter
        private final int index;
        @Getter
        private final String hash;
        @Getter
        @Nullable
        private final Character trip;
        @Getter(lazy = true)
        private final Set<Character> quints = findQuints();

        public Hash(int index, @NonNull String hash) {
            this.index = index;
            this.hash = hash;
            this.trip = findTrip(hash);
        }

        static Character findTrip(String hash) {
            var chars = hash.toCharArray();
            for (int i = 0; i < chars.length - 2; i++) {
                char c = chars[i];
                if (c == chars[i + 1] && c == chars[i + 2]) {
                    return c;
                }
            }
            return null;
        }

        Set<Character> findQuints() {
            Set<Character> set = new HashSet<>();
            var chars = this.hash.toCharArray();
            for (int i = 0; i < chars.length - 4; i++) {
                char c = chars[i];
                boolean matches = true;
                for (int j = 1; j < 5; j++) {
                    if (c != chars[i + j]) {
                        matches = false;
                        break;
                    }
                }
                if (matches) set.add(c);
            }
            return set;
        }
    }
}
