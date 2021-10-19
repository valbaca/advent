(ns advent-clj.year2015.day2
  (:require [advent-clj.elf :refer [->int lines sum]]
            [clojure.string :refer [split]]))

; TIL: easy peasy
; - Rule of 3: move to threading macro once you're 3 parens deep
; - avoid let-bindings for values used once (unless it really helps)


(defn parse [s] (map ->int (split s #"x")))

(defn- calc-wrapping [[l w h]] (+ (* l w) (* 2 l w) (* 2 w h) (* 2 h l)))

(defn wrapping [xs] (calc-wrapping (sort xs)))

(defn part1 []
  (->> "input/year2015/day2.txt"
       lines
       (map parse)
       (map wrapping)
       sum))

(comment (part1))

(defn calc-bow [[l w h]] (+ l l w w (* l w h)))

(defn bow [xs] (calc-bow (sort xs)))

(defn part2 []
  (->> "input/year2015/day2.txt"
       lines
       (map parse)
       (map bow)
       sum))

(comment (part2))