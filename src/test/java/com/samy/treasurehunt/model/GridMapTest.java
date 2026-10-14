package com.samy.treasurehunt.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void shouldCheckIfPositionIsInsideMap() throws IOException {
        Path filePath = tempDirectory.resolve("inside-test-map.txt");

        Files.writeString(filePath, """
            123
            456
            789
            """);

        GridMap map = new GridMap(filePath);

        assertTrue(map.isInside(new Position(0, 0)));
        assertTrue(map.isInside(new Position(2, 2)));

        assertFalse(map.isInside(new Position(-1, 0)));
        assertFalse(map.isInside(new Position(0, -1)));
        assertFalse(map.isInside(new Position(3, 0)));
        assertFalse(map.isInside(new Position(0, 3)));
    }

    @Test
    void shouldDetectObstacles() throws IOException {
        Path filePath = tempDirectory.resolve("obstacle-test-map.txt");

        Files.writeString(filePath, """
            123
            4W6
            P8$
            """);

        GridMap map = new GridMap(filePath);

        assertTrue(map.isObstacle(new Position(1, 1)));
        assertTrue(map.isObstacle(new Position(2, 0)));

        assertFalse(map.isObstacle(new Position(0, 0)));
        assertFalse(map.isObstacle(new Position(2, 2)));
        assertFalse(map.isObstacle(new Position(-1, 0)));
    }

    @Test
    void shouldCheckIfPositionIsAccessible() throws IOException {
        Path filePath = tempDirectory.resolve("accessible-test-map.txt");

        Files.writeString(filePath, """
            123
            4W6
            P8$
            """);

        GridMap map = new GridMap(filePath);

        assertTrue(map.isAccessible(new Position(0, 0)));
        assertTrue(map.isAccessible(new Position(2, 2)));

        assertFalse(map.isAccessible(new Position(1, 1)));
        assertFalse(map.isAccessible(new Position(2, 0)));
        assertFalse(map.isAccessible(new Position(-1, 0)));
        assertFalse(map.isAccessible(new Position(3, 0)));
    }


    @Test
    void shouldReturnMovementCost() throws IOException {
        Path filePath = tempDirectory.resolve("cost-test-map.txt");

        Files.writeString(filePath, """
            123
            4W6
            P8$
            """);

        GridMap map = new GridMap(filePath);

        assertEquals(1, map.getMovementCost(new Position(0, 0)));
        assertEquals(8, map.getMovementCost(new Position(2, 1)));
        assertEquals(1, map.getMovementCost(new Position(2, 2)));

        assertThrows(
                IllegalArgumentException.class,
                () -> map.getMovementCost(new Position(1, 1))
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> map.getMovementCost(new Position(-1, 0))
        );
    }


}