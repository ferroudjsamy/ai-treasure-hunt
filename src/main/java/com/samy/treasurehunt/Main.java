package com.samy.treasurehunt;

import com.samy.treasurehunt.model.AgentState;
import com.samy.treasurehunt.model.GridMap;
import com.samy.treasurehunt.model.Position;
import com.samy.treasurehunt.search.AStarSearch;
import com.samy.treasurehunt.search.SearchNode;
import com.samy.treasurehunt.search.TreasureHuntProblem;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Main <map-file>");
            return;
        }

        try {
            GridMap map = new GridMap(Path.of(args[0]));

            Position initialPosition = new Position(0, 0);

            if (!map.isAccessible(initialPosition)) {
                System.out.println("q");
                return;
            }

            AgentState initialState = new AgentState(
                    initialPosition,
                    0
            );

            TreasureHuntProblem problem =
                    new TreasureHuntProblem(map);

            AStarSearch search = new AStarSearch(problem);

            SearchNode goalNode = search.search(initialState);

            String solution = search.buildSolution(goalNode);

            System.out.println(solution);

        } catch (IOException | IllegalArgumentException e) {
            System.err.println(
                    "Unable to solve the map: " + e.getMessage()
            );
        }
    }
}