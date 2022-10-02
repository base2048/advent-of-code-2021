package day09.code;

import utils.*;
import utils.Timer.*;

import static utils.Part.*;

public class Main {

    private static final String title = "AoC 2021 Day 9: Smoke Basin";

    public static void main(String[] args) {

        Preparation prep = Timer.prepareChallenge(Challenge.class, Parser.class);

        Summary part1 = Timer.solveChallenge(prep.challenge, PART1);    // 425
        Summary part2 = Timer.solveChallenge(prep.challenge, PART2);    // 1135260

        Presenter.displayResults(prep, part1, part2, title);

    }
}
