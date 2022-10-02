## [AoC 2021 Day 23: Amphipod](https://adventofcode.com/2021/day/23)

So this was quite a challenge. The first attempt to implement the whole thing on bit level using two Long, one for the hallway and one for the rooms, instead of an array, showed pretty soon that this is not the best in terms of speed. There were simply too many bit operations, too many multiplications, divisions and modulo-operations.

Even if divisions and modulo-operations could be replaced by multiplications as far as possible, there was nothing big to gain. Even a power of two 4 bit spot width still requires incrementing in the 4-step range at iterations. Altogether just too many small things, which eventually double the runtime to the current version. In the end a classic primitive array was chosen to represent the positions of the regarding state:

```
    Side Wing       Side Wing
    ┌↓ ↓     Hallway   ↓ ↓ ─                     Rooms                     ┐
     0|1|2|3|4|5|6|7|8|9|10║11|12|13|14║15|16|17|18║19|20|21|22║23|24|25|26
    └    ↑   ↑   ↑   ↑     ─   Room 0      Room 1      Room 2      Room 3  ┘
       Entrance Positions
         (= No Parking)
```

About the code itself there is not much to say. A dynamic Dijkstra algorithm is used, which was adapted according to the specifications. For comprehensibility, more comments than usual were placed this time.

An attempt to implement a heuristic function calculating the minimum Manhattan distance did not bring any additional gain, rather the opposite. The runtime increased by almost one third. At least here Dijkstra seems to beat A*. About 200 milliseconds for each part is acceptable.

---

![AoC 2021 Day 23](../day23--Amphipod.png?raw=true)
