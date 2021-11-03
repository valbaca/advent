(ns advent-clj.year2016.day6
  (:require
    [advent-clj.elf :refer :all]
    [advent-clj.twod :refer :all]
    [clojure.string :as s]))

(defn parse [lines]
  (->> lines strs->grid transpose))

(def test-input (parse (ns-test-input)))
(def input (parse (ns-input)))

(defn part1 [input] (map-str (comp first sorted-by-freq) input))

(defn part2 [input] (map-str (comp last sorted-by-freq) input))
