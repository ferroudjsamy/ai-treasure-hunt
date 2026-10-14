package com.samy.treasurehunt.model;

public record Successor(
        AgentState state,
        char action,
        int cost
) {

}