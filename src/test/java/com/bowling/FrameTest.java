package com.bowling;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class FrameTest {
    @Test
    public void testUpperCaseStrikeDetection() {
        Frame strike = new Frame("X");
        assertEquals("Upper Case 'X' not instantiated as strike",
                Frame.Type.Strike, strike.getType());
    }

    @Test
    public void testLowerCaseSpareDetection() {
        Frame strike = new Frame("x");
        assertEquals("Lower Case 'x' not instantiated as strike",
                Frame.Type.Strike, strike.getType());
    }

    @Test
    public void testHitThenSpareDetection() {
        int hit = ThreadLocalRandom.current().nextInt(1, 10);
        String input = String.format("%d/", hit);
        Frame spare = new Frame(input);
        String errorMessage = String.format("Spare after hit ('%s') not instantiated as spare", input);
        assertEquals(errorMessage, Frame.Type.Spare, spare.getType());
    }

    @Test
    public void testMissThenSpareDetection() {
        Frame spare = new Frame("-/");
        assertEquals("Spare after miss ('-/') not instantiated as spare",
                Frame.Type.Spare, spare.getType());
    }

    @Test
    public void testNormalFrameWithNoMissesDetection() {
        int hit1 = ThreadLocalRandom.current().nextInt(1, 10);
        int hit2 = ThreadLocalRandom.current().nextInt(1, 10-hit1);
        String input = String.format("%d%d", hit1, hit2);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "Normal frame with no misses ('%s') not instantiated as normal", input);
        assertEquals(errorMessage, Frame.Type.Normal, normal.getType());
    }

    @Test
    public void testNormalFrameWithOneMissDetection() {
        int hit1 = ThreadLocalRandom.current().nextInt(1, 10);
        String input = String.format("%d-", hit1);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "Normal frame with one miss after a hit ('%s') not instantiated as normal", input);
        assertEquals(errorMessage, Frame.Type.Normal, normal.getType());

        input = String.format("-%d", hit1);
        normal = new Frame(input);
        errorMessage = String.format(
                "Normal frame with one miss before a hit ('%s') not instantiated as normal", input);
        assertEquals(errorMessage, Frame.Type.Normal, normal.getType());
    }

    @Test
    public void testNormalFrameWithTwoMissesDetection() {
        Frame normal = new Frame("--");
        assertEquals("Normal frame with two misses ('--') not instantiated as normal",
                Frame.Type.Normal, normal.getType());
    }
}
