(ns advent-clj.year2016.day1
  (:require [advent-clj.elf :refer :all]
            [clojure.set]))

;; TIL:
;; - Solved this one offline, to really start testing myself
;; - Got stuck on part 2 because I missed you have to track every-step (unless you store ranges)
;; Even then, the end result is a bit of a mess, but used `reduced` to good effect.
;; - Having to write my own abs function to avoid dealing with types

(defn parse-line [line]
  (let [[dir & dist] line]
    [dir (->int (apply str dist))]))

(def input (map parse-line (sep (first (ns-input)))))

(def turn-right {:N :E :E :S :S :W :W :N})
(def turn-left (clojure.set/map-invert turn-right)) ;; cute

(defn turn [curr lr]
  (update curr 0 (if (= lr \R) turn-right turn-left)))

(def calc-dist-diff {:N [2 1] :E [1 1] :S [2 -1] :W [1 -1]})

(defn go-dist [curr dist]
  (let [[idx sign] (calc-dist-diff (first curr))]
    (update curr idx (plus (* sign dist)))))

(defn go [curr [lr dist]] (go-dist (turn curr lr) dist))

(def start [:N 0 0]) ;; [0 dir 1 x 2 y]

(defn manh-dist
  ([[_ x y]] (manh-dist x y))
  ([x y] (+ (abs x) (abs y))))

(defn part1 [input]
  (manh-dist (reduce go start input)))

;; loc = [direction x y], pos = [x y]

(defn positions-to [[sx sy] [ex ey]]
  (rest ;; drop the first so we don't overlap
    (for [x (to sx ex)
          y (to sy ey)]
      [x y])))

(defn loc->pos [[_ x y]] [x y])

(defn travel-first-seen-twice [[start-loc seen] inst]
  (let [next-loc (go start-loc inst)
        each-between (positions-to (loc->pos start-loc) (loc->pos next-loc))]
    (if-let [seen-twice (first (filter seen each-between))]
      (reduced seen-twice)
      [next-loc (into seen each-between)])))

(defn part2 [input]
  (apply
    manh-dist
    (reduce travel-first-seen-twice [start #{(loc->pos start)}] input)))