## [AoC 2021 Day 8: Seven Segment Search](https://adventofcode.com/2021/day/8)

I must admit, part 2 took me quite a bit of time. What I didn't want was to specify a strict way of solving the problem, as indirectly suggested in the description. Instead, the goal is to let the samples sort themselves out until all the wiring of the segments is determined.

To achieve this, we form groups defined by the number of active segments. In this way, a link can be established between a sample digit and a proper digit, or to be more specific, between sample digit segments and proper digit segments. Within such a group, three subgroups can then be formed:

1. all common active segments
2. all non-common active segments
3. all inactive segments

By means of elimination procedures, the correct mapping is then determined step by step.

Side note: the code inside *keepAndDiscard()* could be replaced by a fancy stream as follows:

```Java
private void keepAndDiscard(Map<Integer, Integer> mappings, int samplesSegments, int propersSegments) {
    Stream.iterate(1, pos -> pos < 1 << MAX_SEGMENT_COUNT, pos -> pos << 1)
        .filter(segment -> (samplesSegments & segment) > 0)
        .forEach(segment -> mappings.compute(segment, (k, v) -> v & propersSegments));
}
```

For the sake of readability, and also because it has been proven that the classic for-loop variant is more performant, we will stay old-school.

---

![AoC 2021 Day 8](../day08--Seven_Segment_Search.png?raw=true)
