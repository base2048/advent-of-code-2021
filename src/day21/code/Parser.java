package day21.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser extends AbstractParser {

    static class Data {
        int player1StartingPos, player2StartingPos;

        Data(int player1StartingPos, int player2StartingPos) {
            this.player1StartingPos = player1StartingPos;
            this.player2StartingPos = player2StartingPos;
        }
    }

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {

            /* Quite astonishing, right? */
            int player1StartingPos = scn.nextLine().chars().reduce(0, (acc, cur) -> cur - '0');
            int player2StartingPos = scn.nextLine().chars().reduce(0, (acc, cur) -> cur - '0');

            return (T) new Data(player1StartingPos, player2StartingPos);
        }
    }
}
