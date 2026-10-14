package com.samy.treasurehunt.model;

public record Position(int row, int column) {

    public Position move(Direction direction) {
        int newRow = row + direction.getRowChange();
        int newColumn = column + direction.getColumnChange();

        return new Position(newRow, newColumn);
    }
}