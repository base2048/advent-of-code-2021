## [AoC 2021 Day 22: Reactor Reboot](https://adventofcode.com/2021/day/22)

The first thought was to split overlapping cuboids while working through the steps. This would be quite doable and with a little bit of calculation the result of two overlapping cuboids would be a maximum of 6 sub cuboids plus the sliced one. So no reason to split each of them into 27 pieces.

Nevertheless, that would have been quite a large number of cuboids. So why not go a bit more algorithmic. The logical way is to permanently adjust the result during the process. Looking for a suitable geometric algorithm, one inevitably comes across the Sweep method, which would probably be the best approach for this challenge. However, I didn't want to invest so much time in a completely new topic, and so I started to add cuboid by cuboid subtracting the overlaps. Since the number of cuboids is manageable (420), there is no compelling reason to achieve a linear runtime.

Here are some illustrations of one-dimensional cuboids that may help to get a better understanding of what is going on under the hood:

```
         One-dimensional cuboids              Begin of adding cuboid #3                Simple example       
    ──────────────────┬──────────────     ───────────────────┬─────────────     ─────────────────────────────
    ──────────┬───────┼──────────────     ───────┬───────────┼─────────────     ──────────┬──────────────────
    ──────────┼───────┼───┬──────────     ───────┼──────╥╥╥╥╥┼╥╥╥╥╥┬───────     ──────────┼───┬───┬──────────
    ──────────┼───┬───┼───┼──────────     ───────┼╥╥╥╥╥┬║║║║║┼║║║║║┼───────     ──────────┼───┴───┴──────────
    ──────────┼───┼───┴───┼──────────     ───────┼╨╨╨╨╨┼╨╨╨╨╨┴║║║║║┼───────     ──────────┴──────────────────
    ──────────┴───┼───────┴──────────     ───────┴─────┼──────╨╨╨╨╨┴───────     ─────────────────────────────
    ──────────────┴──────────────────     ─────────────┴───────────────────     ─────────────────────────────
              0   1   2   3                      0     1     2     3                      0   1   2
```

Starting at index 0, we add cuboid #0, and since there are no cuboids in the list of already processed ones, that's it. Now we add cuboid #1. c#0 has a size of 4, c#1 a size of 3. We then subtract the intersection of c#1 and c#0. The result: `4 + 3 - 2 = 5`.

c#2 comes next. We add a value of 4 and now things start to get a little tricky. Not only do we have to take into account the overlap between c#2 and c#1, but also the overlap between c#2 and c#0. And, in order to subtract the correct amount, we must also check these overlaps for intersections with cuboids that have been processed before this intersections index. So what you do is you build temporary cuboids from the intersections that helps to do these calculations. Yeah, took me quite a bit of time, paper and crayons to get it all together correctly in my brain.

As for the off-cuboids, we simply do not include their cubes in the *onCounts*, and during the process of *computeOverlapCorrection()*, any active cubes that are within the range of the off-cuboid are automatically deactivated.

Starting with the simple example on the far right is probably the best way to get a first grasp. From then on it's only a small step to developing a multidimensional many-worlds quantum intelligence. I'm pretty sure, deep down in the trench, there are probably some looney crabs waiting for me to help them just doing that. As long as I'm not blamed for it, everything is fine.

---

![AoC 2021 Day 22](../day22--Reactor_Reboot.png?raw=true)
