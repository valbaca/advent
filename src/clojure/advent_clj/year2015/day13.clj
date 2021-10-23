(ns advent-clj.year2015.day13
  (:require [advent-clj.elf :refer :all]
            [clojure.math.combinatorics :refer [permutations]]))

;; TIL:
;; This had an interesting part 2 that was easily handled with only minor modifications:
;;   provide default 0 for get-in
;;   have `solve` take in an optional arg via `rest`
;;   simply slot in :self within `people`

(defn parse [line]
  (let [[a _ gain-loss val & rest] (split-ints line)
        b (last rest)]
    [a b (if (= "gain" gain-loss) val (- val))]))

(defn seating-map [xs]
  (reduce (fn [acc [a b v]] (assoc-in acc [a b] v)) {} xs))

(defn round-table-pairs [xs]
  (let [pc (prev-curr xs)]
    (conj pc (list (first xs) (last xs)))))

(defn seating-score [m xs]
  (sum
    (map
      (fn [[a b]]
        (+ (get-in m [a b] 0)
           (get-in m [b a] 0)))
      xs)))

(defn solve [input-lines & rest]
  (let [m (seating-map (map parse input-lines))
        people (concat (keys m) rest)
        perms (permutations people)
        table-perms (map round-table-pairs perms)]
    (largest (map #(seating-score m %) table-perms))))

(solve (ns-test-input))

; part 1
(def input (ns-input))
(solve input)
; part 2
(solve input :self)