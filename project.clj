(defproject advent-clj "0.1.0-SNAPSHOT"
  :description "Solving Advent of Code with Clojure: Advent of Clojure!"
  :url "http://github.com/valbaca/advent-clj"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/data.priority-map "1.1.0"]
                 [org.clojure/math.combinatorics "0.1.6"]
                 [digest "1.4.10"]]
  :main ^:skip-aot advent-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
