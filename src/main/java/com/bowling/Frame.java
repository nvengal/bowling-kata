package com.bowling;

public class Frame {
    private final Type type;
    private int points = -1;
    private int hit1 = -1;
    private int hit2 = -1;

    public Frame(String frame) {
        this.type = Type.getType(frame);
        switch (this.type) {
            case STRIKE:
                break;
            case SPARE:
                this.hit1 = parsePointsFromHit(frame.substring(0, 1));
                break;
            case NORMAL:
                this.hit1 = parsePointsFromHit(frame.substring(0, 1));
                this.hit2 = parsePointsFromHit(frame.substring(1));
                break;
        }
    }

    private int parsePointsFromHit(String hit) {
        if ("-".equals(hit)) {
            return 0;
        } else {
            return Integer.valueOf(hit);
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
                this.points = this.hit1 + this.hit2;
                break;
        }
        return points;
    }

    public int getFirstHitPoints() {
        return this.hit1;
    }

    public int getSecondHitPoints() {
        return this.hit2;
    }

    public enum Type {
        STRIKE, SPARE, NORMAL;

        private static Type getType(String frame) {
            if ("x".equalsIgnoreCase(frame)) {
                return STRIKE;
            } else if (frame.endsWith("/")) {
                return SPARE;
            } else {
                return NORMAL;
            }
        }
    }
}
