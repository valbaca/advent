(ns advent-clj.year2015.day20
  (:require [advent-clj.elf :refer :all]))

;; TIL:
;; - Past the problems I've solved before in Go, now onto Python
;; Clojure seems to have runtime similar to Python
;; So we'll see how much advantage clojure gives

(def tgt 33100000)

(defn factors [n]
  (->> (range 1 (inc (Math/sqrt n)))
       (filter #(zero? (rem n %)))
       (mapcat (fn [x] [x (/ n x)]))
       distinct))

(defn presents1 [h] (* 10 (sum (factors h))))

(defn solve
  [tgt presents]
  (seek
    #(< tgt (presents %))
    (drop 700000 (range)))) ;; or (drop BEGIN (range))


(defn part1 [] (solve tgt presents1))

(defn presents2 [h] (* 11 (sum (filter #(<= (/ h %) 50) (factors h)))))

(defn part2 [] (solve tgt presents2))