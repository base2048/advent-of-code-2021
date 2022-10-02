## [AoC 2021 Day 5: Hydrothermal Venture](https://adventofcode.com/2021/day/5)

We interpolate all points between the endpoints, convert the coordinates into unique strings and then count the frequency of the individual points. Storing the coordinates in an Integer using a shift operation resulted in a runtime that was more than twice as long.

As for part two, it makes no difference whether to check for the x or y coordinate as the end condition of Stream.iterate(). Both values change in the same way, by an absolute value of `1` with each step.

---

![AoC 2021 Day 5](../day05--Hydrothermal_Venture.png?raw=true)
