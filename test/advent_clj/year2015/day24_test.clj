(ns advent-clj.year2015.day24-test
  (:require [clojure.test :refer :all]
            [advent-clj.year2015.day24 :refer :all]))

(deftest sanity-test
  (testing "A=A?" (is (= 1 1))))

(deftest a-test
  (testing ""
    (is (= {:minPkgs 2 :entg 99} (part1-test)))
    (is (= {:minPkgs 2 :entg 44} (part2-test)))))