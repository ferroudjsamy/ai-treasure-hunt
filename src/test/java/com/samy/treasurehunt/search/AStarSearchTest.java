package com.samy.treasurehunt.search;

import com.samy.treasurehunt.model.AgentState;
import com.samy.treasurehunt.model.GridMap;
import com.samy.treasurehunt.model.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class AStarSearchTest {

    @TempDir
    Path tempDirectory;

    @Test
    void shouldFindSolutionForOneTreasure() throws IOException {
        Path filePath = tempDirectory.resolve("one-treasure-map.txt");

        Files.writeString(filePath, """
                1$9
                999
                999
                """);

        GridMap map = new GridMap(filePath);
        TreasureHuntProblem problem = new TreasureHuntProblem(map);
        AStarSearch search = new AStarSearch(problem);

        AgentState initialState = new AgentState(
                new Position(0, 0),
                0
        );

        SearchNode goalNode = search.search(initialState);

        assertNotNull(goalNode);
        assertEquals(1, goalNode.g());
        assertEquals("dsq", search.buildSolution(goalNode));
    }

    @Test
    void shouldChooseLowestCostPath() throws IOException {
        Path filePath = tempDirectory.resolve("weighted-map.txt");

        Files.writeString(filePath, """
            19$
            111
            111
            """);

        GridMap map = new GridMap(filePath);
        TreasureHuntProblem problem = new TreasureHuntProblem(map);
        AStarSearch search = new AStarSearch(problem);

        AgentState initialState = new AgentState(
                new Position(0, 0),
                0
        );

        SearchNode goalNode = search.search(initialState);

        assertNotNull(goalNode);
        assertEquals(4, goalNode.g());
        assertEquals("bddhsq", search.buildSolution(goalNode));
    }

    @Test
    void shouldCollectAllTreasures() throws IOException {
        Path filePath = tempDirectory.resolve("multiple-treasures-map.txt");

        Files.writeString(filePath, """
            1$1
            111
            $11
            """);

        GridMap map = new GridMap(filePath);
        TreasureHuntProblem problem = new TreasureHuntProblem(map);
        AStarSearch search = new AStarSearch(problem);

        AgentState initialState = new AgentState(
                new Position(0, 0),
                0
        );

        SearchNode goalNode = search.search(initialState);

        assertNotNull(goalNode);
        assertEquals(4, goalNode.g());
        assertTrue(
                goalNode.state().areAllTreasuresCollected(
                        map.getTreasureCount()
                )
        );

        String solution = search.buildSolution(goalNode);

        assertEquals(2, solution.chars()
                .filter(action -> action == 's')
                .count());

        assertTrue(solution.endsWith("q"));
    }
}