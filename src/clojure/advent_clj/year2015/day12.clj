(ns advent-clj.year2015.day12
  (:require 
   [clojure.string :as s]
   [clojure.data.json :as j]
   [advent-clj.elf :refer [in? pick-ints ->int sum lines]]))

; TIL:
; This one really shined with Clojure:
; Simple case was handled simply with a regex split
; Complex case handled by good JSON handling (access to good libs) and the fact
; that Clojure maps naturally to JSON (i.e. edn)

(defn only-ints [s]
  (remove nil? (map ->int (s/split s #"[^\d-]"))))

(defn part1 [s] (sum (only-ints s)))

(def input (first (lines "input/year2015/day12.txt")))

(defn part1 [] (sum (only-ints input)))

(declare sum-json)

(defn sum-json-map [obj]
  ;; if has key "red" return 0. else, return sum map of vals
  (let [v (vals obj)]
    (if (in? v "red")
      0
      (sum (map sum-json v)))))

(defn sum-json [obj]
  (cond
    (vector? obj) (sum (map sum-json obj))
    (map? obj) (sum-json-map obj)
    (number? obj) obj
    :else 0))

(defn part2 [] (sum-json (j/read-str input)))

