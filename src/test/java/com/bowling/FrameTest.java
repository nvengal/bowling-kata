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
    public void testLowerCaseSPAREDetection() {
        Frame strike = new Frame("x");
        assertEquals("Lower Case 'x' not instantiated as strike",
                Frame.Type.STRIKE, strike.getType());
    }

    @Test
    public void testHitThenSPAREDetection() {
        int hit = ThreadLocalRandom.current().nextInt(1, 10);
        String input = String.format("%d/", hit);
        Frame spare = new Frame(input);
        String errorMessage = String.format("SPARE after hit ('%s') not instantiated as spare", input);
        assertEquals(errorMessage, Frame.Type.SPARE, spare.getType());
    }

    @Test
    public void testMissThenSPAREDetection() {
        Frame spare = new Frame("-/");
        assertEquals("SPARE after miss ('-/') not instantiated as spare",
                Frame.Type.SPARE, spare.getType());
    }

    @Test
    public void testNORMALFrameWithNoMissesDetection() {
        int hit1 = ThreadLocalRandom.current().nextInt(1, 10);
        int hit2 = ThreadLocalRandom.current().nextInt(1, 10-hit1);
        String input = String.format("%d%d", hit1, hit2);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "NORMAL frame with no misses ('%s') not instantiated as normal", input);
        assertEquals(errorMessage, Frame.Type.NORMAL, normal.getType());
    }

    @Test
    public void testNORMALFrameWithOneMissDetection() {
        int hit1 = ThreadLocalRandom.current().nextInt(1, 10);
        String input = String.format("%d-", hit1);
        Frame normal = new Frame(input);
        String errorMessage = String.format(
                "NORMAL frame with one miss after a hit ('%s') not instantiated as normal", input);
        assertEquals(errorMessage, Frame.Type.NORMAL, normal.getType());

        input = String.format("-%d", hit1);
        normal = new Frame(input);
        errorMessage = String.format(
                "NORMAL frame with one miss before a hit ('%s') not instantiated as normal", input);
        assertEquals(errorMessage, Frame.Type.NORMAL, normal.getType());
    }

    @Test
    public void testNORMALFrameWithTwoMissesDetection() {
        Frame normal = new Frame("--");
        assertEquals("NORMAL frame with two misses ('--') not instantiated as normal",
                Frame.Type.NORMAL, normal.getType());
    }
}
