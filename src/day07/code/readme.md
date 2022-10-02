## [AoC 2021 Day 7: The Treachery of Whales](https://adventofcode.com/2021/day/7)

Just a little high school math. For part one, we take the median. It doesn't matter if the number of crabs is even or odd.

However, since in part two the increase in the crabs' fuel consumption is no longer linear, we need the mean value here. Unlike in part one, we have to perform two calculations in case the mean is not an even number, one rounded up and one rounded down, and then take the less expensive one as the answer. Since the delta increase per step is constant, we can use the Gaussian sum formula for the calculation.

---

![AoC 2021 Day 7](../day07--The_Treachery_of_Whales.png?raw=true)
