(ns advent-clj.elf
  (:require [clojure.string :as s]))

(defn min-of
  "Returns the smallest (per compare) element of sequence"
  ([xs] (min-of compare xs))
  ([comp xs] (reduce #(if (neg? (comp %1 %2)) %1 %2) xs)))

(defn sum "Add elements" [xs] (reduce + 0 xs))

(defn product "Multiply elements" [xs] (reduce * xs))

(defn lines
  "Gets the (clean) lines of a file"
  [filename]
  (->> filename
       slurp
       s/split-lines
       (map s/trim)))

(defn lines->ints
  "Gets the lines of file as Integers"
  [filename]
  (map #(Integer/parseInt %) (lines filename)))
