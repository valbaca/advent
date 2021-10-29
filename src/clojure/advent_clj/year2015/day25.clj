(ns advent-clj.year2015.day25
  (:require
    [advent-clj.elf :refer :all]))

(def init [1 1 20151125])

(defn next-code [[r c val]]
  [(if (= r 1) (inc c) (dec r))
   (if (= r 1) 1 (inc c))
   (rem (* val 252533) 33554393)])

(def target-row 2978)
(def target-col 3083)
(last
  (seek
    (fn [[r c _]] (and (= r target-row) (= c target-col)))
    next-code
    init))