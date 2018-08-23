package com.bowling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Game {

    public static void main(String[] args) {
        System.out.println("----------BOWLING GAME SCORE CALCULATOR----------");
        System.out.println("Enter a 10 frame game in the format '5- 8/ 4- -/ -6 X 43 X -/ 4/7'\n" +
            "Spaces separate frames, '-' represents a miss, '/' a spare and 'X' a strike:");

        String input;
        try (Scanner reader = new Scanner(System.in)) {
            input = reader.nextLine().trim();
        }
        int score = calculateScore(input);

        System.out.println(String.format("You scored %d points", score));
    }

    public static int calculateScore(String line) {
        int score = 0;
        int frameNumber = 0;

        List<Frame> frames = parseGameInput(line);
        for (Frame frame : frames) {
            switch (frame.getType()) {
                case NORMAL:
                    score += frame.getPoints();
                    break;
                case SPARE:
                    score += frame.getPoints() + spareBonus(frames, frameNumber);
                    break;
                case STRIKE:
                    score += frame.getPoints() + strikeBonus(frames, frameNumber);
                    break;
            }
            if (++frameNumber >= 10) break;
        }

        return score;
    }

    private static int strikeBonus(List<Frame> frames, int frameNumber) {
        Frame next = frames.get(frameNumber+1);

        if (next.getType() == Frame.Type.STRIKE) {
            return next.getPoints() + frames.get(frameNumber+2).getFirstRoll();
        } else {
            return next.getPoints();
        }
    }

    private static int spareBonus(List<Frame> frames, int frameNumber) {
        return frames.get(frameNumber+1).getFirstRoll();
    }

    private static List<Frame> parseGameInput(String line) {
        List<String> frameStringRepresentations = new ArrayList<>(Arrays.asList(line.split(" ")));

        String lastFrameString = frameStringRepresentations.get(frameStringRepresentations.size()-1);
        if (containsBonusThrows(lastFrameString)) {
            List<String> bonusFrameStrings = separateBonusFrame(lastFrameString);
            frameStringRepresentations.remove(lastFrameString);
            frameStringRepresentations.addAll(bonusFrameStrings);
        }

        return frameStringRepresentations.stream().map(Frame::new).collect(Collectors.toList());
    }

    private static List<String> separateBonusFrame(String last) {
        String roll1 = last.substring(0,1);
        String roll2 = last.substring(1, 2);
        String roll3 = last.substring(2);

        List<String> separatedFrameStrings = new ArrayList<>();

        if (Frame.Type.isStrike(roll1)) {
            separatedFrameStrings.add(roll1);
            if (Frame.Type.isStrike(roll2)) {
                separatedFrameStrings.add(roll2);
                separatedFrameStrings.add(roll3);
            } else {
                separatedFrameStrings.add(roll2 + roll3);
            }
        } else if (Frame.Type.isSpare(roll2)){
            separatedFrameStrings.add(roll1 + roll2);
            separatedFrameStrings.add(roll3);
        }

        return separatedFrameStrings;
    }

    private static boolean containsBonusThrows(String frame) {
        return frame.length() > 2;
    }
}
