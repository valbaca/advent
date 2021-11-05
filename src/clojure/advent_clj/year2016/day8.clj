(ns advent-clj.year2016.day8
  (:require
    [advent-clj.elf :refer :all]
    [clojure.string :as s]
    [advent-clj.twod :refer :all]))

;; TIL:
;; - Strong deja vu of doing this in clojure before
;; - Parsing the lines and generating a single composed function is magical
;; and I mean that both positively and negatively, debugging it was a pain
;; but I was also making dumb mistakes like not counting the index right
;; This is one of the more impressive and memorable puzzles

(def test-input (ns-test-input))
(def input (ns-input))

;; tall == row == y
;; wide == col == x
;; stick to [row col] or [tall wide] or [y x]
;; avoid the opposite (like [x y]), even though it feels backwards
(defn make-screen [tall wide] ;; row col
  (let [row (apply vector-of :boolean (repeat wide false))]
    (vec (repeat tall row))))

(defn- turn-on [wide]
  (fn [screen row]
    (reduce
      (fn [s c] (assoc-in s [row c] true))
      screen
      (range wide))))

(defn rect [tall wide screen]
  (reduce
    (turn-on wide)
    screen
    (range tall)))

(defn- rotate-single-row [row rot]
  (let [len (count row)
        start (- len (mod rot len))
        end (+ start len)
        double-row (vec (concat row row))]
    (subvec double-row start end)))

(defn rotate-row [row rot screen]
  (assoc screen row (rotate-single-row (nth screen row) rot)))

(defn rotate-col [col rot screen]
  (transpose (rotate-row col rot (transpose screen)))) ;; FANCY

(defn count-on [screen]
  (sum (map (fn [row] (count (filter identity row))) screen)))

(defn print-screen [screen]
  (doall
    (->> screen
         (map #(map {true \# false \.} %))
         (map #(println (s/join %)))))
  (println)
  screen) ;; prefer to return screen for chaining

;; for non-chaining
(defn print-screen! [screen] (dorun (print-screen screen)))

(defn- split-line [line]
  (remove #(= "" %) (map str<->int (s/split line #"[\s=x]"))))

(defn parse [line]
  (let [s (split-line line)
        [op a b & _] s]
    (cond
      (= op "rect") #(rect b a %)
      (and (= op "rotate") (= a "column")) #(rotate-col b (nth s 4) %)
      (and (= op "rotate") (= a "row")) #(rotate-row (nth s 3) (nth s 5) %))))

(defn input->fn [input] (apply comp (reverse (map parse input))))

;; Testing area

(def test-init (make-screen 3 7))

(->> test-init
     ;print-screen
     (rect 2 3)
     print-screen
     (rotate-col 1 1)
     print-screen
     (rotate-row 0 4)
     print-screen
     (rotate-col 1 1)
     print-screen!)

(print-screen! ((apply comp (reverse (map parse test-input))) test-init))

(print-screen! ((input->fn test-input) test-init))

(count-on ((input->fn test-input) test-init))

;; Part 1
(def init (make-screen 6 50))
(def input-fn (input->fn input))
(count-on (input-fn init))

;; part 2
(print-screen! (input-fn init)) ;; beautiful