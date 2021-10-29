(ns advent-clj.elf
  (:require [clojure.string :as s]))

;; INPUT/FILE/LINE fns

(declare ns-inner-input)
(defn ns-input
  "Gets the input as seq of strings (lines) based on the current advent namespace.
  Expects corresponding file to be: input/yearYYYY/dayDD.txt
  (No leading zeros in DD)"
  []
  (ns-inner-input :not-test))

(defn ns-test-input
  "Gets the TEST input as seq of strings (lines) based on the current advent namespace.
  Expects file to be: input/yearYYYY/dayDD-test.txt
  (No leading zeros in DD)"
  []
  (ns-inner-input :test))

(defn lines
  "Gets the trimmed lines of a file as a seq"
  [filename]
  (->> filename
       slurp
       s/split-lines
       (map s/trim)))

(declare separate)
(defn- ns-inner-input
  [test?]
  (let [[_ year day] (separate (str *ns*))
        day-with-suffix (str (if (= :test test?) (str day "-test") day) ".txt")
        filename (s/join "/" ["input" year day-with-suffix])]
    (lines filename)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; PARSING fns

(defn separate
  "Split on whitespace, periods & commas"
  [s]
  (s/split s #"[\s,.]"))

(defn ->int
  "convert string to int, nil if error."
  [s]
  (try
    (Integer/parseInt s)                                    ; if you *want* an exception, use parseInt
    (catch NumberFormatException _ nil)))

(defn lines->ints [filename] (map ->int (lines filename)))

(defn str<->int
  "Safely attempts to convert string to int. If cannot, gives back original string"
  [s]
  (if-let [i (->int s)] i s))

(defn split-ints
  "Separate string and safely parses ints.
   'foo 123,234 bar' => ('foo' 123 234 'bar')"
  [s]
  (map str<->int (separate s)))

(defn pick-ints
  "Returns a seq of only the ints in s after separate"
  [s]
  (remove nil? (map ->int (separate s))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; MATH/NUMBERish related functions

(defn smallest
  "Returns the smallest element (first wins ties) in O(n) time"
  ([xs] (smallest compare xs))
  ([comp xs] (reduce #(if (<= (comp %1 %2) 0) %1 %2) xs)))

(defn largest
  "Returns the largest element (first wins ties) in O(n) time"
  ([xs] (largest compare xs))
  ([comp xs] (reduce #(if (>= (comp %1 %2) 0) %1 %2) xs)))

(defn sum [xs] (reduce + 0 xs))

(defn product [xs] (reduce * xs))

(defn clamp
  "Ensures a number is at least `lowest` (default 0) and
  at most `highest` (default Infinite)"
  ([v] (clamp v 0))
  ([v lowest] (if (< v lowest) lowest v))
  ([v lowest highest]
   {:pre [(< lowest highest)]}
   (cond
     (< v lowest) lowest
     (< highest v) highest
     :else v)))

(defn clamp-down
  "Ensures a number is at most `highest`"
  [v highest]
  (if (< v highest) v highest))

(defn minus "Simply #(- % x), use for update/update-in" [x] (fn [y] (- y x)))
(defn plus "Simply #(+ % x), use for update/update-in" [x] (fn [y] (+ y x)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; SEQ/COLLECTION related functions

(defn every-other
  "Returns a pair of streams, first contains every even item, second contains every odd.
  (every-other [:a :b :c :d]) => ((:a :c) (:b :d))"
  [xs]
  (apply map list (partition 2 xs)))

(defn prev-curr
  "Returns a sequence of pairs of each element with their prev.
   (prev-curr [:a :b :c :d]) ; => ((:a :b) (:b :c) (:c :d))"
  [xs]
  (partition 2 1 xs))

(defn in?
  "Predicate of whether x is in xs.
  For vecs & maps, it's whether x is a value. (Use contains? for keys)."
  [xs x]
  (cond
    (string? xs) (not (nil? (s/index-of xs x)))
    (or (vector? xs) (list? xs)) (<= 0 (.indexOf xs x))
    (set? xs) (contains? xs x)
    ; my decision to not replicate `contains?` for keys
    (map? xs) (in? (vals xs) x)
    ; if given an infinite seq, this will obviously break
    (seq? xs) (in? (vec xs) x)
    :else (throw (IllegalArgumentException. (str "Invalid xs: " xs)))))

(defn iter-rest
  "Given a seq, returns seq of rest called iteratively.
   (iter-rest [:a :b :c]) => ([:a :b :c] [:b :c] [:c])"
  [xs]
  (take (count xs) (iterate rest xs)))

(defn any
  "Returns true if xs has any non-falsy value."
  [xs]
  (boolean (first (filter identity xs))))

(defn seek
  "Returns the first of xs that satisfies the predicate (default: identity).
   [pred f x] uses (iterate f x) for xs."
  ([xs] (first (filter identity xs)))
  ([pred xs] (first (filter pred xs)))
  ([pred f x] (first (filter pred (iterate f x)))))

;; c/o https://clojuredocs.org/clojure.core/bit-test#example-5d401face4b0ca44402ef78b
(defn powerset
  "Returns all the ways you could select items from the collection.
  Each element is considered distinct. If coll has duplicates, output has duplicates.
  Counts 0 to 2^count and uses the bits to select elements.

  (powerset [1 2 3]) =>
  (() (1) (2) (1 2) (3) (1 3) (2 3) (1 2 3))"
  [coll]
  (let [cnt (count coll)
        bits (Math/pow 2 cnt)]
    (for [i (range bits)]
      (for [j (range i)
            :while (< j cnt)
            :when (bit-test i j)]
        (nth coll j)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; MACROS

;; c/o https://stackoverflow.com/a/11705723/158886
(defmacro kw-map
  "Creates a keyword map, a map with keyword keys from the symbols and values are the evaluated symbol.
  (let [x 1 y 2 z 3] (kw-map x y z)) => {:x 1 :y 2 :z 3}"
  [& syms]
  (zipmap (map keyword syms) syms))