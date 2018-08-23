package com.bowling;

public class Frame {
    private final Type type;
    private int points;

    public Frame(String frame) {
        type = Type.getType(frame);
    }

    public Type getType() {
        return type;
    }

    public int getPoints() {
        return points;
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
