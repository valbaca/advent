(ns advent-clj.year2015.day3-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-clj.year2015.day3 :refer [part1]]))

(deftest day3
  (testing "travel"
    (is (= 2 (part1 ">")))
    (is (= 4 (part1 "^>v<")))
    (is (= 2 (part1 "^v^v^v^v^v")))))