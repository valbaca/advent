(defproject advent-clj "0.1.0-SNAPSHOT"
  :description "Solving Advent of Code with Clojure: Advent of Clojure!"
  :url "http://github.com/valbaca/advent"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/data.priority-map "1.1.0"]
                 [org.clojure/math.combinatorics "0.1.6"]
                 [digest "1.4.10"]
                 [org.clojure/data.json "2.4.0"]
                 ;; Java deps
                 [org.projectlombok/lombok-maven-plugin "1.18.20.0"]
                 [org.projectlombok/lombok "1.18.20"]
                 [com.google.guava/guava "31.0.1-jre"]
                 [org.apache.commons/commons-lang3 "3.12.0"]
                 [org.apache.commons/commons-text "1.9"]
                 [org.apache.commons/commons-math3 "3.6.1"]
                 [commons-codec "1.15"]]
  :main ^:skip-aot advent-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :test    {
                       :dependencies [
                                      ;; Java Test Deps
                                      [org.junit.jupiter/junit-jupiter-api "5.6.0"]
                                      [org.junit.jupiter/junit-jupiter-engine "5.8.1"]
                                      [org.assertj/assertj-guava "3.4.0"]]
                       }}
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :test-paths ["test/clojure" "test/java"])