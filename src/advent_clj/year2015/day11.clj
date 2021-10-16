(ns advent-clj.year2015.day11
  (:require
   [advent-clj.elf :refer [prev-curr iter-rest seek]]))

;; TIL:
;; - This one was honestly a pain to do in clojure
;; - Remember to use `seq` to "cast" empty lists to nil, which is falsy
;; - (some p xs) ~= (first (filter p xs)) IFF p returns the input on success
;; - The pairs finding AND carrying logic was troublesome

(defn inc-ch [c] (char (inc (int c)))) ;; no wrap nor carry

(defn inc-char [c] ;; wraps and carrys
  (if (= c \z)
    [\a true]
    [(inc-ch c) false]))

(defn inc-str
  ([s] (inc-str (reverse s) '() true))
  ([s z carry]
   (if (or (not carry) (empty? s))
     (apply str (concat (when carry [\a]) (reverse s) z)) ;; done!
     (let [[c & rest] s
           [c++ carry++] (inc-char c)]
       (recur rest (conj z c++) carry++))))) ;; regular increment

(iterate inc-str "aaa")

(defn is-straight? [[a b c]] (= (inc-ch (inc-ch a)) (inc-ch b) c))
(defn has-straight? [s] (first (filter is-straight? (partition 3 1 (seq s)))))

(defn all-valid-chars? [s] (not (seq (filter #{\i \o \l} s))))

(defn get-pairs [xs] (filter (fn [[a b]] (= a b)) (prev-curr xs)))

;; This one looks even worse than day 5's pair-of-two, ugh
(defn has-pair-of-two?
  [s]
  (seq
   (filter
    (fn [[a b & rest]]
      (and
       (<= 2 (count rest))
       (= a b)
       (seq (remove #(= a (first %)) (get-pairs rest)))))
    (iter-rest (seq s)))))

(defn valid? [s]
  ((every-pred all-valid-chars? has-straight? has-pair-of-two?) s))

(defn next-valid [s] (seek valid? inc-str (inc-str s)))

(def input "vzbxkghb")
(defn part1 [] (next-valid input))
(defn part2 [] (next-valid (part1)))