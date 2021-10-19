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

(defn inc-str ;; puts all strings in stack `s`, pops onto `z` and keeps carry
  ([s] (inc-str (reverse s) '() true))
  ([s z c] ;; s=string stack, z=result list, c=carry
   (if (or (not c) (empty? s))
     (apply str (when c \a) (concat (reverse s) z)) ;; done!
     (let [[ch & rest] s
           [ch++ c++] (inc-char ch)]
       (recur rest (conj z ch++) c++))))) ;; regular increment

(iterate inc-str "aaa")

(defn is-straight? [[a b c]] (= (inc-ch (inc-ch a)) (inc-ch b) c))
(defn has-straight? [s] (first (filter is-straight? (partition 3 1 (seq s)))))

(defn all-valid-chars? [s] (not (seq (filter #{\i \o \l} s))))

(defn pair-two? [s] (re-find #"(.)\1.*(?!\1)(.)\2" s))
;;                             pair1  diff  pair2
;; diff is a negative-lookahead of first char to ensure different pairs

(defn valid? [s] ((every-pred all-valid-chars? has-straight? pair-two?) s))

(defn next-valid [s] (seek valid? inc-str (inc-str s)))

(def input "vzbxkghb")
(defn part1 [] (next-valid input))
(defn part2 [] (next-valid (part1)))