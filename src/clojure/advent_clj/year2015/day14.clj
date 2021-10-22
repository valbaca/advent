(ns advent-clj.year2015.day14
  (:require [advent-clj.elf :refer :all]))

(def comet {:distance            0
            :speed               14
            :stamina             10
            :full-stamina        10
            :rest-remaining      127
            :full-rest-remaining 127})

(def dancer {:distance            0
             :speed               16
             :stamina             11
             :full-stamina        11
             :rest-remaining      162
             :full-rest-remaining 162})


(defn step [{:keys [distance speed stamina rest-remaining full-stamina full-rest-remaining] :as rd}]
  (cond
    ;; run
    (pos? stamina)
    (assoc rd :distance (+ distance speed)
              :stamina (dec stamina))
    ;; rest
    (pos? rest-remaining)
    (assoc rd :rest-remaining (dec rest-remaining))
    ;; reset and step again
    :else
    (recur (assoc rd
             :stamina full-stamina
             :rest-remaining full-rest-remaining))))



(defn solve [n xs]
  (largest (map :distance (map #(nth (iterate step %) n) xs))))
(solve 1000 [comet dancer])

(def line "Rudolph can fly 22 km/s for 8 seconds, but then must rest for 165 seconds.")

(defn parse [line]
  (let [splits (split-ints line)
        [name _ _ speed _ _ stamina _ _ _ _ _ _ _ full-rest-remaining] splits]
    {:points              0
     :distance            0
     :name                name
     :speed               speed
     :stamina             stamina
     :full-stamina        stamina
     :rest-remaining      full-rest-remaining
     :full-rest-remaining full-rest-remaining}))

(def input (map parse (lines "input/year2015/day14.txt")))

(solve 2503 input)

(defn award [r] (update-in r [:points] inc))

(defn step-award [xs]
  (let [xs-step (map step xs)
        lead-dist (largest (map :distance xs-step))]
    (map
      (fn [r]
        (if (= lead-dist (:distance r))
          (award r)
          r))
      xs-step)))

(largest (map :points (nth (iterate step-award input) 2503)))