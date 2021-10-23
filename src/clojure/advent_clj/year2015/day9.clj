(ns advent-clj.year2015.day9
  (:require
   [advent-clj.elf :refer [in? lines split-ints sum prev-curr smallest
                           largest]]))

;; This one was a nice showcase of clojure and shows of a few of the helper fns
;; I've written and how they really help keep common patterns simple.
;; In particular:
;; lines - easy input
;; split-int - easy parsing
;; prev-cur - common pattern w/ reduce
;; in? - how is this not built-into Clojure? I get why, but it's so basic
;; smallest / largest - again, very basic. Sure, they're O(n) each time b/c I don't sort (which would give O(nlgn) once and would be O(1) afterward)

;; read in dists into a map {:from {:to dist} :to {:from dist}}
;; Throughout:
;; m = map (as in santa's map)
;; p = path
;; a = all locations

(defn parse [s]
  (let [[a _ b _ d] (split-ints s)] [a b d]))

(defn dist-map [input]
  (reduce
   (fn [m [a b d]] ;; setup a->b = d and b->a = d
     (assoc-in (assoc-in m [b a] d) [a b] d))
   {}
   (map parse input)))

(defn next-dests [p a]
  (remove #(in? p %) a))

(defn calc-travel [m p]
  (sum
   (map (fn [a->b] (get-in m a->b)) (prev-curr p)))) ;; get-in is nice!

(defn travel
  ([m] (travel m smallest)) ;; default
  ([m opt] (travel m [] (keys m) opt)) ;; refactor for p2
  ([m p a opt] ;; map path all optimization
   (if (= (count p) (count a))
     (calc-travel m p)
     (opt
      (for [dest (next-dests p a)]
        (travel m (conj p dest) a opt))))))

(def test-input (lines "input/year2015/day9-test.txt"))
(def test-santa-map (dist-map test-input))
(comment (travel test-santa-map))

(def input (lines "input/year2015/day9.txt"))
(def santa-map (dist-map input))
(comment (travel santa-map)) ;; Part 1

(defn longest-travel [m] (travel m largest))
(comment (longest-travel santa-map)) ;; Part 2