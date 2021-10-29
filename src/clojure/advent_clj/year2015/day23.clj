(ns advent-clj.year2015.day23
  (:require [advent-clj.elf :refer :all]))

;; TIL:
;; - more destructuring, this time using :as
;; - Finally feeling very familiar and comfortable with Clojure!
;; Pretty much go this one working on the first attempt (again, recently solved these in Python)
;; This one especially felt very natural in clojure b/c executing the operands can be done in one line,
;; but could also very easily be made generic. But with how terse clojure can be, there's not much point.

(def input (ns-input))

(def init-state {:input input :curr-line 0 :registers {"a" 0 "b" 0}})

(defn jump [state offset] (update state :curr-line #(+ % offset)))
(defn next-line [state] (jump state 1))

(defn exec [{:keys [input curr-line registers] :as state}]
  ;(println state) ;; DEBUG
  (if-not (within? (count input) curr-line)
    state ;; exit when out of bounds
    (let [[op arg & args] (sep (get input curr-line))]
      (case op
        "hlf" (-> state (update-in [:registers arg] #(/ % 2)) next-line recur)
        "tpl" (-> state (update-in [:registers arg] #(* % 3)) next-line recur)
        "inc" (-> state (update-in [:registers arg] inc) next-line recur)
        "jmp" (-> state (jump arg) recur)
        "jie" (-> (if (even? (get registers arg)) (jump state (first args)) (next-line state)) recur)
        "jio" (-> (if (= 1 (get registers arg)) (jump state (first args)) (next-line state)) recur)))))

(def test-input (vec (ns-test-input)))
(def test-init-state (assoc init-state :input test-input))
(defn test-part1 [] (exec test-init-state))

(defn part1 [] (get-in (exec init-state) [:registers "b"]))

(defn part2 [] (get-in (exec (assoc-in init-state [:registers "a"] 1)) [:registers "b"]))