package com.bowling;

public class Frame {
    private final Type type;
    private int points = -1;
    private int roll1 = 0;
    private int roll2 = 0;

    public Frame(String frame) {
        this.type = Type.getType(frame);
        switch (this.type) {
            case STRIKE:
                this.roll1 = 10;
                break;
            case SPARE:
                this.roll1 = parsePointsFromRoll(frame.substring(0, 1));
                this.roll2 = 10 - this.roll1;
                break;
            case NORMAL:
                this.roll1 = parsePointsFromRoll(frame.substring(0, 1));
                if (frame.length() > 1) this.roll2 = parsePointsFromRoll(frame.substring(1));
                break;
        }
    }

    private int parsePointsFromRoll(String roll) {
        if ("-".equals(roll)) {
            return 0;
        } else {
            return Integer.valueOf(roll);
        }
    }

    public Type getType() {
        return this.type;
    }

    public int getPoints() {
        if (this.points != -1) {
            return this.points;
        }
        switch (this.type) {
            case STRIKE:
            case SPARE:
                this.points = 10;
                break;
            case NORMAL:
                this.points = this.roll1 + this.roll2;
                break;
        }
        return points;
    }

    public int getFirstRoll() {
        return this.roll1;
    }

    public int getSecondRoll() {
        return this.roll2;
    }

    public enum Type {
        STRIKE, SPARE, NORMAL;

        private static Type getType(String frame) {
            if (isStrike(frame)) {
                return STRIKE;
            } else if (isSpare(frame)) {
                return SPARE;
            } else {
                return NORMAL;
            }
        }

        public static boolean isStrike(String frame) {
            return "x".equalsIgnoreCase(frame);
        }

        public static boolean isSpare(String frame) {
            return frame.endsWith("/");
        }
    }
}
