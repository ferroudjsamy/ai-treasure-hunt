package com.samy.treasurehunt;

import com.samy.treasurehunt.model.GridMap;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Path filePath = Path.of(
                "src",
                "main",
                "resources",
                "maps",
                "test1.txt"
        );

        try {
            GridMap map = new GridMap(filePath);
            map.display();
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Unable to load the map: " + e.getMessage());
        }
    }
}