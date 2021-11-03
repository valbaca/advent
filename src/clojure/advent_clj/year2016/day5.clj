(ns advent-clj.year2016.day5
  (:require
    [advent-clj.elf :refer :all]
    [clojure.string :as s]))

;; TIL:
;; - Pulled in some minor interop with Java via md5
;; - With that sorted, this one was really fun
;; - Using lazy-seq to generate the infinite sequence of ("id0" "id1" ...) was really helpful.
;; Then it just became a matter of: map, filter/remove/keep, and reduce
;; - (keep f coll) is like (remove nil? (map f coll))
;; - Used replace for the first time for the "animation", very handy! Literally does what it says

(defn id+index
  ([id] (id+index id 0))
  ([id idx]
   (lazy-seq (cons (str id idx) (id+index id (inc idx))))))

(defn pass-char [hash]
  (when (s/starts-with? hash "00000")
    (nth hash 5)))

(def input "ojvtpuvg")

(defn part1 [input]
  (->> input
       id+index
       (map md5)
       (keep pass-char)
       (take 8)
       s/join))

;; part 2

(defn pass-pos-char [hash]
  (when (s/starts-with? hash "00000")
    (let [pos (- (int (nth hash 5)) (int \0))
          ch (nth hash 6)]
     [pos ch])))

(defn rd-password [passv [pos ch]]
  (if (and (within? (count passv) pos) (not (nth passv pos)))
    (let [upd (assoc passv pos ch)]
      (println (s/join (replace {nil \_} upd))) ;; "animation"
      (if (in? upd nil) upd (reduced upd))) ;; quit when we're done
    passv))


(defn part2 [input]
  (->> input
       id+index
       (map md5)
       (keep pass-pos-char)
       (reduce rd-password (vec (repeat 8 nil)))
       s/join))