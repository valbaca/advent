(ns advent-clj.year2016.day7
  (:require
    [advent-clj.elf :refer :all]
    [clojure.string :as s]))

;; TIL:
;; - One of my least favorite ones, do not like the abba/aba regex.
;; Too much of a gimmick IMO. Just about finding the right regex
;; These are ones where doing an imperative loop over all the chars would likely be faster.

(def test-input (ns-test-input))
(def a (first test-input))
(def input (ns-input))

(defn has-abba [s]
  (re-find #"([^\[\]])((?:(?!\1).))\2\1" s))

;; [outside inside] aka [supernet hypernet]
(defn bracketize [s]
  (let [parts (remove #(or (= [\[] %) (= [\]] %)) (partition-by #(or (= % \[) (= % \])) s))
        strs (map s/join parts)]
    (evens-odds strs)))

(defn supports-tls? [s]
  (let [[outside inside] (bracketize s)
        has-out-abba (seek has-abba outside)
        has-in-abba (seek has-abba inside)]
    (and has-out-abba (not has-in-abba))))

(defn part1 [input] (count (filter supports-tls? input)))

;; part 2

(def test-input2 (lines "input/year2016/day7-test2.txt"))
(def b (last test-input2))

(defn get-abas [s]
  (keep first (re-seq #"(.)(?:(?!\1).)\1" s)))

;; this is such a bad brute-force hack, but it works
(defn get-abas-iter [s]
  (distinct (mapcat get-abas (map s/join (iter-rest s)))))

(defn has-bab-solo [abas s] (seek #(in? s %) abas))

(defn has-bab [abas xs]
  (->> xs
       (map #(has-bab-solo abas %))
       seek))

(defn aba->bab [s] (str (second s) (first s) (second s)))

(defn supports-ssl? [s]
  (let [[outside inside] (bracketize s)
        abas (mapcat get-abas-iter outside)
        babs (map aba->bab abas)]
    (has-bab babs inside)))

(defn part2 [input] (count (keep supports-ssl? input)))