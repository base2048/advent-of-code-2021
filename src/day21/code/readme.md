## [AoC 2021 Day 21: Dirac Dice](https://adventofcode.com/2021/day/21)

Straight to part two. If it is already stated in the specification that the result is a 15-digit number, then you can be sure that there is no prize to gain by simulating. Dynamic Programming is the answer - for once not 42. So the minimum you need is a cache and a loop - or, if resource-wise possible, a recursion.

The most challenging aspect of Dynamic Programming is to fully understand the problem in its details. Once this has been managed, a working algorithm can be developed. So let's start with understanding.

We know that after each roll, three new universes are created: one in which the player rolled a `1`, one in which a `2` was rolled, and one with a `3`. Since the player must roll the dice three times on his turn, this creates a total of 27 new universes. Let's illustrate this in a probability tree:

```                          
               ┌──────────────────────────0──────────────────────────┐
               │                          │                          │
              1│                         2│                         3│                
               │                          │                          │
      ┌────────1────────┐        ┌────────2────────┐        ┌────────3────────┐
      │        │        │        │        │        │        │        │        │
     1│       2│       3│       1│       2│       3│       1│       2│       3│
      │        │        │        │        │        │        │        │        │
   ┌──2──┐  ┌──3──┐  ┌──4──┐  ┌──3──┐  ┌──4──┐  ┌──5──┐  ┌──4──┐  ┌──5──┐  ┌──6──┐
  1│ 2│ 3│ 1│ 2│ 3│ 1│ 2│ 3│ 1│ 2│ 3│ 1│ 2│ 3│ 1│ 2│ 3│ 1│ 2│ 3│ 1│ 2│ 3│ 1│ 2│ 3│
   │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │  │
   3  4  5  4  5  6  5  6  7  4  5  6  5  6  7  6  7  8  5  6  7  6  7  8  7  8  9
```

The result is 27 universes with 27 dice sums. Since in some universes the dice sums are equal, the first optimization is already possible here.

| Dice Sum | in how many Universes | 
|:--------:|:---------------------:|
 |    3     |           1           |
 |    4     |           3           |
 |    5     |           6           |
 |    6     |           7           |
 |    7     |           6           |
 |    8     |           3           |
 |    9     |           1           |

For example, we now know that each player on his turn creates, among 24 others, three universes, in which the result of the dice sum is `4`. Therefore, if he wins in one of these three universes, he logically wins in the other two as well.

And this insight also leads us directly on to the big solution: if within the billions of theoretical games we reach a state that we have already reached once in some other universe, we do not need to compute the result again. We simply take what's in the cache and move on.

We still have to calculate all possible outcomes, but according to the possibilities of how many different game states there are, the total number of games that have to be evaluated is reduced to a theoretical maximum of: **21 score states player one** multiplied by **10 positions player one** multiplied by **21 score states player two** multiplied by **10 positions player two** multiplied by **2 possibilities of which player's turn**: `21 * 10 * 21 * 10 * 2 = 88200`.

But this is only a theoretical maximum. In practice, at least one player is not on position 1 after his first turn. Thus, his starting score is higher than 1 and therefore all combinations with a score below his starting score do not need to be calculated. An additional optimization could be achieved by removing the active player from the combination and adjusting the algorithm in a way that the results are only calculated for one player and mirrored accordingly for the other. The runtime is already good enough, so no need for extra work.

---

![AoC 2021 Day 21](../day21--Dirac_Dice.png?raw=true)
