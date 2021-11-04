(ns advent-clj.twod
  (:require [clojure.string :as s]))

;; help when working with vectors of vectors
;; calling them grids
;; row col is the best way to deal with them
;; row = y = first index
;; col = x = second index

;; Return nil instead of throwing out of bounds

(defn ggrid "get-grid"
  ([g rc] (when rc (apply ggrid g rc)))
  ([g r c]
   (let [row (nth g r nil)
         elem (nth row c nil)]
     elem)))

(def dirs
  {:up    (fn [r c] [(dec r) c])
   :down  (fn [r c] [(inc r) c])
   :left  (fn [r c] [r (dec c)])
   :right (fn [r c] [r (inc c)])})

(defn move [dir grid r c]
  (let [new-rc ((dirs dir) r c)]
    (when (ggrid grid new-rc)
      new-rc)))

(def up (partial move :up))
(defn get-up [grid r c] (ggrid grid (up grid r c)))
(def down (partial move :down))
(defn get-down [grid r c] (ggrid grid (down grid r c)))
(def left (partial move :left))
(defn get-left [grid r c] (ggrid grid (left grid r c)))
(def right (partial move :right))
(defn get-right [grid r c] (ggrid grid (right grid r c)))

(defn get-xy [grid x y] (ggrid grid y x))

(def test-grid
  (for [x (range 3)]
    (range (* 10 x) (+ 3 (* 10 x)))))


(defn strs->grid [strs] (mapv vec strs))

(defn s->grid [s] (-> s s/split-lines strs->grid))



(defn transpose [g] (apply mapv vector g))