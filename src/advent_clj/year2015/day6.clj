(ns advent-clj.year2015.day6
  (:require [clojure.string :refer []]
            [advent-clj.elf :refer [in? lines pick-ints sum]]))

; TIL
; - Another quite involved one...and runs very slow, >10s
; Several challenges:
; - 2d looping. do-bits is still a mess. Options include:
;     - generating a list of x-y pairs and just single-looping through those?
;     - switching to something like core.matrix or raw java 2d int arrays
; - Getting the parse/op fns setup right. Definitely a case of don't premature optimize.
; Until you know which arg is going to vary, hard to guess what meta-fns you'll need
; - I'm proud of the parse/op pair that I ended up with.
; The logic is simple by using in? checks and by passing what to do to `op`
; - assoc-in and update-in are great!
; - also proud of `pick-ints`, just a simple way to get what I need

(defn create-2d [n] (vec (repeat n (vec (repeat n 0)))))

(def init-arr (create-2d 1000))

(defn count-2d [xxs] (sum (map sum xxs)))

(defn xy-range [xa xb ya yb]
  (for [x (range xa (inc xb))
        y (range ya (inc yb))]
    [x y]))

;; do-bits is a mess and at the heart of everything :/
(defn do-bits [f xxs xa xb ya yb]
  (let [xyrange (xy-range xa xb ya yb)]
    (reduce (fn [acc [x y]] (f acc x y)) xxs xyrange)))

(defn set-bit [xxs x y] (assoc-in xxs [y x] 1))
(defn unset-bit [xxs x y] (assoc-in xxs [y x] 0))
(defn flip-bit [xxs x y] (update-in xxs [y x] #(if (zero? %) 1 0)))

(defn op
  "Given an update function and the x-y inputs, returns a fn ready to take in a xxs and update it"
  ([update-fn s]
   (let
    [[xa ya xb yb] (pick-ints s)]
     (op update-fn xa xb ya yb)))
  ([update-fn xa xb ya yb]
   #(do-bits update-fn % xa xb ya yb)))

(defn parse [s]
  (cond
    (in? s "turn on") (op set-bit s)
    (in? s "turn off") (op unset-bit s)
    (in? s "toggle") (op flip-bit s)
    :else (throw (Exception. "Couldn't parse"))))

(def input (lines "input/year2015/day6.txt"))

(comment (time (->> input
                    (map parse)
              ;; seq of parsed instruction fns
                    (reduce (fn [a c] (c a)) init-arr)
                    count-2d)))

;; Part 2
(defn inc-2d [xxs x y] (update-in xxs [y x] inc))
(defn dec-2d [xxs x y] (update-in xxs [y x] #(if (zero? %) % (dec %))))
(defn inc-twice-2d [xxs x y] (update-in xxs [y x] #(+ % 2)))

(defn parse2 [s]
  (cond
    (in? s "turn on") (op inc-2d s)
    (in? s "turn off") (op dec-2d s)
    (in? s "toggle") (op inc-twice-2d s)
    :else (throw (Exception. "Couldn't parse"))))

(comment (time (->> input
                    (map parse2)
              ;; seq of parsed instruction fns
                    (reduce (fn [a c] (c a)) init-arr)
                    count-2d)))