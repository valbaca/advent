(ns advent-clj.year2016.day2
  (:require [advent-clj.elf :refer :all]
            [advent-clj.twod :refer :all]))

(def input (map seq (ns-input)))

;; UDLR
(def dir->idx {\U 0 \D 1 \L 2 \R 3})

(def next-num9
  {\1 [\1 \4 \1 \2]
   \2 [\2 \5 \1 \3]
   \3 [\3 \6 \2 \3]
   \4 [\1 \7 \4 \5]
   \5 [\2 \8 \4 \6]
   \6 [\3 \9 \5 \6]
   \7 [\4 \7 \7 \8]
   \8 [\5 \8 \7 \9]
   \9 [\6 \9 \8 \9]
   })
(defn numpad
  [config]
  (fn [prev dir]
    (let [idx (dir->idx dir)]
      (get (config prev) idx))))

(defn solve [config]
  (map #(reduce (numpad config) \5 %) input))

(defn part1 [] (solve next-num9))

(def next-numD
  {\1 [\1 \3 \1 \1]
   \2 [\2 \6 \2 \3]
   \3 [\1 \7 \2 \4]
   \4 [\4 \8 \3 \4]
   \5 [\5 \5 \5 \6]
   \6 [\2 \A \5 \7]
   \7 [\3 \B \6 \8]
   \8 [\4 \C \7 \9]
   \9 [\9 \9 \8 \9]
   \A [\6 \A \A \B]
   \B [\7 \D \A \C]
   \C [\8 \C \B \C]
   \D [\B \D \D \D]})

(defn part2 [] (solve next-numD))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Solving again with twod.clj
(def dir->fn {\U up \D down \L left \R right})

(defn grid-move [[grid r c] d]
  (let [new-rc ((dir->fn d) grid r c)]
    (if (and new-rc (not= \space (ggrid grid new-rc)))
      [grid (first new-rc) (second new-rc)]
      [grid r c])))

(defn grid-travel
  [grid start-r start-c]
  (fn [input]
    (let [[_ r c] (reduce grid-move [grid start-r start-c] input)]
      (ggrid grid r c))))

(def num9 "123
456
789")
(defn part1grid [] (map (grid-travel (s->grid num9) 1 1) input))

(def numD
  "  1
   234
  56789
   ABC
    D")
(defn part2grid [] (map (grid-travel (s->grid numD) 2 0) input))