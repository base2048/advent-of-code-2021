## [AoC 2021 Day 9: Smoke Basin](https://adventofcode.com/2021/day/9)

Open the input data in an editor, which is able to search for an input and mark all hits at the same time. Search for `9`. You then see how the basins are separated from each other by ridges consisting of nines. With this in mind, we can start searching for the existing basins and collect all height data of a basin in a list.

Height data of basins that have already been scanned by *scanBasin()* are overwritten with a `9`, so that *findBasins()* does not take these data into account anymore. It may be also worth mentioning that we use kind of tail-recursion and so do not bloat the stack unnecessarily.

And last but not least speaking with Mötley Crüe: [Cause everybody knows that smoking ain't allowed in submarines](https://youtu.be/5oVBvxA0mm0).

---

![AoC 2021 Day 9](../day09--Smoke_Basin.png?raw=true)
