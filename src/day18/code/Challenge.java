package day18.code;

import utils.Solvable;

import java.util.*;

public class Challenge implements Solvable {

    private final int openingBracket = ('[' - '0') * -1;
    private final int closingBracket = (']' - '0') * -1;
    private final int comma = ',' - '0';    /* is already negative */

    private final List<String> numbers;

    public Challenge(List<String> numbers) {
        this.numbers = numbers;
    }

    @Override
    public long solvePart1() {
        LinkedList<Integer> base = parseNumber(numbers.get(0));

        for (int i = 1; i < numbers.size(); i++) {
            add(base, parseNumber(numbers.get(i)));
            reduce(base);
        }

        return computeMagnitude(base);
    }

    @Override
    public long solvePart2() {
        long magnitude = 0;
        LinkedList<Integer> base;

        for (int i = 0; i < numbers.size() - 1; i++) {
            for (int j = i + 1; j < numbers.size(); j++) {

                base = parseNumber(numbers.get(i));
                add(base, parseNumber(numbers.get(j)));
                reduce(base);
                magnitude = Math.max(computeMagnitude(base), magnitude);

                base = parseNumber(numbers.get(j));
                add(base, parseNumber(numbers.get(i)));
                reduce(base);
                magnitude = Math.max(computeMagnitude(base), magnitude);
            }
        }

        return magnitude;
    }

    private void add(LinkedList<Integer> base, LinkedList<Integer> addition) {
        base.addFirst(openingBracket);
        base.addLast(comma);
        base.addAll(addition);
        base.addLast(closingBracket);
    }

    private void reduce(LinkedList<Integer> number) {
        for (boolean modified = true; modified; modified = trySplit(number))
            while (modified) modified = tryExplode(number);
    }

    private boolean tryExplode(LinkedList<Integer> number) {
        int depthCtr = 0;
        ListIterator<Integer> it = number.listIterator();

        while (depthCtr < 5 && it.hasNext()) {
            int value = it.next();
            if (value == openingBracket) depthCtr++;
            if (value == closingBracket) depthCtr--;
        }

        if (depthCtr < 5) return false;

        it.remove();    // [
        int valueLeft = it.next();
        it.remove();    // left regular
        it.next();
        it.remove();    // ,
        int valueRight = it.next();
        it.remove();    // right regular
        it.next();
        it.set(-99);    // ] -> placeholder

        while (it.hasPrevious()) {
            int value = it.previous();
            if (value < 0) continue;

            it.set(value + valueLeft);
            it.next();  /* position cursor for further processing */
            break;
        }

        while (it.hasNext()) {
            int value = it.next();
            if (value == -99)
                it.set(0);

            if (value >= 0) {
                it.set(value + valueRight);
                break;
            }
        }

        return true;
    }

    private boolean trySplit(LinkedList<Integer> number) {
        for (ListIterator<Integer> it = number.listIterator(); it.hasNext(); ) {
            int value = it.next();
            if (value < 10) continue;

            int valueLeft = value / 2;
            int valueRight = (int) Math.ceil(value / 2.0);

            it.set(openingBracket);
            it.add(valueLeft);
            it.add(comma);
            it.add(valueRight);
            it.add(closingBracket);

            return true;
        }
        return false;
    }

    private long computeMagnitude(LinkedList<Integer> number) {
        if (number.size() == 1) return number.peek();

        ListIterator<Integer> it = number.listIterator();

        while (it.hasNext()) {
            int value = it.next();

            if (value >= 0 && it.next() == comma && it.next() >= 0) {
                int valueRight = it.previous() * 2;
                it.remove();    // right regular
                it.previous();
                it.remove();    // ,
                int valueLeft = it.previous() * 3;
                it.remove();    // left regular
                it.previous();
                it.remove();    // [
                it.next();
                it.set(valueLeft + valueRight);    // ] -> magnitude
            }
        }

        return computeMagnitude(number);
    }

    private LinkedList<Integer> parseNumber(String number) {
        return number.chars()
                .map(ch -> ch - '0')
                .map(ch -> ch > 9 ? ch *= -1 : ch)
                .collect(LinkedList::new, LinkedList::addLast, LinkedList::addAll);
    }
}
