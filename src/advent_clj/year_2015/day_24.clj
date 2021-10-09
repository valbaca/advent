(ns advent-clj.year-2015.day-24
  (:require
   [advent-clj.elf :refer [sum product min-of lines->ints]]
   [clojure.math.combinatorics :as c]))

; TIL:
; - Coming back to Clojure definitely takes some ramp-up all over
; - But each time gets a little easier
; Parts that are still hard:
; - no variables: still just want to write to a variable
; - namespaces & requires are always a PITA
; - REPL mechanics. Calva is a lifesaver here
; - 

(defn- viable-combs [ints i target]
  (->> (c/combinations ints i)
       (filter #(= target (sum %)))))

(defn- combs
  [ints i target minPkgs entg]
  (->> (viable-combs ints i target)
       (map #(vector (count %) (product %))) ; to [pkg entanglement]
       (#(conj % [minPkgs entg]))
       min-of))

(defn- solve [ints slots]
  (let [target (/ (reduce + ints) slots)
        max-n (- (count ints) 2)]
    (loop [i 1
           minPkgs Integer/MAX_VALUE
           entg 0]
      (if (or (= i max-n)
              (<= minPkgs i))
        {:minPkgs minPkgs :entg entg}
        (let [[nextMinPkgs nextEntanglement] (combs ints i target minPkgs entg)]
          (recur (inc i) nextMinPkgs nextEntanglement))))))

(defn part1-test []
  (let [input (lines->ints "input/year_2015/day_24_test.txt")]
     (println "Part 1 Test")
     (time (solve input 3))))

(defn part2-test []
  (let [input (lines->ints "input/year_2015/day_24_test.txt")]
     (println "Part 2 test")
     (time (solve input 4))))

(defn main []
  (println "Day 24")
  [(part1-test)
   (let [input (lines->ints "input/year_2015/day_24.txt")]
     (println "Part 1")
     (time (solve input 3)))
   (part2-test)
   (let [input (lines->ints "input/year_2015/day_24.txt")]
     (println "Part 2")
     (time (solve input 4)))])

(comment (main))
         ;; => [{:minPkgs 2, :entg 99} {:minPkgs 6, :entg 11846773891} {:minPkgs 4, :entg 80393059} {:minPkgs 2, :entg 44}]


