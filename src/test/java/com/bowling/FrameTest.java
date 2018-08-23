package com.bowling;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class FrameTest {
    @Test
    public void testUpperCaseStrikeDetection() {
        Frame strike = new Frame("X");
        assertEquals("Upper Case 'X' not instantiated as strike",
                Frame.Type.STRIKE, strike.getType());
    }

    @Test
    public void testLowerCaseStrikeDetection() {
        Frame strike = new Frame("x");
        assertEquals("Lower Case 'x' not instantiated as strike",
                Frame.Type.STRIKE, strike.getType());
    }

    @Test
    public void testStrikePoints() {
        Frame strike = new Frame("x");
        assertEquals("Strike points were not calculated properly", 10, strike.getPoints());
    }

    @Test
    public void testHitThenSpareDetection() {
        int hit = ThreadLocalRandom.current().nextInt(1, 10);
        String input = String.format("%d/", hit);
        Frame spare = new Frame(input);
        String errorMessage = String.format("Spare after hit ('%s') not instantiated as spare", input);
        assertEquals(errorMessage, Frame.Type.SPARE, spare.getType());
    }

    @Test
    public void testSparePointsWithHit() {
        int hit = ThreadLocalRandom.current().nextInt(1, 10);
        String input = String.format("%d/", hit);
        Frame spare = new Frame(input);
        String errorMessage = String.format("Spare after hit ('%s') points not calculated properly", input);
        assertEquals(errorMessage, 10, spare.getPoints());
    }

    @Test
    public void testMissThenSpareDetection() {
        Frame spare = new Frame("-/");
        assertEquals("Spare after miss ('-/') not instantiated as spare",
                Frame.Type.SPARE, spare.getType());
    }

    @Test
    public void testSparePointsWithMiss() {
        Frame spare = new Frame("-/");
        assertEquals("Spare after miss ('-/') points not calculated properly",
                10, spare.getPoints());
    }

    @Test
    public void testNormalFrameWithNoMissesDetection() {
        int hit1 = ThreadLocalRandom.current().nextInt(1, 10);
        int hit2 = ThreadLocalRandom.current().nextInt(1, 10-hit1);
        String input = String.format("%d%d", hit1, hit2);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "Normal frame with no misses ('%s') not instantiated as normal", input);
        assertEquals(errorMessage, Frame.Type.NORMAL, normal.getType());
    }

    @Test
    public void testNormalFrameWithOneMissDetection() {
        int hit = ThreadLocalRandom.current().nextInt(1, 10);
        String input = String.format("%d-", hit);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "Normal frame with one miss after a hit ('%s') not instantiated as normal", input);
        assertEquals(errorMessage, Frame.Type.NORMAL, normal.getType());

        input = String.format("-%d", hit);
        normal = new Frame(input);
        errorMessage = String.format(
                "Normal frame with one miss before a hit ('%s') not instantiated as normal", input);
        assertEquals(errorMessage, Frame.Type.NORMAL, normal.getType());
    }

    @Test
    public void testNormalFrameWithTwoMissesDetection() {
        Frame normal = new Frame("--");
        assertEquals("Normal frame with two misses ('--') not instantiated as normal",
                Frame.Type.NORMAL, normal.getType());
    }

    @Test
    public void testNormalFramePointsWithNoMisses() {
        int hit1 = ThreadLocalRandom.current().nextInt(1, 10);
        int hit2 = ThreadLocalRandom.current().nextInt(1, 10-hit1);
        String input = String.format("%d%d", hit1, hit2);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "Normal frame with no misses ('%s') not calculated properly", input);
        assertEquals(errorMessage, hit1+hit2, normal.getPoints());
    }

    @Test
    public void testNormalFramePointsWithOneMiss() {
        int hit = ThreadLocalRandom.current().nextInt(1, 10);
        String input = String.format("%d-", hit);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "Normal frame with one miss after a hit ('%s') not calculated properly", input);
        assertEquals(errorMessage, hit, normal.getPoints());

        input = String.format("-%d", hit);
        normal = new Frame(input);
        errorMessage = String.format(
                "Normal frame with one miss before a hit ('%s') not calculated properly", input);
        assertEquals(errorMessage, hit, normal.getPoints());
    }

    @Test
    public void testNormalFramePointsWithTwoMisses() {
        Frame normal = new Frame("--");
        assertEquals("Normal frame with two misses ('--') not calculated properly",
                0, normal.getPoints());
    }

    @Test
    public void testGetFirstHitWithNoMiss() {
        int hit1 = ThreadLocalRandom.current().nextInt(1, 10);
        int hit2 = ThreadLocalRandom.current().nextInt(1, 10-hit1);
        String input = String.format("%d%d", hit1, hit2);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "Normal frame with no misses ('%s') first hit not parsed correctly", input);
        assertEquals(errorMessage, hit1, normal.getFirstHitPoints());
    }

    @Test
    public void testGetFirstHitWithOneMiss() {
        int hit = ThreadLocalRandom.current().nextInt(1, 10);
        String input = String.format("%d-", hit);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "Normal frame with one miss ('%s') first hit not parsed correctly", input);
        assertEquals(errorMessage, hit, normal.getFirstHitPoints());
    }

    @Test
    public void testGetFirstHitWithStrike() {
        int hit = ThreadLocalRandom.current().nextInt(1, 10);
        String input = String.format("%d/", hit);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "Normal frame with strike ('%s') first hit not parsed correctly", input);
        assertEquals(errorMessage, hit, normal.getFirstHitPoints());
    }

    @Test
    public void testGetSecondHitWithNoMiss() {
        int hit1 = ThreadLocalRandom.current().nextInt(1, 10);
        int hit2 = ThreadLocalRandom.current().nextInt(1, 10-hit1);
        String input = String.format("%d%d", hit1, hit2);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "Normal frame with no misses ('%s') second hit not parsed correctly", input);
        assertEquals(errorMessage, hit2, normal.getSecondHitPoints());
    }

    @Test
    public void testGetSecondHitWithOneMiss() {
        int hit = ThreadLocalRandom.current().nextInt(1, 10);
        String input = String.format("-%d", hit);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "Normal frame with one miss ('%s') second hit not parsed correctly", input);
        assertEquals(errorMessage, hit, normal.getSecondHitPoints());
    }

    @Test
    public void testGetNormalFrameHitsWithTwoMisses() {
        Frame normal = new Frame("--");
        assertEquals("Normal frame with two misses ('--') first hit not parsed correctly",
                0, normal.getFirstHitPoints());
        assertEquals("Normal frame with two misses ('--') second hit not parsed correctly",
                0, normal.getSecondHitPoints());
    }
}
