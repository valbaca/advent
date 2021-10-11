(ns advent-clj.elf
  (:require [clojure.string :as s]))

(defn smallest
  "Returns the smallest element of xs according to compare. First wins ties."
  ([xs] (smallest compare xs))
  ([comp xs] (reduce #(if (<= (comp %1 %2) 0) %1 %2) xs)))

(defn largest
  "Returns the largest element of xs according to compare. First wins ties."
  ([xs] (largest compare xs))
  ([comp xs] (reduce #(if (>= (comp %1 %2) 0) %1 %2) xs)))

(defn sum "Add elements" [xs] (reduce + 0 xs))

(defn product "Multiply elements" [xs] (reduce * xs))

(defn lines
  "Gets the trimmed lines of a file as a seq"
  [filename]
  (->> filename
       slurp
       s/split-lines
       (map s/trim)))

(defn ->int "string to int"[s] (Integer/parseInt s))

(defn lines->ints
  "Gets the lines of file as Integers"
  [filename]
  (map #(Integer/parseInt %) (lines filename)))

(defn every-other
  "Returns a pair of streams, first is every even item, second is every odd"
  [xs]
  (apply map list (partition 2 xs)))

(defn prev-curr 
  "Returns a sequence of pairs of each element with their prev.
   
   (prev-curr [:a :b :c :d]) ; => ((:a :b) (:b :c) (:c :d))"
  [xs]
  (partition 2 1 xs))

(defn in? [xs x]
  (cond
    (or (vector? xs) (list? xs)) (<= 0 (.indexOf xs x))
    (set? xs) (contains? xs x)
    (map? xs) (in? (vals xs) x) ; my decision to not just replicate contains?
    (seq? xs) (in? (vec xs) x)
    :else (throw (Exception. "Invalid collection provided for xs"))))

(defn iter-rest
  "Given a seq, returns seq of rest called repeatedly.
   [:a :b :c] => ([:a :b :c] [:b :c] [:c])"
  [xs]
  (take (count xs) (iterate rest xs)))