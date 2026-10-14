package com.samy.treasurehunt.model;

public record AgentState(
        Position position,
        int collectedTreasuresMask
) {

    public boolean isTreasureCollected(int treasureNumber) {
        int treasureBit = 1 << treasureNumber;

        return (collectedTreasuresMask & treasureBit) != 0;
    }

    public AgentState collectTreasure(int treasureNumber) {
        int treasureBit = 1 << treasureNumber;
        int newMask = collectedTreasuresMask | treasureBit;

        return new AgentState(position, newMask);
    }

    public boolean areAllTreasuresCollected(int treasureCount) {
        int completeMask = (1 << treasureCount) - 1;

        return collectedTreasuresMask == completeMask;
    }



}