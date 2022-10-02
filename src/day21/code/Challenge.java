package day21.code;

import utils.Solvable;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.*;

public class Challenge implements Solvable {

    private final int PLAYER_1 = 0;
    private final int PLAYER_2 = 1;
    private final int MOD_OFFSET = 1;

    private final Map<Integer, Integer> diceSumToFrequency;
    private final Map<Integer, long[]> cache = new HashMap<>();
    private final int player1StartingPos, player2StartingPos;

    public Challenge(Parser.Data data) {
        this.player1StartingPos = data.player1StartingPos;
        this.player2StartingPos = data.player2StartingPos;
        this.diceSumToFrequency = getDiceSumToFrequency();
    }

    @Override
    public long solvePart1() {
        int player1Pos = player1StartingPos;
        int player2Pos = player2StartingPos;
        int player1Score = 0;
        int player2Score = 0;
        int activePlayer = PLAYER_1;

        int dice = 0;
        int turns = 0;
        int target = 1000;

        while (player1Score < target && player2Score < target) {
            dice = (dice + 3 - MOD_OFFSET) % 100 + MOD_OFFSET;

            if (activePlayer == PLAYER_1) {
                player1Pos = (player1Pos + dice * 3 - 3 - MOD_OFFSET) % 10 + MOD_OFFSET;
                player1Score += player1Pos;
            }

            if (activePlayer == PLAYER_2) {
                player2Pos = (player2Pos + dice * 3 - 3 - MOD_OFFSET) % 10 + MOD_OFFSET;
                player2Score += player2Pos;
            }

            activePlayer ^= 1;
            turns += 3;
        }

        return (long) Math.min(player1Score, player2Score) * turns;
    }

    @Override
    public long solvePart2() {
        int initialGameState = encryptState(player1StartingPos, player2StartingPos, 0, 0, PLAYER_1);

        long[] wins = rollDice(initialGameState);
        return Math.max(wins[0], wins[1]);
    }

    private long[] rollDice(int gameState) {
        if (cache.containsKey(gameState)) return cache.get(gameState);

        int player1Pos = gameState >>> 28;
        int player2Pos = gameState >>> 24 & 0xF;
        int player1Score = gameState >>> 16 & 0xFF;
        int player2Score = gameState >>> 8 & 0xFF;
        int activePlayer = gameState & 1;

        if (player1Score >= 21) return cache.merge(gameState, new long[]{1, 0}, (a, b) -> b);
        if (player2Score >= 21) return cache.merge(gameState, new long[]{0, 1}, (a, b) -> b);

        long[] totalWins = new long[2];

        for (Map.Entry<Integer, Integer> roll : diceSumToFrequency.entrySet()) {
            long[] wins = new long[2];

            if (activePlayer == PLAYER_1) {
                int nextPos = (player1Pos + roll.getKey() - MOD_OFFSET) % 10 + MOD_OFFSET;
                int nextScore = player1Score + nextPos;
                int nextGameState = encryptState(nextPos, player2Pos, nextScore, player2Score, PLAYER_2);
                wins = rollDice(nextGameState);
            }

            if (activePlayer == PLAYER_2) {
                int nextPos = (player2Pos + roll.getKey() - MOD_OFFSET) % 10 + MOD_OFFSET;
                int nextScore = player2Score + nextPos;
                int nextGameState = encryptState(player1Pos, nextPos, player1Score, nextScore, PLAYER_1);
                wins = rollDice(nextGameState);
            }

            totalWins[0] += roll.getValue() * wins[0];
            totalWins[1] += roll.getValue() * wins[1];
        }

        return cache.merge(gameState, totalWins, (a, b) -> b);
    }

    private int encryptState(int player1Pos, int player2Pos, int player1Score, int player2Score, int activePlayer) {
        return player1Pos << 28 | player2Pos << 24 | player1Score << 16 | player2Score << 8 | activePlayer;
    }

    private Map<Integer, Integer> getDiceSumToFrequency() {
        Supplier<Integer> diceSums = new Supplier<>() {
            final int[] sums = new int[27];
            int pointer = 0;

            {
                for (int i = 1; i <= 3; i++)
                    for (int j = 1; j <= 3; j++)
                        for (int k = 1; k <= 3; k++)
                            sums[pointer++] = i + j + k;

                pointer = 0;
            }

            @Override
            public Integer get() {
                return pointer < sums.length ? sums[pointer++] : null;
            }
        };

        return Stream.generate(diceSums)
                .takeWhile(Objects::nonNull)
                .collect(Collectors.groupingBy(diceSum -> diceSum, Collectors.summingInt($ -> 1)));
    }
}
