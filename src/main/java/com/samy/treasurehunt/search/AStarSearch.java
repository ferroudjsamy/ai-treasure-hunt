package com.samy.treasurehunt.search;

import com.samy.treasurehunt.model.AgentState;
import com.samy.treasurehunt.model.Successor;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class AStarSearch {

    private final TreasureHuntProblem problem;

    public AStarSearch(TreasureHuntProblem problem) {
        this.problem = problem;
    }

    public SearchNode search(AgentState initialState) {
        PriorityQueue<SearchNode> frontier =
                new PriorityQueue<>(
                        Comparator.comparingInt(SearchNode::f)
                );

        SearchNode initialNode = new SearchNode(
                initialState,
                null,
                '\0',
                0,
                problem.heuristic(initialState)
        );

        frontier.add(initialNode);
        Map<AgentState, Integer> bestCosts = new HashMap<>();
        bestCosts.put(initialState, 0);

        while (!frontier.isEmpty()) {
            SearchNode currentNode = frontier.poll();

            int bestKnownCost = bestCosts.get(currentNode.state());

            if (currentNode.g() > bestKnownCost) {
                continue;
            }

            if (problem.isGoal(currentNode.state())) {
                return currentNode;
            }

            for (Successor successor :
                    problem.generateSuccessors(currentNode.state())) {

                int newG = currentNode.g() + successor.cost();
                int knownG = bestCosts.getOrDefault(
                        successor.state(),
                        Integer.MAX_VALUE
                );

                if (newG < knownG) {
                    bestCosts.put(successor.state(), newG);
                    SearchNode newNode = new SearchNode(
                            successor.state(),
                            currentNode,
                            successor.action(),
                            newG,
                            problem.heuristic(successor.state())
                    );

                    frontier.add(newNode);
                }
            }
        }

        return null;
    }

    public String buildSolution(SearchNode goalNode) {
        if (goalNode == null) {
            return "q";
        }

        StringBuilder actions = new StringBuilder();
        SearchNode currentNode = goalNode;

        while (currentNode.parent() != null) {
            actions.append(currentNode.action());
            currentNode = currentNode.parent();
        }

        actions.reverse();
        actions.append('q');

        return actions.toString();
    }
}