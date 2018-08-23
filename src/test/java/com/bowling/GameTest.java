package com.bowling;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class GameTest {
    @Test
    public void calculateScoreForGameWithAllMisses() {
        String line = "-- -- -- -- -- -- -- -- -- --";
        int score = Game.calculateScore(line);
        String errorMessage = String.format("Score for line: '%s' was incorrect", line);
        assertEquals(errorMessage, 0, score);
    }

    @Test
    public void calculateScoreForGameWithAllStrikes() {
        String line = "X X X X X X X X X XXX";
        int score = Game.calculateScore(line);
        String errorMessage = String.format("Score for line: '%s' was incorrect", line);
        assertEquals(errorMessage, 300, score);
    }

    @Test
    public void calculateScoreForGameWithAllStrikesAndSpareBonusHits() {
        String line = "X X X X X X X X X X5/";
        int score = Game.calculateScore(line);
        String errorMessage = String.format("Score for line: '%s' was incorrect", line);
        assertEquals(errorMessage, 285, score);
    }

    @Test
    public void calculateScoreForGameWithAllStrikesAndNormalBonusHits() {
        String line = "X X X X X X X X X X54";
        int score = Game.calculateScore(line);
        String errorMessage = String.format("Score for line: '%s' was incorrect", line);
        assertEquals(errorMessage, 284, score);
    }

    @Test
    public void calculateScoreForGameWithAllSparesAndBonusHitNotStrike() {
        String line = "5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5";
        int score = Game.calculateScore(line);
        String errorMessage = String.format("Score for line: '%s' was incorrect", line);
        assertEquals(errorMessage, 150, score);
    }

    @Test
    public void calculateScoreForGameWithAllSparesAndBonusHitStrike() {
        String line = "5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/X";
        int score = Game.calculateScore(line);
        String errorMessage = String.format("Score for line: '%s' was incorrect", line);
        assertEquals(errorMessage, 155, score);
    }

    @Test
    public void calculateScoreForGameWithAllNormalHits() {
        int[] randomFirstHits =
                IntStream.range(0, 10)
                    .map(i -> ThreadLocalRandom.current().nextInt(1, 9)).toArray();
        int[] randomSecondHits =
                IntStream.range(0, 10)
                    .map(i -> ThreadLocalRandom.current().nextInt(1, 10-randomFirstHits[i])).toArray();

        StringBuffer lineBuffer = new StringBuffer();
        IntStream.range(0, 10)
            .forEach(i -> lineBuffer.append(randomFirstHits[i]).append(randomSecondHits[i]).append(" "));
        String line = lineBuffer.toString().trim();

        int score = Game.calculateScore(line);
        int expectedScore = IntStream.of(randomFirstHits).sum() + IntStream.of(randomSecondHits).sum();
        String errorMessage = String.format("Score for line: '%s' was incorrect", line);
        assertEquals(errorMessage, expectedScore, score);
    }

    @Test
    public void calculateScoreForMixedGame() {
        String line = "5- 8/ 4- -/ -6 X 43 X -/ X--";
        int score = Game.calculateScore(line);
        String errorMessage = String.format("Score for line: '%s' was incorrect", line);
        assertEquals(errorMessage, 113, score);
    }
}
