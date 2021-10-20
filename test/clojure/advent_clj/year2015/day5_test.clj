(ns advent-clj.year2015.day5-test
  (:require 
   [clojure.test :refer [deftest testing is]]
   [advent-clj.year2015.day5 :refer [nice? nice2?]]))

(deftest a-test
  (testing "nice"
    (is (nice? "ugknbfddgicrmopn"))
    (is (nice? "aaa"))
    (is (not (nice? "jchzalrnumimnmhp")))
    (is (not (nice? "haegwjzuvuyypxyu")))
    (is (not (nice? "dvszwmarrgswjxmb")))))

(deftest b-test
  (testing "nice"
    (is (nice2? "qjhvhtzxzqqjkmpb"))
    (is (nice2? "xxyxx"))
    (is (not (nice2? "uurcxstgmygtbstg")))
    (is (not (nice2? "ieodomkazucvgmuy")))))