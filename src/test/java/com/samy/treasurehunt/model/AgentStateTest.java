package com.samy.treasurehunt.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentStateTest {

    @Test
    void shouldCheckIfTreasureIsCollected() {
        AgentState state = new AgentState(
                new Position(2, 3),
                0b0101
        );

        assertTrue(state.isTreasureCollected(0));
        assertFalse(state.isTreasureCollected(1));
        assertTrue(state.isTreasureCollected(2));
        assertFalse(state.isTreasureCollected(3));
    }

    @Test
    void shouldCollectTreasure() {
        AgentState initialState = new AgentState(
                new Position(2, 3),
                0
        );

        AgentState newState = initialState.collectTreasure(2);

        assertFalse(initialState.isTreasureCollected(2));
        assertTrue(newState.isTreasureCollected(2));
        assertEquals(initialState.position(), newState.position());
    }

    @Test
    void shouldCheckIfAllTreasuresAreCollected() {
        AgentState state = new AgentState(
                new Position(2, 3),
                0
        );

        state = state.collectTreasure(0);
        state = state.collectTreasure(1);

        assertFalse(state.areAllTreasuresCollected(3));

        state = state.collectTreasure(2);

        assertTrue(state.areAllTreasuresCollected(3));
    }


}