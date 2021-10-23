(ns advent-clj.year2015.day7
  (:require [advent-clj.elf :refer [separate ->int ns-test-input ns-input]]))

; TIL:
; - This one was a lot of fun and challenging
; - It really highlights the power of just using fns, lists, and maps
; - memorize was a lifesaver, and super easy to use
; - It was a challenge to debug nil, because it's "contagious": once something 
; returns nil, fn calls on it will keep returning nil (similar to Objective-C)
; - It's arguable that a REPL replaces a debugger...but would love to have both.

; At a high-level turn the lines to a map where the key is the wire name
; and the function takes in the map and returns the evaluation
; Takes in "x OR y -> e"
; turns into: { "e" (fn [m] (or (exec m "x")
;                               (exec m "y")))}

;; constantly elegantly handles when the key is an integer constant
(defn get-gate [m k] (get m k (constantly (->int k))))
;; there's no "add", just use assoc :)
;; executing is as easy as calling the fn with the gates map itself
(defn- exec-gate- [m k] ((get-gate m k) m))
(def exec-gate (memoize exec-gate-)) ;; memoize is a lifesaver and so easy!

;; c/o https://stackoverflow.com/a/41984792/158886
(defn ->ushort [x] (bit-and 0xFFFF x))

(defn gate-fn
  "Meta-function that takes in the function and args. Handles the innards of gates"
  ([f a] (fn [m] (->ushort (f (exec-gate m a)))))
  ([f a b] (fn [m] (->ushort (f (exec-gate m a) (exec-gate m b))))))

;; parse (as always) is a hot mess, but it keeps the rest elegant
(defn parse
  "Returns key and a fn, the fn takes 1 arg: the gates map"
  [s]
  (let [[a b c :as seps] (separate s) ;; https://clojure.org/guides/destructuring
        k (last seps)]
    [k
     (cond
       (= 3 (count seps)) (gate-fn identity a) ;; identity is handy sometimes!
       (= "NOT" a) (gate-fn bit-not b)
       (= b "LSHIFT") (gate-fn bit-shift-left a c)
       (= b "RSHIFT") (gate-fn unsigned-bit-shift-right a c)
       (= b "AND") (gate-fn bit-and a c)
       (= b "OR") (gate-fn bit-or a c)
       :else (throw (IllegalArgumentException. ^String s)))]))

(defn ->gates [lines]
  (reduce (fn [a [k f]] (assoc a k f)) {} (map parse lines)))

(def test-input (ns-test-input))
(def test-gates (->gates test-input))


(def input (ns-input))
(def part1-gates (->gates input))
(comment (exec-gate part1-gates "a"))

(def part2-gates
  (assoc part1-gates "b" (constantly 956)))
(comment (exec-gate part2-gates "a"))

;; Extra credit:
(defn exec-everything [gates]
  (map #(vector % (exec-gate gates %)) (keys gates)))

(comment (exec-everything test-gates))
(comment (exec-everything part1-gates))
(comment (exec-everything part2-gates))