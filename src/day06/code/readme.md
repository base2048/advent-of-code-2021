## [AoC 2021 Day 6: Lanternfish](https://adventofcode.com/2021/day/6)

Instead of putting each fish on its own timer, we take a primitive array, where the field indices stand for the corresponding timers. Then we put each fish according to its current timer into the corresponding collection box and shift the whole swarm one timer to the left with each day.

Perhaps also worth mentioning is the use of a custom Collector in the *Parser* class.

---

![AoC 2021 Day 6](../day06--Lanternfish.png?raw=true)
