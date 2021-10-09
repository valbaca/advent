(ns advent-clj.core
  (:gen-class)
  (:require [advent-clj.year-2015.day-24 :as y15d24]))

(defn -main
  "Advent of Clojure!"
  [& args]
  (println "λ🎄.(λ🎅.🎄(🎅🎅))(λ🎅.🎄(🎅🎅))")
  (println (y15d24/main)))

(comment (-main)) ; main run automatically with $ lein run

