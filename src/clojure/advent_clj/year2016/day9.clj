(ns advent-clj.year2016.day9
  (:require
    [advent-clj.elf :refer :all]
    [clojure.string :refer :all]))

;; TIL:
;; - This one mostly came down to getting the right regex
;; - The second part *had* an awful runtime, but honestly just let it run
;; - After adding memoization the runtime went from over 10 mins to around 100ms
;; and if run again, around 2 ms


(def test-input (ns-test-input))

; chars x times
(def r #"([^)]*)\((\d*)x(\d*)\)(.*)")

(defn match
  "Gives: [full prefix chars times rest]"
  [s]
  (re-matches r s))

(defn expand-str [[_ prefix chrs times rest]]
  (let [ichrs (->int chrs)
        a (subs rest 0 ichrs)
        b (subs rest ichrs)
        itimes (->int times)
        exp (join (repeat itimes a))]
    [(str prefix exp) b]))

(defn expand
  ([s] (expand expand-str "" s))
  ([expf s] (expand expf "" s))
  ([expf pre rem]
   (if-let [matches (match rem)]
     (let [[a b] (expf matches)]
       (recur expf (str pre a) b))
     (str pre rem))))

(def input (first (ns-input)))

(defn part1 [] (count (expand input)))

(def test-input2 (lines "input/year2016/day9-test2.txt"))

;; converting to counting chars only

(declare expand-str2)
(declare expand-count)
(defn expand-count-inner
  ([s] (expand-count expand-str2 0 s))
  ([expf s] (expand-count expf 0 s))
  ([expf sum rem]
   (if-let [matches (match rem)]
     (let [[added next-rem] (expf matches)]
       (recur expf (+ sum added) next-rem))
     (+ sum (count rem)))))

(def expand-count (memoize expand-count-inner))

(defn expand-str2 [[_ prefix chrs times rest]]
  (let [ichrs (->int chrs)
        a (subs rest 0 ichrs)
        b (subs rest ichrs)
        itimes (->int times)
        exp (join (repeat itimes a))
        sum (expand-count expand-str2 exp)]
    [(+ (count prefix) sum) b]))

(defn part2 [] (expand-count input))


;; Before memoization:
;(time (part2))
;"Elapsed time: 620933.955167 msecs"
;=> 10755693147

;; After memoization:
;(time (part2))
;"Elapsed time: 134.06575 msecs"
;=> 10755693147
;(time (part2))
;"Elapsed time: 1.414417 msecs"
;=> 10755693147