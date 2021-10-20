(ns advent-clj.year2015.day2-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-clj.year2015.day2 :refer [parse wrapping]]))

(deftest a-test
  (testing "parsing"
    (is (= [2 3 4] (parse "2x3x4")))))

(deftest b-test
  (testing "slack"
    (is (= 58 (wrapping [2 3 4])))))