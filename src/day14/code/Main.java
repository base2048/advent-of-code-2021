package day14.code;

import utils.*;
import utils.Timer.*;

import static utils.Part.*;

public class Main {

    private static final String title = "AoC 2021 Day 14: Extended Polymerization";

    public static void main(String[] args) {

        Preparation prep = Timer.prepareChallenge(Challenge.class, Parser.class);

        Summary part1 = Timer.solveChallenge(prep.challenge, PART1);    // 5656
        Summary part2 = Timer.solveChallenge(prep.challenge, PART2);    // 12271437788530

        Presenter.displayResults(prep, part1, part2, title);

    }
}
