package com.samy.treasurehunt.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GridMapTest {

    @TempDir
    Path tempDirectory;

    @Test
    void shouldLoadValidSquareMap() throws IOException {
        Path filePath = tempDirectory.resolve("valid-map.txt");

        Files.writeString(filePath, """
                12$
                4W6
                P89
                """);

        GridMap map = new GridMap(filePath);

        assertEquals(3, map.getSize());
        assertEquals('1', map.getCell(0, 0));
        assertEquals('$', map.getCell(0, 2));
        assertEquals('W', map.getCell(1, 1));
        assertEquals('P', map.getCell(2, 0));
    }

    @Test
    void shouldRejectEmptyMap() throws IOException {
        Path filePath = tempDirectory.resolve("empty-map.txt");

        Files.createFile(filePath);

        assertThrows(
                IllegalArgumentException.class,
                () -> new GridMap(filePath)
        );
    }

    @Test
    void shouldRejectNonSquareMap() throws IOException {
        Path filePath = tempDirectory.resolve("non-square-map.txt");

        Files.writeString(filePath, """
                123
                45
                678
                """);

        assertThrows(
                IllegalArgumentException.class,
                () -> new GridMap(filePath)
        );
    }
}