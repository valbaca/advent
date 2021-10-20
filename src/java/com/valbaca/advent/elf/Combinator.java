package com.valbaca.advent.elf;

import com.google.common.collect.ImmutableList;
import org.apache.commons.math3.util.Combinations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Convienance wrapper around {@link Combinations}
 */
public class Combinator {

    /**
     * Returns an iterator that gives each combination for the provided n-options and k-choices.
     * @param n collection of options, generally a list of integers or strings
     * @param k how many choices should be made from n
     * @return an iterator that provides each combination (until exhausted)
     */
    public static <T> Iterable<List<T>> nChooseK(Collection<? extends T> n, int k) {
        return new ComboIter<>(n, k);
    }

    /**
     *
     * @param <T> is the type of option given, likely Integer, Double, or String
     */
    private static class ComboIter<T> implements Iterator<List<T>>, Iterable<List<T>> {
        List<T> options;
        Iterator<int[]> combos;

        public ComboIter(Collection<? extends T> options, int k) {
            this.options = ImmutableList.copyOf(options);
            this.combos = new Combinations(options.size(), k).iterator();
        }

        @Override
        public boolean hasNext() {
            return combos.hasNext();
        }

        @Override
        public List<T> next() {
            var nextIndexes = combos.next();
            var next = new ArrayList<T>(nextIndexes.length);
            for (int i : nextIndexes) {
                next.add(options.get(i));
            }
            return next;
        }

        @Override
        public Iterator<List<T>> iterator() {
            return this;
        }
    }
}
