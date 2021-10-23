(ns advent-clj.year2015.day3
  (:require
   [clojure.string :refer [trim]]
   [clojure.set :as set]
   [advent-clj.elf :refer :all]))

; TIL:
; - Using the acc (accumulator) in reduce to keep the state of the problem
; as we go through the problem (move through the neighborhood)
; https://clojure.org/guides/destructuring
; - Could've used pairs, but using maps with :keys is more readable
; - Use a map if the results are constant, otherwise good old case:
; https://clojuredocs.org/clojure.core/case
; - By chosing the right refactoring/abstraction combination for part 1, part 2 was practically trivial

(defn move [[x y] c]
  (case c
    \^ [x (dec y)]
    \v [x (inc y)]
    \< [(dec x) y]
    \> [(inc x) y]))

(defn travel [{:keys [prev seen]} c]
  (let [curr (move prev c)
        hset (conj seen curr)]
    {:prev curr
     :seen hset}))

(defn houses-seen [dirs]
  (let [start [0 0]
        travel-init {:prev start :seen (hash-set start)}]
    (:seen (reduce travel travel-init dirs))))

(def input (seq (first (ns-input))))

(defn part1 [dirs]
  (count (houses-seen dirs)))

(comment (part1 input))

(defn part2 [dirs]
  (let [[santa-dirs robo-dirs] (every-other dirs)
        santa-seen (houses-seen santa-dirs)
        robo-seen (houses-seen robo-dirs)]
    (count (set/union santa-seen robo-seen))))

(comment (part2 input))