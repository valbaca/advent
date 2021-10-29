(ns advent-clj.year2015.day22
  (:require [advent-clj.elf :refer :all]))

;; TIL:
;; - Take a walk! Stepping away quickly helped me figure out one of the first problems (forgot to reset the armor buff)
;; - Getting into the problems I very recently solved in python, so the approach is very similar

(def boss-won Integer/MAX_VALUE)

(def least-mana-spent (atom boss-won))
(defn reset-least-mana-spent! [] (reset! least-mana-spent boss-won))
(defn update-least-mana-spent! [x] (swap! least-mana-spent min x))
;; DEBUG VERSION:
;(defn update-least-mana-spent! [x] (let [least (swap! least-mana-spent min x)] (println least) least))

;; Direct updaters
(defn harm [x d] (update x :hp (minus d)))
(defn heal [x d] (harm x (- d))) ;; cute

;; hit goes through armor and always does at least 1
(defn hit [tgt dmg]
  (let [net-dmg (clamp (- dmg (:armor tgt)) 1)]
    (update tgt :hp (minus net-dmg))))

(defn magic-missile [[player enemy effects]]
  [player
   (harm enemy 4)
   effects])

(defn drain [[player enemy effects]]
  [(heal player 2) ;; heal
   (harm enemy 2)
   effects])

(defn shield [[player enemy effects]]
  [player
   enemy
   (conj effects {:name :shield :turns 6 :effect-fn (fn [[p e]] [(assoc p :armor 7) e])})])

(defn poison [[player enemy effects]]
  [player
   enemy
   (conj effects {:name :poison :turns 6 :effect-fn (fn [[p e]] [p (harm e 3)])})])

(defn recharge [[player enemy effects]]
  [player
   enemy
   (conj effects {:name :recharge :turns 5 :effect-fn (fn [[p e]] [(update p :mana (plus 101)) e])})])

(def spells
  [{:name :magic-missile :cost 53 :spell magic-missile}
   {:name :drain :cost 73 :spell drain}
   {:name :shield :cost 113 :spell shield}
   {:name :poison :cost 173 :spell poison}
   {:name :recharge :cost 229 :spell recharge}])

(defn dead? [x] (<= (:hp x) 0))
(def min-mana (smallest (map :cost spells)))
(defn lost? [player] (or (dead? player) (< (:mana player) min-mana)))

;; resets the player's armor (otherwise it's on indefinitely) and handles part 2 "hard difficulty"
(defn pre-effects [player turn]
  (let [reset-player (assoc player :armor 0)]
    (if (and (= (:difficulty player) :hard) (= turn :player-turn))
      (update reset-player :hp dec)
      reset-player)))

(defn apply-effects [[player enemy effects] turn]
  (let [player' (pre-effects player turn)
        [player'' enemy'] (reduce (fn [a c] ((:effect-fn c) a)) [player' enemy] effects)
        effects' (remove #(zero? (:turns %)) (map #(update % :turns dec) effects))]
    [player'' enemy' effects']))

(defn pick-next-spells [player effects]
  (let [active (set (map :name effects))]
    (remove #(< (:mana player) (:cost %))
            (remove #(in? active (:name %)) spells))))

(defn cast-spell [{:keys [spell cost]} state]
  (let [[p enemy effects] (spell state)
        player (update (update p :mana-spent (plus cost)) :mana (minus cost))]
    [player enemy effects]))

(declare play)
(defn play-boss-turn [state]
  ;(println state) ;; DEBUG
  (let [[player enemy effects] (apply-effects state :boss-turn)]
    (cond
      (lost? player) boss-won
      (dead? enemy) (update-least-mana-spent! (:mana-spent player))
      :else (play [(hit player (:dmg enemy)) enemy effects]))))

(defn play [state]
  ;(println state) ;; DEBUG
  (let [[player enemy effects] (apply-effects state :player-turn)
        next-spells (pick-next-spells player effects)]
    (cond
      (lost? player) boss-won
      (dead? enemy) (update-least-mana-spent! (:mana-spent player))
      (< @least-mana-spent (:mana-spent player)) boss-won ;; quit while behind
      :else
      (->> next-spells
          (map #(cast-spell % [player enemy effects]))
          (map play-boss-turn)
          smallest))))

(comment (play [{:hp 10 :mana 250 :mana-spent 0 :armor 0} {:hp 13 :dmg 8} []]))
(comment (play [{:hp 10 :mana 250 :mana-spent 0 :armor 0} {:hp 14 :dmg 8} []]))

(def init-player {:hp 50 :mana 500 :mana-spent 0 :armor 0})
(def boss {:hp 58 :dmg 9})

(defn part1 []
  (reset-least-mana-spent!)
  (play [init-player boss []]))

(defn part2 []
  (reset-least-mana-spent!)
  (play [(assoc init-player :difficulty :hard) boss []]))