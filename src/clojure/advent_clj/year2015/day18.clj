(ns advent-clj.year2015.day18
  (:require [advent-clj.elf :refer :all]
            [clojure.string :as s]))

;; TIL:
;; Clojure is pretty slow when used liked this
;; I could probably get much faster speeds by using char-arrays directly
;; But seqs/vecs allow for better idiomatic use
;; Still fast enough
;; Finally got around to creating ns based automatic file input getters
;; usually I make the mistake of creating too many input-based helpers

;; Next-level laziness could be to have ns-input automatically create vars named
;; input and test-input
;; but that could be getting too clever, and it's already pushing it

(def test-input (ns-test-input))
(def input (ns-input))

(defn print-grid [grid]
  (print (s/join \newline grid)))

(def ON \#)
(def OFF \.)

(defn on? [state] (= state ON))
(defn count-on [xs] (count (filter on? xs)))

(defn next-state-from-on [c]
  (if (or (= c 2) (= c 3)) ON OFF))

(defn next-state-from-off [c]
  (if (= c 3) ON OFF))

(defn next-state [curr-state neighbors-on]
  (if (on? curr-state)
    (next-state-from-on neighbors-on)
    (next-state-from-off neighbors-on)))

(defn get-grid [grid r c]
  ;; implicitly using the fact that when returns nil if false
  ;; which is what we want, because nil is not "ON"
  (when (and (< -1 r (count grid))
             (< -1 c (count (first grid))))
    (nth (nth grid r) c)))

(defn get-neighbors [grid r c]
  (let [delta [-1 0 1]]
    (for [row-delta delta
          col-delta delta
          :when (not (and (zero? row-delta) (zero? col-delta)))]
      (get-grid grid (+ r row-delta) (+ c col-delta)))))

(defn step-grid [grid]
  (for [r (range (count grid))]
    (apply str
      (for [c (range (count (first grid)))
            :let [curr (get-grid grid r c)
                  nbs (count-on (get-neighbors grid r c))]]
        (next-state curr nbs)))))

(defn count-on-grid [grid]
  (sum (map (comp count-on seq) grid)))

(defn part1 []
  (count-on-grid (nth (iterate step-grid input) 100)))

(defn turn-first-last-on [s]
  (str ON (subs s 1 (dec (count s))) ON))

(defn stuck-grid [grid]
  (->
    grid
    vec
    (update 0 turn-first-last-on)
    (update (dec (count grid)) turn-first-last-on)))

(defn stuck-step-grid [grid]
  (-> grid step-grid stuck-grid))

(def part2input (stuck-grid input))

(defn part2 []
  (count-on-grid (nth (iterate stuck-step-grid part2input) 100)))