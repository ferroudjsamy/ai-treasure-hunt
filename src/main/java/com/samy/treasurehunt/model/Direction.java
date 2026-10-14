package com.samy.treasurehunt.model;

public enum Direction {

    UP(-1, 0, 'h'),
    RIGHT(0, 1, 'd'),
    DOWN(1, 0, 'b'),
    LEFT(0, -1, 'g');

    private final int rowChange;
    private final int columnChange;
    private final char action;

    Direction(int rowChange, int columnChange, char action) {
        this.rowChange = rowChange;
        this.columnChange = columnChange;
        this.action = action;
    }

    public int getRowChange() {
        return rowChange;
    }

    public int getColumnChange() {
        return columnChange;
    }

    public char getAction() {
        return action;
    }
}