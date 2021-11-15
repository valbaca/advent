(ns advent-clj.year2016.day10
  (:require
    [advent-clj.elf :refer :all]))

; TIL
; - Even a "stateful" problem like this can be handled gracefully
; - When using a map or any assoc structure (like I did for bots) keep it as the first arg
; This allows for easy -> threading
; (And when using a seq, keep it as the last arg, for easy ->> threading)

;; jus showing what the "bots" map looks like
(def bots {:id {:vals    {}
                :targets {}}})

(declare update-bots)

(defn give-val [bots id val]
  (update-bots (assoc-in bots [id :vals val] val) id))

(defn give-target [bots id lo-hi target]
  (update-bots (assoc-in bots [id :targets lo-hi] target) id))

(defn bot-ready? [bots id]
  (let [bot (get bots id)
        has-vals (= 2 (count (:vals bot)))
        has-targets (= 2 (count (:targets bot)))
        bot-ready (and has-vals has-targets)]
    (when bot-ready bot)))

(defn update-bots [bots id]
  (if-let [bot (bot-ready? bots id)]
    (let [lo-tgt (get-in bot [:targets :lo])
          hi-tgt (get-in bot [:targets :hi])
          [lo-val hi-val] (sort (vals (:vals bot)))]
      (-> bots
          (give-val lo-tgt lo-val)
          (give-val hi-tgt hi-val)))
    bots))


(def test-input (ns-test-input))

(defn parse [line]
  (let [seps (sep line)
        op (first seps)]
    (if (= op "value")
      (fn [bots]
        (give-val bots #{"bot" (last seps)} (second seps)))
      (fn [bots]
        (-> bots
            (give-target #{"bot" (second seps)} :lo #{(nth seps 5) (nth seps 6)})
            (give-target #{"bot" (second seps)} :hi #{(nth seps 10) (last seps)}))))))

(defn exec [input] ((apply comp (map parse input)) {}))

(defn part1 [input]
  (let [output (exec input)]
    (first
      (seek
        (fn [[_ v]] (= #{17 61} (set (keys (:vals v)))))
        (seq output)))))

(def input (ns-input))

(comment (part1 input))

(defn part2 [input]
  (let [seek-three (fn [[k _]] (or (= k #{"output" 0}) (= k #{"output" 1}) (= k #{"output" 2})))
        get-output-value (comp keys :vals second)]
    (->> (exec input)
         (filter seek-three)
         (mapcat get-output-value)
         product)))

(comment (part2 input))