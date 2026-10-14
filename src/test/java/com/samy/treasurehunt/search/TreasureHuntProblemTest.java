package com.samy.treasurehunt.search;

import com.samy.treasurehunt.model.AgentState;
import com.samy.treasurehunt.model.GridMap;
import com.samy.treasurehunt.model.Position;
import com.samy.treasurehunt.model.Successor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreasureHuntProblemTest {

    @TempDir
    Path tempDirectory;

    @Test
    void shouldGenerateAccessibleMovementSuccessors() throws IOException {
        Path filePath = tempDirectory.resolve("movement-map.txt");

        Files.writeString(filePath, """
                123
                4W6
                789
                """);

        GridMap map = new GridMap(filePath);
        TreasureHuntProblem problem = new TreasureHuntProblem(map);
        AgentState state = new AgentState(new Position(0, 0), 0);

        List<Successor> successors = problem.generateSuccessors(state);

        assertEquals(2, successors.size());

        assertEquals(
                new Successor(
                        new AgentState(new Position(0, 1), 0),
                        'd',
                        2
                ),
                successors.get(0)
        );

        assertEquals(
                new Successor(
                        new AgentState(new Position(1, 0), 0),
                        'b',
                        4
                ),
                successors.get(1)
        );
    }

    @Test
    void shouldNotGenerateCollectSuccessorForCollectedTreasure()
            throws IOException {

        Path filePath = tempDirectory.resolve("collected-map.txt");

        Files.writeString(filePath, """
            $23
            456
            789
            """);

        GridMap map = new GridMap(filePath);
        TreasureHuntProblem problem = new TreasureHuntProblem(map);

        AgentState state = new AgentState(
                new Position(0, 0),
                0
        ).collectTreasure(0);

        List<Successor> successors = problem.generateSuccessors(state);

        assertEquals(2, successors.size());

        for (Successor successor : successors) {
            assertNotEquals('s', successor.action());
        }
    }

    @Test
    void shouldGenerateCollectSuccessorForUncollectedTreasure()
            throws IOException {

        Path filePath = tempDirectory.resolve("uncollected-map.txt");

        Files.writeString(filePath, """
            $23
            456
            789
            """);

        GridMap map = new GridMap(filePath);
        TreasureHuntProblem problem = new TreasureHuntProblem(map);

        AgentState state = new AgentState(
                new Position(0, 0),
                0
        );

        List<Successor> successors = problem.generateSuccessors(state);

        Successor expectedCollectSuccessor = new Successor(
                new AgentState(
                        new Position(0, 0),
                        1
                ),
                's',
                0
        );

        assertEquals(3, successors.size());
        assertTrue(successors.contains(expectedCollectSuccessor));
    }
}