## [AoC 2021 Day 14: Extended Polymerization](https://adventofcode.com/2021/day/14)

Since this is not my first underwater rodeo, I was of course immediately aware of where the journey was going. Nevertheless, I wanted to implement part one in the classic bruteforce way first, also to see how many iterations are possible without getting gray hair. The use of a CharBuffer is of course completely overdone at the cost of resources, but hey: convenience rules.

To successfully solve part two within human lifespans, the tactic was changed to tracking only the number of pair occurrences. Thus, after the counter has been initially loaded with the letter counts from the polymer-template, we have to consider only the additional insertions extending the according count value by the number of the corresponding pair.

```
Insertions  │   Polymer       Pairs                   Counts
            │ 
NB -> B     │   NB            NB: 1                   N: 1, B: 1
BB -> N     │   NBB           NB: 1, BB: 1            N: 1, B: 2
BN -> B     │   NBBNB         NB: 2. BB: 1, BN: 1     N: 2, B: 3
            │   NBBNBBNBB     NB: 3, BB: 3, BN: 2     N: 3, B: 6    
            
NB -> B     │   N       B     NB: 1                   N: 1, B: 1
BB -> N     │   N   B   B     NB: 1, BB: 1            N: 1, B: 2
BN -> B     │   N B B N B     NB: 2. BB: 1, BN: 1     N: 2, B: 3
            │   NBBNBBNBB     NB: 3, BB: 3, BN: 2     N: 3, B: 6               
```

Also, to optimize performance in part two, a simple hash technique is used for the representation of letters and pairs. So we finally come down below 10 milliseconds.

---

![AoC 2021 Day 14](../day14--Extended_Polymerization.png?raw=true)
