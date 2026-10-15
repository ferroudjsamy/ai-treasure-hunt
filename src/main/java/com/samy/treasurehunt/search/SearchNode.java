package com.samy.treasurehunt.search;

import com.samy.treasurehunt.model.AgentState;

public record SearchNode(
        AgentState state,
        SearchNode parent,
        char action,
        int g,
        int h
) {

    public int f() {
        return g + h;
    }




}