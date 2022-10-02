## [AoC 2021 Day 18: Snailfish](https://adventofcode.com/2021/day/18)

For a first attempt, a StringBuilder was chosen. However, it turned out that this approach is not optimal for the job. A runtime of about 150 milliseconds for part one and 600 milliseconds for part two is not convincing. One could get a little more out of it with some tricks and short circuits, but these micro optimizations would be in the single digit millisecond range and on top traded for readability. For historical reasons, Santa wanted the legacy code to be preserved:

<details>
    <summary>StringBuilder Approach</summary>

```Java
public class Challenge implements Solvable {

    private final Pattern regularLeftPAT = Pattern.compile("^.*?(\\d+)(?:\\D*)$");
    private final Pattern regularRightPAT = Pattern.compile("^.*?(\\d+).*$");
    private final Pattern splitPAT = Pattern.compile("(\\d{2,})");
    private final Pattern pairPAT = Pattern.compile("(\\[\\d+,\\d+\\])");
    private final Pattern digitsOnlyPAT = Pattern.compile("\\d+");

    private final List<String> numbers;

    public Challenge(List<String> numbers) {
        this.numbers = numbers;
    }

    @Override
    public long solvePart1() {
        StringBuilder basis = new StringBuilder(numbers.get(0));

        for (int i = 1; i < numbers.size(); i++) {
            add(basis, numbers.get(i));
            reduce(basis);
        }

        return computeMagnitude(basis);
    }

    @Override
    public long solvePart2() {
        long magnitude = 0;
        StringBuilder basis;

        for (int i = 0; i < numbers.size() - 1; i++) {
            for (int j = i + 1; j < numbers.size(); j++) {

                basis = new StringBuilder(numbers.get(i));
                add(basis, numbers.get(j));
                reduce(basis);
                magnitude = Math.max(computeMagnitude(basis), magnitude);

                basis = new StringBuilder(numbers.get(j));
                add(basis, numbers.get(i));
                reduce(basis);
                magnitude = Math.max(computeMagnitude(basis), magnitude);
            }
        }

        return magnitude;
    }

    private void add(StringBuilder basis, String addition) {
        basis.insert(0, "[").append(",").append(addition).append("]");
    }

    private void reduce(StringBuilder number) {
        for (boolean modified = true; modified; modified = trySplit(number))
            while (modified) modified = tryExplode(number);
    }

    private boolean tryExplode(StringBuilder number) {
        int depthCtr = 0, cursor = 0;

        while (depthCtr < 5 && cursor < number.length()) {
            if (number.charAt(cursor) == '[') depthCtr++;
            if (number.charAt(cursor) == ']') depthCtr--;
            cursor++;
        }

        if (depthCtr < 5) return false;

        int indexLeft = cursor;
        while (number.charAt(cursor) != ']') cursor++;
        int indexRight = cursor;

        String[] pair = number.substring(indexLeft, indexRight).split(",");
        int valueLeft = Integer.parseInt(pair[0]);
        int valueRight = Integer.parseInt(pair[1]);

        Matcher matcherRight = regularRightPAT.matcher(number).region(indexRight + 1, number.length());
        if (matcherRight.find())
            number.replace(
                    matcherRight.start(1),
                    matcherRight.end(1),
                    String.valueOf(Integer.parseInt(matcherRight.group(1)) + valueRight));

        number.replace(indexLeft - 1, indexRight + 1, "0");

        Matcher matcherLeft = regularLeftPAT.matcher(number).region(0, indexLeft - 1);
        if (matcherLeft.find())
            number.replace(
                    matcherLeft.start(1),
                    matcherLeft.end(1),
                    String.valueOf(Integer.parseInt(matcherLeft.group(1)) + valueLeft));

        return true;
    }

    private boolean trySplit(StringBuilder number) {
        Matcher matcher = splitPAT.matcher(number);
        if (!matcher.find()) return false;

        int regular = Integer.parseInt(matcher.group(1));
        int valueLeft = regular / 2;
        int valueRight = (int) Math.ceil(regular / 2.0);

        String insert = String.format("[%s,%s]", valueLeft, valueRight);
        number.replace(matcher.start(1), matcher.end(1), insert);

        return true;
    }

    private long computeMagnitude(StringBuilder number) {
        if (digitsOnlyPAT.matcher(number).matches()) return Long.parseLong(number.toString());

        Matcher matcher = pairPAT.matcher(number);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String[] regulars = matcher.group(1).substring(1, matcher.group(1).length() - 1).split(",");
            long mag = Long.parseLong(regulars[0]) * 3 + Long.parseLong(regulars[1]) * 2;
            matcher.appendReplacement(sb, String.valueOf(mag));
        }

        matcher.appendTail(sb);
        return computeMagnitude(sb);
    }
}
```
</details>

So in a second attempt a LinkedList was used. Due to the specifications, it is most performant to drive the Iterator wildly through the neighborhood, just like Santa flies his sled. Nevertheless, it was tried to keep the code as clear as possible and also ornamented with a few comments. One could write a custom LinkedList, but that would just move the wild part of the code elsewhere unnecessarily, without any additional value. Finally, this version achieves a runtime of 30 milliseconds for part one and 150 milliseconds for part two. Let the skids glow.

---

![AoC 2021 Day 18](../day18--Snailfish.png?raw=true)
