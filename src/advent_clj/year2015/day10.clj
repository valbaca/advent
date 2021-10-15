(ns advent-clj.year2015.day10)

;; TIL:
;; - This one really shows the power of clojure (or just FP in general)
;; The "hardest" part was finding the right function: partition-by
;; - Learned `juxt` is a great way to shorten applying functions where the results all go in a vector:
;; `(juxt f g) == (fn [a] [(f a) (g a)])
;; juxt is kind of like the flip of map
;; map applies a function to multiple inputs
;; juxt applies multiple functions to an input

(defn say [s]
  (->> s
       (partition-by identity) ;; groups runs of numbers
       (mapcat (juxt count first)) ;; the "say" part
       (apply str)))
(say "111221")
(defn test-part1 [] (take 6 (iterate say "1")))
(defn part1 [] (count (nth (iterate say "1113122113") 40)))
(defn part2 [] (count (nth (iterate say "1113122113") 50)))