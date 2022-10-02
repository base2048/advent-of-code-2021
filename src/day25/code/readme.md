## [AoC 2021 Day 25: Sea Cucumber](https://adventofcode.com/2021/day/25)

A quick and fancy implementation with two sets containing bit encoded coordinates as integers, unfortunately showed a rather bad runtime behavior. Although we iterate over all active coordinates only once per step, accessing the HashSet is constant with O(1) and the hash function basically has nothing to do, this version takes almost 700ms. Bit operations are then not the means of choice for this.

<details>
    <summary>HashSet Version</summary>

```Java
public long solvePart1() {
    int steps = 0;
    boolean hasMoved = true;

    while (hasMoved) {
        hasMoved = false;
        steps++;

        Set<Integer> nextEast = new HashSet<>(east.size());
        Set<Integer> nextSouth = new HashSet<>(south.size());

        for (int coords : east) {
            int nextPos = ((coords >>> 16) + 1) % width << 16 | coords & 0xFFFF;
            if (east.contains(nextPos) || south.contains(nextPos)) nextEast.add(coords);
            else hasMoved = nextEast.add(nextPos);
        }
        east = nextEast;

        for (int coords : south) {
            int next_pos = coords & 0xFFFF0000 | ((coords & 0xFFFF) + 1) % height;
            if (east.contains(next_pos) || south.contains(next_pos)) nextSouth.add(coords);
            else hasMoved = nextSouth.add(next_pos);
        }
        south = nextSouth;
    }

    return steps;
}
```
</details>

So let's rebuild and use a primitive byte array. As far as memory requirements are concerned, this is about the same as the HashSet version, as long as you use the same data type. The number of occupied and free cells on the grid is roughly equal. Previously we used temporary Sets, in the array version we work in-place. And although we now have to run over the whole grid twice per step, which in total with the empty cell check is quadrupling the cell evaluations, the current version takes less than 50ms. [Time to run, Rudolph](https://youtu.be/-fCpsBzOeUQ)<!-- Run Rudolph Run, Keith Richards -->.

---

![AoC 2021 Day 25](../day25--Sea_Cucumber.png?raw=true)
