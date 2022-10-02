# [Advent of Code 2021](https://adventofcode.com/2021)

|  #  |                            Challenge                            |          Solution          | Keywords                |
|:---:|:---------------------------------------------------------------:|:--------------------------:|:------------------------|
|  1  |       [Sonar Sweep](https://adventofcode.com/2021/day/1)        | [Solution](src/day01/code) |                         |
|  2  |          [Dive!](https://adventofcode.com/2021/day/2)           | [Solution](src/day02/code) |                         |
|  3  |    [Binary Diagnostic](https://adventofcode.com/2021/day/3)     | [Solution](src/day03/code) |                         |
|  4  |       [Giant Squid](https://adventofcode.com/2021/day/4)        | [Solution](src/day04/code) |                         |
|  5  |   [Hydrothermal Venture](https://adventofcode.com/2021/day/5)   | [Solution](src/day05/code) |                         |
|  6  |       [Lanternfish](https://adventofcode.com/2021/day/6)        | [Solution](src/day06/code) |                         |
|  7  | [The Treachery of Whales](https://adventofcode.com/2021/day/7)  | [Solution](src/day07/code) |                         |
|  8  |   [Seven Segment Search](https://adventofcode.com/2021/day/8)   | [Solution](src/day08/code) |                         |
|  9  |       [Smoke Basin](https://adventofcode.com/2021/day/9)        | [Solution](src/day09/code) | Tail Recursion          |
| 10  |     [Syntax Scoring](https://adventofcode.com/2021/day/10)      | [Solution](src/day10/code) |                         |
| 11  |      [Dumbo Octopus](https://adventofcode.com/2021/day/11)      | [Solution](src/day11/code) | Simple Recursion        |
| 12  |     [Passage Pathing](https://adventofcode.com/2021/day/12)     | [Solution](src/day12/code) | Graph Traversal         |
| 13  |   [Transparent Origami](https://adventofcode.com/2021/day/13)   | [Solution](src/day13/code) |                         |
| 14  | [Extended Polymerization](https://adventofcode.com/2021/day/14) | [Solution](src/day14/code) | String Manipulation     |
| 15  |         [Chiton](https://adventofcode.com/2021/day/15)          | [Solution](src/day15/code) | Dijkstra                |
| 16  |     [Packet Decoder](https://adventofcode.com/2021/day/16)      | [Solution](src/day16/code) | Recursion, IOStream     |
| 17  |       [Trick Shot](https://adventofcode.com/2021/day/17)        | [Solution](src/day17/code) |                         |
| 18  |        [Snailfish](https://adventofcode.com/2021/day/18)        | [Solution](src/day18/code) | LinkedList              |
| 19  |     [Beacon Scanner](https://adventofcode.com/2021/day/19)      | [Solution](src/day19/code) | 3D Brain Fog            |
| 20  |       [Trench Map](https://adventofcode.com/2021/day/20)        | [Solution](src/day20/code) | Cellular Automaton      |
| 21  |       [Dirac Dice](https://adventofcode.com/2021/day/21)        | [Solution](src/day21/code) | Dynamic Programming     |
| 22  |     [Reactor Reboot](https://adventofcode.com/2021/day/22)      | [Solution](src/day22/code) | Cuboid Union, Recursion |
| 23  |        [Amphipod](https://adventofcode.com/2021/day/23)         | [Solution](src/day23/code) | Dijkstra Beats a Star   |
| 24  |  [Arithmetic Logic Unit](https://adventofcode.com/2021/day/24)  | [Solution](src/day24/code) | Reverse Engineering     |
| 25  |      [Sea Cucumber](https://adventofcode.com/2021/day/25)       | [Solution](src/day25/code) |                         |

All challenges were implemented in Java. In contrast to other years, more attention was paid to speed this time, which resulted in fewer stream operations being used. As a positive side effect, this also improves the readability and comprehensibility of the code.

Eric seems to do his best to gift us with more and more outstanding challenges every year. As always, the level of difficulty increases during Advent. This year worth mentioning definitely are [Day 21](src/day21/code), [Day 22](src/day22/code) and [Day 23](src/day23/code).

A click on the *Challenge* link directly leads to the corresponding challenge page on [Advent of Code](https://adventofcode.com). As long as part one of the daily challenge has not been completed successfully, part two is not visible. Therefore, a screenshot with the complete challenge information was added to the source folder of the day. A click on *Solution* leads straight to the daily code folder.

```
Advent of Code 2021
│   readme.md
│
├───src
│   │   Runner.java
│   │
│   ├───day01
│   │   └───code
│   │           Challenge.java      // contains the main business logic
│   │           Main.java           // contains main-method and runs the specific day
│   │           Parser.java         // parses input data for the Challenge constructor
│   │           data.txt            // challenge input data
│   │
│   ├───day02
│   ...
│   └───utils
│           AbstractParser.java
│           Presenter.java
│           Timer.java
│   
└───test
```

The challenges are all structured in the same way. The business logic is mainly within the *Challenge* file, sometimes there are additional classes. To execute a challenge, an instance needs to be created. The Constructor expects appropriately prepared input data. This job is done by the *Parser*.

The *Main* file runs the daily challenge with the help of the *Timer* class, located in the utility folder. In order to be able to measure the preparation time as well, in particular everything that is triggered by the Constructor, the *Timer* creates an instance using Reflection. For the measurement of the actual runtimes, only an Interface is required.

The *Runner* file runs all challenges by searching the packages for *Main* classes and then invoking their *main()* method.

If you want to run a challenge by yourself without the help of *Main* or *Runner* class, do it as follows:

```Java
Challenge challenge = new Challenge(new Parser().fetchData());
long result1 = challenge.solvePart1();
long result2 = challenge.solvePart2();
```

The *fetch()* method, located in the *AbstractParser*, fetches the correct data for the challenge, and then passes it on as an InputStream to the *parse()* method. In case you want to use your own input data, this can be done as follows:

```Java
InputStream is = Files.newInputStream(Path.of("your_input_file.txt"));
Challenge challenge = new Challenge(new Parser().parse(is));
```

Whenever possible, unit tests were created using sample data from the description.

Code blocks in the *readme* files did not make it into the final versions. Usually these are alternative implementations, but I consider the final ones to be nicer, more modern or simply faster. Please think of this code as additional information only.

An explicit check for accuracy of the input data as well as of the passed parameter values within the implementation has been omitted in the interest of clarity and readability. We assume that both the input data and the passed values are correct. Of course, I have checked the code and the results and can confirm that everything is fine.

---

![AoC 2021 Dive](src/dive.png?raw=true)

The evolution of Santa's means of transportation:\
Sleigh &nbsp;**&#10143;**&nbsp; Submarine &nbsp;**&#10143;**&nbsp; [Choo-Choo Train](https://youtu.be/iYO8mrsgw9g) <!-- Boogie Woogie Choo Choo Train, The Tractors -->
