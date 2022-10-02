## [AoC 2021 Day 15: Chiton](https://adventofcode.com/2021/day/15)

Classic Dijkstra in array variation. Paths are bit-encoded as follows: x: 9 bit, y: 9 bit, costs: 14 bit -> `x << 23  | y << 14 | costs`. This approach spares a separate class and gives easy access to the priority queue.

In part one the grid is 100 x 100, the maximum edge weight is 9, so the maximum possible costs are 198 * 9 = 1782.\
In part two the grid is 500 x 500, the maximum edge weight is 9, so the maximum possible costs are 998 * 9 = 8982;

Although the sample data in both parts extend paths only to the right and downwards, we search through all possible paths in textbook fashion. Nevertheless, in terms of runtime no further optimization is needed.

---

![AoC 2021 Day 15](../day15--Chiton.png?raw=true)
