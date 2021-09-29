# advent
Solving Advent of Code problems. See https://adventofcode.com/

# Running

## Requirements:
- Gradle
- Java 16+

Run with Gradle wrapper: `./gradlew`

```shell
# Compile and run on JVM
$ ./gradlew run           # compile and run on jvm

# to pass args to the main method:
$ ./gradlew run --args="foo"

# Native via GraalVM
$ ./gradlew nativeImage   # build native executable
$ ./build/graal/advent    # run native executable
```

# My Advent Year Progress

- Advent Year 2015: **FINISHED** 
  - Days 1-19 solved with Go in 2019 (TODO: convert to Java)
  - Days 19-22 solved with Python in 2021
  - Last couple of days finished with Java in 2021
- Advent Year 2016: *IN PROGRESS*
  - Days 1-7 solved with Python in 2019-2020
  - Days 1 solved with Clojure in 2018 and Day 8 solved with Clojure in 2021
- Year 2017: *IN PROGRESS*
  - Days 1-5 solved with Crystal in 2020

## Why different languages? What's best?

I like using Advent of Code to learn the basics of working with a language: reading & parsing input, building functions, classes, etc.

Throughout this process I've found the general strengths and weaknesses of the various languages:

- [Go](https://go.dev/)
  - Pros: Very efficient, millisecond execution, fast regex, simple. Code basically writes itself.
  - Cons: Bad ergonomics; repetitive error handling, no function overloading or generics, slices are [tricky and need a cheatsheet](https://ueokande.github.io/go-slice-tricks/). Language where I was actually limited by my typing speed.
  - Verdict: By pure numbers, Go has helped me solve the most problems by mostly getting out of my way, but it ruins that by inserting err/nil handling at every step.
- [Python](https://www.python.org/)
  - Pros: Incredibly readable/writable language, great syntax, does so much for you. Great support in VS Code for writing, running, & debugging. 
  - Cons: Very slow, often unsure if my code is poorly written or if Python was just inefficient. Dealing with pyenv/versions was a nightmare.
  - Verdict: Python is the #1 language for Advent for good reason. My main gripe was never knowing if my solution was slow (and wrong) or just slow because of Python itself.
- [Clojure](https://clojure.org/)
  - Pros: REPL, functional programming gives super-powers
  - Cons: Very, very slow, especially startups. REPL connection finicky. Spent more time wrestling with tools than code. Sometimes immutability really slows down writing or execution
  - Verdict: I only did a small amount of Advent with Clojure, and while Clojure gives powerful capabilities, it felt like I was translating the problem once into my mind, then again into Clojure, then again dealing with debugging/tooling.
- [Crystal](https://crystal-lang.org/)
  - Pros: very, very efficient, **micro**second executions. Arguably a perfect language
  - Cons: Very rough edges. No IDE support, no debugger, no autocomplete. Printf-debugging is all you've got  
  - Verdict: Crystal will be *fantastic* when it gets better tooling.
- [Java](https://dev.java/)
  - Pros: efficient, sub-second execution. Tons of data structures, algorithms, and help. IntelliJ is awesome.
  - Cons: verbose static void main. Boring. Bad higher-level programming (no macros etc).
  - Verdict: Java is my personal primary language and the IntelliJ IDE does a great job of covering Java's warts.

