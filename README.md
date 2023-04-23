# advent

Solving [Advent of Code](https://adventofcode.com) problems in [Clojure](https://clojure.org/) and [Java](https://dev.java) to get hands-on fun experience with the language.

Each day I write down a "Today I Learned" (TIL) so I don't just solve the problem, but also keep track of what I've learned, tricks I found, or just problems I liked.

`λ🎄.(λ🎅.🎄(🎅🎅))(λ🎅.🎄(🎅🎅))`

## Top References/Tools

- [Learn Clojure (XinYMinutes)](https://learnxinyminutes.com/docs/clojure/) **<-- Start here if you're totally new to Clojure**
- [Clojure Cheatsheet](https://clojure.org/api/cheatsheet)
- [ClojureDocs - community examples](https://clojuredocs.org/)
  - [clojure.string](https://clojuredocs.org/clojure.string)
- [Associative (Map) Destructuring](https://clojure.org/guides/destructuring#_associative_destructuring)
- [regex.Pattern](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)


## Usage

Prerequisites:
1. Install Java
2. Install Clojure
3. Install [Leiningen](https://leiningen.org/)

Highly Recommended: 
1. [IntelliJ](https://www.jetbrains.com/idea/): the best Java IDE
    - Note: IntelliJ has a free Community Edition
2. [Cursive](https://cursive-ide.com/): IntelliJ plugin that adds Clojure support
    - Note: Cursive has a free license for non-commercial

### Start REPL (Most recommended)

    $ lein repl

    # repl starts. use -main as a sanity check

    advent-clj.core=> (-main)
    λ🎄.(λ🎅.🎄(🎅🎅))(λ🎅.🎄(🎅🎅))


### Just run core.clj and exit

    $ lein run

### Build uberjar, a jar with Clojure & deps

    $ lein uberjar

    # The resulting standalone can be run by java, from anywhere, and executes core.clj 
    $ java -jar target/uberjar/advent-clj-0.1.0-SNAPSHOT-standalone.jar

### Compile only .java files

    $ lein javac

    # Howdy is a basic hello world example
    $ java -cp target/default/classes/ com.valbaca.advent.Howdy

### IntelliJ / Cursive notes

If you get `ClassNotFound` when running a method in Clojure in IntelliJ, configure the following:
- How to run it
  - Run with Leiningen
    - Profiles: dev

## Progress

### Advent of Clojure Progress

What I've completed in Clojure, within this repo

- Year 2015: 🎄 All in Clojure!
- Year 2016: 🎁 IN PROGRESS
- Years 2016 - 2020: ❄️ NOT STARTED *in Clojure*

### Overall Personal Advent Account Progress

What I've completed **in any language**

- Year 2015: 🎄 DONE! Did days 1-19 in [Go](https://go.dev/), 19-22 in [Python](https://python.org/), and rest in Java. Re-solved in Clojure to warm up.
- Year 2016: 🎁 IN PROGRESS @ Day 19. Days 1-7 in Python, 8-18 in Java.
- Year 2017: ❄️ ON ICE. Was @ Day 7. Days 1-5 in [Crystal](https://crystal-lang.org/)
- Year 2018: ❄️ ON ICE. Was @ Day 6
- Year 2019: ❄️ ON ICE. Was @ Day 2
- Year 2020: 🎄 DONE! All done in Python in [valbaca/advent-py](https://github.com/valbaca/advent-py)
- Year 2021: 🎅 IN PROGRESS in Python in [valbaca/advent-py](https://github.com/valbaca/advent-py)
- Year 2022: 🎄 DONE! All in Kotlin [valbaca/advent-kt](https://github.com/valbaca/advent-kt)

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
