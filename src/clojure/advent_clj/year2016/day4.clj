(ns advent-clj.year2016.day4
  (:require
    [advent-clj.elf :refer :all]
    [clojure.string :as s]))

;; TIL:
;; - Pretty complicated one, but working in parts was great
;; - First time using split-at, was handy to make easy list-decomposition
;; - Used the "negative/golf score" trick again: Rather than dealing with complex sorters, just negate the score.
;; Then you can sort "normally" (lowest-to-highest)

(def test-line "aaaaa-bbb-z-y-x-123[abxyz]")

(defn parse
  "line => [name id checksum]"
  [line]
  (let [seps (s/split line #"[\[\]-]")
        [name-parts [id-str checksum]] (split-at (- (count seps) 2) seps)
        name (s/join name-parts)
        id (->int id-str)]
    [name id checksum]))

(defn valid-checksum
  "Generates the actual valid checksum for `name`"
  [name]
  (->> name sorted-by-freq (take 5) s/join))

(defn valid-checksum? [name checksum]
  (= checksum (valid-checksum name)))

(defn sector-id [[name id checksum]]
  (if (valid-checksum? name checksum) id 0))

(defn part1 [input]
  (->> input
       (map parse)
       (map sector-id)
       sum))

(def test-input (ns-test-input))

(def input (ns-input))

;; part 2

(defn decrypt-letter [letter id]
  (if (= letter \-)
    \space
    (let [a (int \a)
          pos (- (int letter) a)
          shift-pos (mod (+ pos id) 26)
          shift-ascii (+ a shift-pos)]
      (char shift-ascii))))

(defn decrypt-name [name id]
  [(s/join (map #(decrypt-letter % id) (seq name)))
   id])

(defn part2 [input]
  (->> input
       (map parse)
       (filter #(valid-checksum? (first %) (last %)))
       (map #(decrypt-name (first %) (second %)))
       (seek #(in? (first %) "pole"))))
