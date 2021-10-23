(ns advent-clj.year2015.day17
  (:require [advent-clj.elf :as elf]))

;; TIL:
;; "powerset" is missing from clojure.math.combinatorics because that library de-dupes
;; and for this problem, each number is a *distinct* eggnog container
(defn part1 [target input]
  (filter #(= target (elf/sum %)) (elf/powerset input)))

(part1 25 [20 15 10 5 5])

(def input (sort (map elf/->int (elf/lines "input/year2015/day17.txt"))))
(count (part1 150 input))

(defn part2 [target input]
  (let [by-count (group-by count (part1 target input))
        min-count (elf/smallest (keys by-count))]
    (get by-count min-count)))

(part2 25 [20 15 10 5 5])
(count (part2 150 input))
