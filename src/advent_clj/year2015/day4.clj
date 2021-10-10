(ns advent-clj.year2015.day4
  (:require [digest :refer [md5]]
            [clojure.string :refer [starts-with?]]))

; TIL: sometimes the #() anon fn is wonky with simple return values

(defn find-prefix [key prefix]
  (->> (range)
       (map #(vector % (md5 (str key %))))
       (filter #(starts-with? (second %) prefix))
       first ; get solution
       first)) ; get index

(defn part1 [key] (find-prefix key "00000"))

(def input "yzbqklnj")

(comment (part1 input)) ;; => 282749

(defn part2 [key] (find-prefix key "000000"))

(comment (part2 input)) ;; => 9962624
