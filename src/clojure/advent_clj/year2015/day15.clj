(ns advent-clj.year2015.day15
  (:require [advent-clj.elf :refer :all]
            [clojure.string]))

(def seen (atom #{}))

(defn in-seen [x] (in? @seen x))
(defn add-seen [x] (swap! seen conj x))

(def max-val (atom 0))
(defn update-max [x] (swap! max-val #(max %1 %2) x))

(defn parse [line]
  (let [[name & _] (clojure.string/split line #"[\s:]")
        props (pick-ints line)]
    {name (vec props)}))

(defn lines->ingredients [lines] (into {} (map parse lines)))

;(def ings (lines->ingredients (lines "input/year2015/day15-test.txt")))
(def ings (lines->ingredients (lines "input/year2015/day15.txt")))

;; ingredients name count
(defn sub-score [i [n c]] (map #(* c %) (get i n)))

;; ingredients {"name" vals}
;; measurements {"name" count}
(defn mult-vals [i m]
  (apply map +
         (map
           #(sub-score i [% (get m %)])
           (keys m))))

(defn score-helper [i m] (butlast (mult-vals i m)))

(defn score [i m]
  (reduce * (map clamp (score-helper i m))))

(score ings {"Butterscotch" 44 "Cinnamon" 56})

(defn full? [m] (= 100 (sum (vals m))))

(defn add "nil-safe inc" [n] (if (nil? n) 1 (inc n)))

(defn brew
  ([i] (brew i (fn [v & _] v)))
  ([i ff] (brew i (keys i) {} ff))
  ;; ingredients keys measurements filter-function
  ([i k m ff]
   (if (in-seen m)
     0
     (do
       (add-seen m)
       (if (full? m)
         (update-max (ff (score i m) i m))
         (largest (map #(brew i k (update m % add) ff) k)))))))

(defn part1 []
  (do
    (reset! seen #{})
    (reset! max-val 0)
    (brew ings)
    @max-val))

(defn diet-filter [v i m] (if (= 500 (last (mult-vals i m))) v 0))

(defn part2 []
  (do
    (reset! seen #{})
    (reset! max-val 0)
    (brew ings diet-filter)
    @max-val))