(ns advent-clj.year2015.day24
  (:require
   [advent-clj.elf :refer :all]
   [clojure.math.combinatorics :as c]))

; TIL:
; - Coming back to Clojure definitely takes some ramp-up all over
; - But each time gets a little easier
; Parts that are still hard:
; - no variables: still just want to write to a variable
; - namespaces & requires are always a PITA
; - REPL mechanics. Calva is a lifesaver here

(defn- viable-combs [ints i target]
  (->> (c/combinations ints i)
       (filter #(= target (sum %)))))

(defn- combs
  [ints i target minPkgs entg]
  (->> (viable-combs ints i target)
       (map #(vector (count %) (product %))) ; to [pkg entanglement]
       (#(conj % [minPkgs entg]))
       smallest))

(defn- solve [ints slots]
  (let [target (/ (sum ints) slots)
        max-n (- (count ints) 2)]
    (loop [i 1
           minPkgs Integer/MAX_VALUE
           entg 0]
      (if (or (= i max-n)
              (<= minPkgs i))
        {:minPkgs minPkgs :entg entg}
        (let [[nextMinPkgs nextEntanglement] (combs ints i target minPkgs entg)]
          (recur (inc i) nextMinPkgs nextEntanglement))))))

(def test-input (map ->int (ns-test-input)))


(defn part1-test []
  (println "Part 1 Test")
  (time (solve test-input 3)))

(defn part2-test []
  (println "Part 2 test")
  (time (solve test-input 4)))


(def input (map ->int (ns-input)))

(defn day24main []
  (println "Day 24")
  [(part1-test)
   (do
     (println "Part 1")
     (time (solve input 3)))
   (part2-test)
   (do
     (println "Part 2")
     (time (solve input 4)))])

(comment (day24main))
;; => [{:minPkgs 2, :entg 99} {:minPkgs 6, :entg 11846773891} {:minPkgs 4, :entg 80393059} {:minPkgs 2, :entg 44}]


