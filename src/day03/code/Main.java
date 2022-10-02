package day03.code;

import utils.*;
import utils.Timer.*;

import static utils.Part.*;

public class Main {

    private static final String title = "AoC 2021 Day 3: Binary Diagnostic";

    public static void main(String[] args) {

        Preparation prep = Timer.prepareChallenge(Challenge.class, Parser.class);

        Summary part1 = Timer.solveChallenge(prep.challenge, PART1);    // 3320834
        Summary part2 = Timer.solveChallenge(prep.challenge, PART2);    // 4481199

        Presenter.displayResults(prep, part1, part2, title);

    }
}
