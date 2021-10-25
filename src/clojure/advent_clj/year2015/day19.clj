(ns advent-clj.year2015.day19
  (:require [advent-clj.elf :refer :all]
            [clojure.string :as str]))

;; TIL:
;; - OH this puzzle. This one is where I stopped with Go three years ago. I recently solved it in Python.
;; It's a bit frustrating (but also realistic) in that the only performant solution is greedy and random.
;; - after getting a solution, going back and cleaning up allowed me to really cleanup

;; - turning complex anon functions into clean, named functions is really great
;; - really nice that str/index-of returns nil on unfound, which allows for easy `if` use and `if-let` bindings

;; s = string molecule; xfs = transformations

;; read in the input as a list of pairs (rather than a multi-map which is important for part 2!)
(defn parse-line [line] (let [[a _ b] (separate line)] [a b]))
(defn parse [input] (vec (map parse-line input)))

(def test-input (parse (ns-test-input)))
(def test-mol "HOH")

(def input (parse (ns-input)))
(def mol "CRnCaSiRnBSiRnFArTiBPTiTiBFArPBCaSiThSiRnTiBPBPMgArCaSiRnTiMgArCaSiThCaSiRnFArRnSiRnFArTiTiBFArCaCaSiRnSiThCaCaSiRnMgArFYSiRnFYCaFArSiThCaSiThPBPTiMgArCaPRnSiAlArPBCaCaSiRnFYSiThCaRnFArArCaCaSiRnPBSiRnFArMgYCaCaCaCaSiThCaCaSiAlArCaCaSiRnPBSiAlArBCaCaCaCaSiThCaPBSiThPBPBCaSiRnFYFArSiThCaSiRnFArBCaCaSiRnFYFArSiThCaPBSiThCaSiRnPMgArRnFArPTiBCaPRnFArCaCaCaCaSiRnCaCaSiRnFYFArFArBCaSiThFArThSiThSiRnTiRnPMgArFArCaSiThCaPBCaSiRnBFArCaCaPRnCaCaPMgArSiRnFYFArCaSiThRnPBPMgAr")
(def e "e")

;; given a single a->b and molecule, returns list of all the unique molecules after a single replacement step
(defn single-replace
  ([a b s] (single-replace a b s 0 []))
  ([a b s idx ret]
   (if-let [found (str/index-of s a idx)]
     (let [pre-match (subs s 0 found)
           post-match (subs s (+ found (count a)))
           rpl (str pre-match b post-match)]
       (recur a b s (inc found) (conj ret rpl)))
     ret)))

;; for each xf, generate all possible molecules
(defn replacements [xfs s] (mapcat (fn [[a b]] (single-replace a b s)) xfs))

(defn part1
  ([] (part1 input mol))
  ([xfs s] (count (set (replacements xfs s)))))

;; Part 2 was VERY HARD. Any "smart" solution goes exponential. Literally comes down to attempting permutations of xfs

(def test-input2 (parse (lines "input/year2015/day19-test2.txt")))

;; This was attempt #4 at part 2...literally a random-greedy solution, but it solves instantly!

(defn a->b
  "Replaces all occurrences of `a` with `b` in `s`. Return result and n + # of replacements made."
  [[s n] [a b]]
  (if (str/index-of s a)
    (recur [(str/replace-first s a b) (inc n)] [a b])
    [s n]))

(defn reduce-xfs [xfs s] (reduce a->b [s 0] xfs))

(defn recur-greedy
  "Repeatedly applies xfs (in given order) to s until it's 'stable' (no more replacements are possible).
  Returns the stable molecule and how many xfs were applied"
  ([xfs s] (recur-greedy xfs s 0))
  ([xfs s n]
   (let [[xf-str xf-n] (reduce-xfs xfs s)]
     (if (= xf-str s)
       [s n]
       (recur xfs xf-str (+ n xf-n))))))

(defn random-greedy-xfs
  [xfs tgt mol]
  (let [infinite-greed (repeatedly #(recur-greedy (shuffle xfs) mol)) ;; couldn't help using this name
        solns (filter #(= tgt (first %)) infinite-greed)]
    (first solns)))

(def rev-test-input2 (map reverse test-input2))
(def rev-input (map reverse input))
(defn part2 [] (second (random-greedy-xfs rev-input e mol)))