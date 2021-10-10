(ns advent-clj.year-2015.day-1-test
  (:require [clojure.test :refer :all]
            [advent-clj.year-2015.day-1 :refer [part1 part2]]))

(deftest part1test
  (testing "1"
    (is (= 0 (part1 "()()")))
    (is (= 0 (part1 "(())")))
    (is (= 3 (part1 "(((")))
    (is (= 3 (part1 "))(((((")))
    (is (= -1 (part1 "())"))) 
    (is (= -1 (part1 "))(")))
    (is (= -3 (part1 ")))")))
    (is (= -3 (part1 ")())())")))))
(deftest part2test
  (testing "2"
    (is (= 1 (part2 ")")))
    (is (= 5 (part2 "()())")))))