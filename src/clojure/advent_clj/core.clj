(ns advent-clj.core
  (:gen-class)
  (:import (com.valbaca.advent Howdy)))

(defn -main
  "Advent of Clojure!"
  [& args]
  (println (str "λ🎄.(λ🎅.🎄(🎅🎅))(λ🎅.🎄(🎅🎅))" args))
  (println "See the individual days for solutions and runners")
  (println (Howdy/say)))
