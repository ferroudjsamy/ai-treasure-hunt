package com.samy.treasurehunt.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridMap {
    private final char [][] grid;

    private final Map<Position, Integer> treasureNumbers = new HashMap<>();

    public GridMap(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        int size = lines.size();

        if (size == 0) {
            throw new IllegalArgumentException("The map cannot be empty");
        }

        grid = new char[size][size];
        for (int i = 0; i < size; i++) {
            String ligne = lines.get(i);

            if (ligne.length() != size) {
                throw new IllegalArgumentException("The map must be square");
            }

            for (int j = 0; j < size; j++) {
                grid[i][j] = ligne.charAt(j);
            }
        }

        numberTreasures();

    }

    private void numberTreasures() {
        int treasureNumber = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '$') {
                    Position position = new Position(i, j);
                    treasureNumbers.put(position, treasureNumber);
                    treasureNumber++;
                }
            }
        }
    }

    public void display() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
            }

            System.out.println();
        }
    }

    public int getSize() {
        return grid.length;
    }

    public char getCell(int i, int j) {
        return grid[i][j];
    }

    public boolean isInside(Position position) {
        return position.row() >= 0
                && position.row() < grid.length
                && position.column() >= 0
                && position.column() < grid.length;
    }

    public boolean isObstacle(Position position) {
        if (!isInside(position)) {
            return false;
        }

        char cell = grid[position.row()][position.column()];

        return cell == 'W' || cell == 'P';
    }

    public boolean isAccessible(Position position) {
        return isInside(position) && !isObstacle(position);
    }

    public int getMovementCost(Position position) {
        if (!isAccessible(position)) {
            throw new IllegalArgumentException(
                    "Cannot calculate the cost of an inaccessible position"
            );
        }

        char cell = grid[position.row()][position.column()];

        if (Character.isDigit(cell)) {
            return Character.getNumericValue(cell);
        }

        if (cell == '$') {
            return 1;
        }

        throw new IllegalArgumentException(
                "Unknown cell type: " + cell
        );
    }


    public boolean containsTreasure(Position position) {
        return isInside(position)
                && grid[position.row()][position.column()] == '$';
    }

    public int getTreasureNumber(Position position) {
        Integer treasureNumber = treasureNumbers.get(position);

        if (treasureNumber == null) {
            throw new IllegalArgumentException(
                    "There is no treasure at this position"
            );
        }

        return treasureNumber;
    }

    public int getTreasureCount() {
        return treasureNumbers.size();
    }
}
