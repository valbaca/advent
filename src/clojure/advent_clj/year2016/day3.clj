(ns advent-clj.year2016.day3
  (:require [advent-clj.elf :refer :all]))

;; TIL:
;; - very straightforward. partition and mapcat are fantastic.
;; - Finding the right "level" to write functions at. Where parsing ends and computation begins.
;; Low-level functions are composable, but to do more may need rewriting.

(def input (map sep (ns-input)))

(defn triangle? [xyz]
  (let [[x y z] (sort xyz)]
    (< z (+ x y))))

(defn part1 [] (count (filter triangle? input)))

(defn part2 []
  (->> input
       (partition 3)
       (mapcat #(apply map vector %))
       (filter triangle?)
       count))