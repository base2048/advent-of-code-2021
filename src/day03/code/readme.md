## [AoC 2021 Day 3: Binary Diagnostic](https://adventofcode.com/2021/day/3)

Now that the submarine's vitality values have been clarified, it is only logical to quantify the supply of cookies as well. Let's see how the elves have stocked the galley.

Fortunately, this is quite easy to determine. Each active bit in the diagnostic report represents the existence of one cookie. Non-active bits are the result of more or less voluntarily dis-incarnated cookies (cookies generally don't have too much say in this matter).

```Java
int cookieCount =  Arrays.stream(bitCountByColumn).sum();
```

A submarine-wide cookie analysis on day three shows 6,004 cookies left. We'll see how long that lasts.

---

![AoC 2021 Day 3](../day03--Binary_Diagnostic.png?raw=true)
