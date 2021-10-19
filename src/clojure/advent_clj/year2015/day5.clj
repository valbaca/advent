(ns advent-clj.year2015.day5
  (:require
   [clojure.string :refer [index-of]]
   [advent-clj.elf :refer [prev-curr lines in? any iter-rest]]))

; TIL:
; - This one was honestly a pain in clojure (or at least with my skill)
; - Still figuring out state-based iterations and the core fns
; - Also tripped on nil/false. Once sorted, the code is cleaner
; - Use (seq xs) instead of (not (empty? xs))
; - Surprised I had to write `any`, the `any?` in core seems like a waste of a valuable word

(defn three-vowels? [s]
  (let [vowels #{\a \e \i \o \u}]
    (<= 3 (count (filter #(in? vowels %) (seq s))))))

(defn letter-twice? [s]
  (->> (seq s)
       prev-curr
       (filter #(= (first %) (second %)))
       seq))

(defn has-bad-strings? [s]
  (let [bad ["ab" "cd" "pq" "xy"]]
    (any (map #(index-of s %) bad))))

(defn nice? [s]
  (and
   (three-vowels? s)
   (letter-twice? s)
   (not (has-bad-strings? s))))

(def input (lines "input/year2015/day5.txt"))

(defn part1 [] (count (filter nice? input)))

(comment (part1)) ;; => 255

;; This one still feels over-complicated, but it's working
(defn has-pair-of-two?
  [s]
  (->> s
       seq
       prev-curr
       iter-rest
       (map #(if (in? (nthrest % 2) (first %)) (first %) nil))
       any))

(defn repeat-with-one-between? [s] (re-find #"(\w).\1" s))

(defn nice2? [s]
  (and
   (has-pair-of-two? s)
   (repeat-with-one-between? s)))

(defn part2 [] (count (filter nice2? input)))

(comment (part2)) ;; => 55