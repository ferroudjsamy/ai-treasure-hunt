package com.samy.treasurehunt.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GridMap {
    private final char [][] grid;

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
}
