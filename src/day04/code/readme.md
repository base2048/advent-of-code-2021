## [AoC 2021 Day 4: Giant Squid](https://adventofcode.com/2021/day/4)

To make life a little easier, we extend the board both horizontally and vertically by one row. In these additional fields we now store the number of hits per row. We could also use the last free corner field [5][5] for continuous board calculation, but this would lead to unnecessary calculations for all boards.

In part one, we could abort the *checkBoards()* method early, once we have found a winning board. There is no reason to check the remaining boards still in play, since the specification does not explicitly state that two boards can win at the same time. However, this would unnecessarily complicate the code to comply with part two as well.

In order to be able to use the *checkBoards()* method for both parts, a rather unusual and less recommendable practice of labeling for-loops was applied here. Since the for-loop is quite short and otherwise fairly clear, this is acceptable.

Drawn numbers are replaced with `-1` on their fields, since the number `0` is also one of the numbers that can be drawn. Shortcut optimizations would be possible, but make the code unnecessarily complicated and are not strongly indicated with a runtime of 5 milliseconds each part.

---

![AoC 2021 Day 4](../day04--Giant_Squid.png?raw=true)
