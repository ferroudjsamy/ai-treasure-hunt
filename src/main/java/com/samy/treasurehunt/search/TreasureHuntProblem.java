package com.samy.treasurehunt.search;

import com.samy.treasurehunt.model.*;

import java.util.ArrayList;
import java.util.List;

public class TreasureHuntProblem {

    private final GridMap map;

    public TreasureHuntProblem(GridMap map) {
        this.map = map;
    }

    private void addMovementSuccessors(
            AgentState state,
            List<Successor> successors
    ) {
        for (Direction direction : Direction.values()) {
            Position newPosition = state.position().move(direction);

            if (map.isAccessible(newPosition)) {
                AgentState newState = new AgentState(
                        newPosition,
                        state.collectedTreasuresMask()
                );

                int movementCost = map.getMovementCost(newPosition);

                Successor successor = new Successor(
                        newState,
                        direction.getAction(),
                        movementCost
                );

                successors.add(successor);
            }
        }
    }

    private void addCollectSuccessor(
            AgentState state,
            List<Successor> successors
    ) {
        Position currentPosition = state.position();

        if (map.containsTreasure(currentPosition)) {
            int treasureNumber = map.getTreasureNumber(currentPosition);

            if (!state.isTreasureCollected(treasureNumber)) {
                AgentState newState =
                        state.collectTreasure(treasureNumber);

                Successor successor = new Successor(
                        newState,
                        's',
                        0
                );

                successors.add(successor);
            }
        }
    }

    public List<Successor> generateSuccessors(AgentState state) {
        List<Successor> successors = new ArrayList<>();

        addMovementSuccessors(state, successors);
        addCollectSuccessor(state, successors);

        return successors;
    }

    public boolean isGoal(AgentState state) {
        return state.areAllTreasuresCollected(
                map.getTreasureCount()
        );
    }

    public int heuristic(AgentState state) {
        return 0;
    }
}