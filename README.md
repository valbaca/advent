# advent-clj

Advent of Clojure! 

Solving [Advent of Code](https://adventofcode.com) problems in [Clojure](https://clojure.org/) to get hands-on fun experience with the language.

Each day I write down a "Today I Learned" (TIL) so I don't just solve the problem, but also keep track of what I've learned, tricks I found, or just problems I liked.

`λ🎄.(λ🎅.🎄(🎅🎅))(λ🎅.🎄(🎅🎅))`

## Top References/Tools

- [Learn Clojure (XinYMinutes)](https://learnxinyminutes.com/docs/clojure/) **<-- Start here if you're totally new to Clojure**
- [Clojure Cheatsheet](https://clojure.org/api/cheatsheet)
- [ClojureDocs - community examples](https://clojuredocs.org/)
  - [clojure.string](https://clojuredocs.org/clojure.string)
- [regex.Pattern](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)


## Usage

**Prerequisite:** Install [Leiningen](https://leiningen.org/)

### Start REPL (Most recommended)

    $ lein repl
    # repl starts...
    # use -main as a sanity check
    advent-clj.core=> (-main)
    λ🎄.(λ🎅.🎄(🎅🎅))(λ🎅.🎄(🎅🎅))


### Run only core.clj and exit

    $ lein run

### Build uberjar, a jar with Clojure & deps

    $ lein uberjar
    # The resulting standalone can be run by java, from anywhere 
    $ java -jar target/uberjar/advent-clj-0.1.0-SNAPSHOT-standalone.jar

### Compile only .java files

    $ lein javac
    # Howdy is a basic hello world example
    $ java -cp target/default/classes/ com.valbaca.advent.Howdy


## Progress

### Advent of Clojure Progress

What I've completed in Clojure, within this repo

- Year 2015: 🎁  PORTING IN PROGRESS. Currently porting over solutions from Go to get warmed up
- Years 2016 - 2020: 🎅 NOT STARTED in Clojure

### Overall Personal Advent Account Progress

What I've completed **in any language**

- Year 2015: 🎄 DONE! Mostly Days 1-19 in Go, 19-22 in Python, rest in Java
- Year 2016: 🎁 IN PROGRESS @ Day 19. Days 1-7 in Python, rest in Java.
- Year 2017: 🎁 IN PROGRESS @ Day 7. Days 1-5 in Crystal
- Year 2018: 🎁 IN PROGRESS @ Day 6
- Year 2019: 🎁 IN PROGRESS @ Day 2
- Year 2020: 🎁 IN PROGRESS @ Day 4

## License

Copyright © 2021 Valentin Baca

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
