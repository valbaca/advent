(ns advent-clj.year2015.day16
  (:require [advent-clj.elf :refer :all]))

;; TIL:
;; This one is perfect for clojure, got this done in no time
;; Pretty straightforward and really leverages the data-first
;; and succinct nature of clojure.
;; You can especially see the requirements spelled out in the code
;; like with `retro-matches`

(def tgt
  {"children"    3
   "cats"        7
   "samoyeds"    2
   "pomeranians" 3
   "akitas"      0
   "vizslas"     0
   "goldfish"    5
   "trees"       3
   "cars"        2
   "perfumes"    1})

(def input (ns-input))

(defn parse [line]
  (->> (clojure.string/split line #"[\s:,]")
       (remove #(= "" %))
       (map str<->int)))

(defn aunt-maps [input]
  (map #(apply hash-map %) (map parse input)))

;; part 1
(filter
  (fn [aunt]
    (every?
      #(or (not (contains? aunt %))
           (= (get aunt %) (get tgt %)))
      (keys tgt)))
  (aunt-maps input))

;; part 2
(defn retro-matches [actual k target]
  (cond
    (or (= k "cats") (= k "trees"))
    (< target actual)
    (or (= k "pomeranians") (= k "goldfish"))
    (< actual target)
    :else
    (= actual target)))

(defn retro-filter [tgt]
  (fn [aunt]
    (every?
      #(or (not (contains? aunt %))
           (retro-matches (get aunt %) % (get tgt %)))
      (keys tgt))))

(filter (retro-filter tgt) (aunt-maps input))