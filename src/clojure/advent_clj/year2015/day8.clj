(ns advent-clj.year2015.day8
  (:require
   [advent-clj.elf :refer [lines sum]]
   [clojure.string :refer [replace]]))

;; TIL
;; - This one was painful, despite how short the actual result ended up being
;; - \\ == \ for java regexes, I forgot this and spent way too long fighting with slashes
;; - Really wish Clojure had a `raw` string format
;; - Looking online, JS solutions are trivial b/c eval just works
;; - This one was also pretty dumb b/c the order of replacements for Part 1 mattered. My original result was over by 2. no idea why.
;; - Part 2 was super easy though b/c you can just count slashes and quotes plus 2

(def test-input (lines "input/year2015/day8_test.txt"))

(defn decode [s]
  (-> s
      (subs 1 (- (count s) 1))
      (replace #"\\\\" "@")
      (replace #"\\\"" "@")
      (replace #"(\\x[0-9a-f]{2})" "@")))

(defn decode-diff [s]
  (- (count s) (count (decode s))))

(sum (map decode-diff test-input))

(def input (lines "input/year2015/day8.txt"))

(sum (map decode-diff input)) ;; Part 1

(defn encode-diff [s]
  (+ 2 (count (filter #{\\ \"} (seq s)))))

(sum (map encode-diff input)) ;; Part 2