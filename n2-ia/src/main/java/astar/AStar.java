package astar;

import general.Graph;
import general.Node;
import utils.NodeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AStar {

    static int iterations = 0;
    static int nodeChecks = 0;
    static int changes = 0;

    public static void main(String[] args) {
        Graph graph = new Graph();

        List<Node> nodes = NodeUtils.getNodesForGraph();
        List<Node> nodesToVisit = NodeUtils.getNodesToVisit();
        nodes.forEach(graph::addNode);


        long startTime = System.currentTimeMillis();
        List<Node> path = findPath(graph, NodeUtils.findByName("Rio de Janeiro"), nodesToVisit);

        if (path != null) {
            System.out.println("Rota mais eficiente:");
            for (Node node : path) {
                System.out.println(node.getName());
            }
            long endTime = System.currentTimeMillis();
            long runtime = endTime - startTime;

            System.out.println("Número de Iterações: " + iterations);
            System.out.println("Número de Verificações: " + nodeChecks);
            System.out.println("Número de Trocas: " + changes);
            System.out.println("Tempo de Execução (ms): " + runtime);
        } else {
            System.out.println("Não foi possível encontrar uma rota.");
        }
    }

    public static List<Node> findPath(Graph graph, Node start, List<Node> nodesToVisit) {
        List<Node> openSet = new ArrayList<>();
        List<Node> closedSet = new ArrayList<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Double> gScore = new HashMap<>();
        Map<Node, Double> fScore = new HashMap<>();
        Map<Node, List<Node>> routeMap = new HashMap<>();

        openSet.add(start);
        gScore.put(start, 0.0);
        fScore.put(start, start.getHeuristic(nodesToVisit.get(0)));
        routeMap.put(start, new ArrayList<>());
        routeMap.get(start).add(start);

        while (!openSet.isEmpty()) {
            iterations++;
            Node current = getNodeWithLowestFScore(openSet, fScore);
            if (nodesToVisit.contains(current)) {
                nodesToVisit.remove(current);
                if (nodesToVisit.isEmpty()) {
                    return routeMap.get(current);
                }
            }

            openSet.remove(current);
            closedSet.add(current);

            for (Node neighbor : current.getEdgeTargets()) {
                nodeChecks++;
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                double tentativeGScore = gScore.get(current) + current.getDistance(neighbor);

                if (!openSet.contains(neighbor) || tentativeGScore < gScore.get(neighbor)) {
                    changes++;
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + neighbor.getHeuristic(nodesToVisit.get(0)));
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }

                    routeMap.put(neighbor, new ArrayList<>(routeMap.get(current)));
                    routeMap.get(neighbor).add(neighbor);
                }
            }
        }

        return new ArrayList<>();
    }


    private static Node getNodeWithLowestFScore(List<Node> openSet, Map<Node, Double> fScore) {
        Node lowestNode = openSet.get(0);
        double lowestFScore = fScore.get(lowestNode);

        for (Node node : openSet) {
            double nodeFScore = fScore.get(node);
            if (nodeFScore < lowestFScore) {
                lowestNode = node;
                lowestFScore = nodeFScore;
            }
        }

        return lowestNode;
    }

}
