package com.samy.treasurehunt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {

    private Position position;

    @BeforeEach
    void setUp() {
        position = new Position(3, 4);
    }

    @Test
    void shouldMoveUp() {
        Position result = position.move(Direction.UP);

        assertEquals(new Position(2, 4), result);
    }

    @Test
    void shouldMoveRight() {
        Position result = position.move(Direction.RIGHT);

        assertEquals(new Position(3, 5), result);
    }

    @Test
    void shouldMoveDown() {
        Position result = position.move(Direction.DOWN);

        assertEquals(new Position(4, 4), result);
    }

    @Test
    void shouldMoveLeft() {
        Position result = position.move(Direction.LEFT);

        assertEquals(new Position(3, 3), result);
    }



}