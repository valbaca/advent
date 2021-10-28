(ns advent-clj.year2015.day21
  (:require [advent-clj.elf :refer :all]
            [clojure.math.combinatorics :as c]))

(def input (ns-input))
(defn split-line [line]
  (remove #(= % "") (split-ints line)))

(defn parse-line [type line]
  (let [[name cost dmg armor] (split-line line)]
    (create-map type name cost dmg armor)))

(defn parse-lines [lines]
  (let [vlines (vec lines)
        map-parse-line (fn [type lines] (mapv #(parse-line type %) lines))
        weapons (map-parse-line :weapon (subvec vlines 1 6))
        armor (map-parse-line :armor (subvec vlines 8 13))
        rings (map-parse-line :ring (subvec vlines 15))]
    (create-map weapons armor rings)))

(def items (parse-lines input))

(defn- make-ring-sets [rings]
  (conj
    (concat
      (c/combinations rings 1) ;; one ring
      (c/combinations rings 2)) ;; two rings
    ())) ;; no rings

(defn make-item-sets [{:keys [weapons armor rings]}]
  (c/cartesian-product
    weapons
    (conj armor nil) ;; nil is for the no-armor case
    (make-ring-sets rings)))

(defn- add-item [player item]
  (merge-with + player (select-keys item [:dmg :armor :cost])))

(def init-player {:hp 100 :max-hp 100 :dmg 0 :armor 0 :cost 0})

(defn item-set->player [item-set]
  (reduce add-item init-player (remove nil? (flatten item-set))))

(defn hits [a b]
  (let [dmg (:dmg a)
        arm (:armor b)
        net-dmg (- dmg arm)]
    (update b :hp #(- % (clamp net-dmg 1)))))

(defn fight
  ([player boss] (fight player boss :player))
  ([player boss turn]
   (cond
     (<= (:hp player) 0) :boss
     (<= (:hp boss) 0) :player
     (= turn :player) (recur player (hits player boss) :boss)
     :else (recur (hits boss player) boss :player)
     )))

;For example, suppose you have 8 hit points, 5 damage, and 5 armor, and that the boss has 12 hit points, 7 damage, and 2 armor:
(assert (= :player (fight {:hp 8 :dmg 5 :armor 5} {:hp 12 :dmg 7 :armor 2})))

(def player-sets (map item-set->player (make-item-sets items)))
(def sorted-player-sets (sort-by :cost player-sets))

(def boss {:hp 103 :max-hp 103 :dmg 9 :armor 2})

(defn part1 []
  (:cost (seek #(= :player (fight % boss)) sorted-player-sets)))

(defn part2 []
  (:cost (seek #(= :boss (fight % boss)) (reverse sorted-player-sets))))