(ns advent-clj.year2015.day4-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-clj.year2015.day4 :refer [part1]]))

(deftest a-test
  (testing "testing"
    (is (= 609043 (part1 "abcdef")))))
