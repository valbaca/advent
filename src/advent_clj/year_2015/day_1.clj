(ns advent-clj.year-2015.day-1
  (:require [advent-clj.elf :refer :all]))

; TIL: make sure the test load-on-save is successful or it uses the last successful eval of tests, which makes your test run useless
; Once the tests are loaded in the REPL successfully, then you can edit the src and re-run tests over and over with Ctrl+Alt+C T 
; Had a bit of a problem with the -> threading macro and .indexOf, see below
; Remember, hmaps are great...for "mapping", replaced if-then with just a hmap
; reductions is like reduce with time-travel: gives the result of all the intermediate reductions

(def floor {\( 1 \) -1}) ; ( goes up, ) goes down

(defn part1 [s]
  (->> s
       seq
       (map floor)
       sum))

(defn part2 [s]
  (->> s
       seq
       (map floor)
       (reductions +)
       (#(.indexOf % -1)) ;; This gave trouble, I think b/c of method not being a fn
       inc)) ;; pos is 1-indexed

(defn main []
  (let [in (slurp "input/year_2015/day_01.txt")]
    [(part1 in)
     (part2 in)]))

(comment (main))