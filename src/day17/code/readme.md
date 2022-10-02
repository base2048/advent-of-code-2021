## [AoC 2021 Day 17: Trick Shot](https://adventofcode.com/2021/day/17)

To solve part one we need to calculate the maximum altitude that can be reached with any initial launch velocity still hitting the target. Let's stipulate that there is always an initial x-velocity that allows the x-range of the target to be reached at whatever y-velocity. Under the given conditions, the probe will hit the abscissa exactly again after its ascent and afterwards the y-position values will become negative.

We reach the maximum ascent when the last step, which leads to still hitting the target within the required y-coordinates, has a length equal to the distance from the abscissa to the minimum y-position. Since the length of the y-steps increases by exactly one with each further step after reaching the high point, this is the best place to thank Gauß for his ingenuity by applying his summation formula.

```
   7  ............................................
   6  .......................#....#...............
   5  .................#..............#...........
   4  ............................................
   3  ..........#........................#........
   2  ............................................
   1  ............................................
   0  ..S..................................#......
  -1  ............................................
  -2  ...................................┌─────┐..
  -3  ...................................│.....│..
  -4  ...................................└──#──┘..
  -5  ............................................
```

To solve part two, we must first constrain the possible initial x and y velocities and then calculate all possible trajectories and see if they would hit the target. The values for the maximum initial x-velocity and the minimum initial y-velocity can be taken directly from the instructions, since any values larger respectively smaller would lead to the target no longer being hit. The minimum x-velocity and maximum y-velocity, on the other hand, must be calculated.

To get the minimum initial x-velocity we once again use good old Gauß:\
`minXVelocity = (n * n + n) / 2`

Now we have to solve for n.

- first step: achieving a quadratic formula:\
`n * n + n - 2x = 0`


- second step: [WolframAlpha](https://www.wolframalpha.com/widgets/view.jsp?id=ad90fa06581eed56d398e0c50fb52357) or [Symbolab](https://www.symbolab.com/solver) - or just remembering highschool maths:\
`n = (-1 + sqrt(8x + 1)) / 2`

Getting the maximum initial y-velocity, according to part one, is by taking the absolute of the minimum y-position decreased by `1`.

---

![AoC 2021 Day 17](../day17--Trick_Shot.png?raw=true)
