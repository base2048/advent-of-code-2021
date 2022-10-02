## [AoC 2021 Day 13: Transparent Origami](https://adventofcode.com/2021/day/13)

In order to store the individual dots on the paper, a Map was chosen whose keys indicate the rows and the values contain a List with all x-coordinates of the dots on this specific row. By doing so, we can rearrange whole rows at once and do not have to recalculate every single dot in case of a fold-y instruction. Of course, we could also store the dots as Points in a Set, but to be honest, that would be too trivial and would take the fun out of coding.

However, since working with Streams can get pretty fancy pretty fast, a compromise was finally made between modern code and performance. For example, the following code for the fold-y instruction was not kept because it triples the runtime:

```Java
void foldY(int pivot) {
    dotsByRow = dotsByRow.entrySet().stream()
            .collect(Collectors.groupingBy(
                    entry -> entry.getKey() > pivot ? (pivot << 1) - entry.getKey() : entry.getKey(),
                    Collectors.collectingAndThen(Collectors.toList(), list ->
                            list.stream().map(Map.Entry::getValue).flatMap(Collection::stream).collect(Collectors.toSet()))));

    height = pivot - 1;
}
```

Another approach would be to write an own BitMap Class with an underlying long[][] Array. To be honest, I was already halfway to a working solution, but in the end it was too much mathematical gibberish that would remain somewhat incomprehensible for a long time without a fair amount of additional comments.

One more note: since the paper will be altered while we execute the instructions, we have to consider the order in which we call the two parts of the challenge. Restoring the paper to its initial state after processing a single part would be possible. Anyhow, I didn't want to add additional code just for this. The solution to complete both parts correctly, is to run part one first and then part two. The current implementation would also allow to run only part one or only part two.

---

![AoC 2021 Day 13](../day13--Transparent_Origami.png?raw=true)
