## [AoC 2021 Day 20: Trench Map](https://adventofcode.com/2021/day/20)

This Game of Life variant with a starting pattern of 100 by 100 cells requires a special approach to avoid running into timing problems. So instead of tracking each cell individually, we only store the active ones in the form of a coordinates hash.

Also, we need to keep track of the size of the active field, which we do by adjusting the viewports upper left and lower right corners on each iteration.

And finally we have to take into account that we are dealing with an infinite grid. Depending on the algorithm, it might be necessary to include all cells running to infinity in the calculation. This is handled by toggling the background completely after each iteration, if necessary, and storing this information separately. The option of toggling only once is not considered here.

One last note: in the *enhanceImage()* method we scan the image first over x and then over y, while we traverse the 9 by 9 sector first vertically and then horizontally. Here it has been shown that the chosen variant is more than 100 milliseconds faster than iterating over y first. How Java manages the HashSet in the depths of its implementation I can't say, but it seems that this approach results in better use of the Line Cache. Almost down to half a second, time for some cookies.

---

![AoC 2021 Day 20](../day20--Trench_Map.png?raw=true)
