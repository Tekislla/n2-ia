package astar;

import Utils.NodeUtils;
import general.Edge;
import general.Graph;
import general.Node;

import java.util.*;

public class AStar {

    static int iterations = 0;
    static int nodeChecks = 0;
    static int changes = 0;

    public static void main(String[] args) {
        Graph graph = new Graph();

        List<Node> nodes = NodeUtils.getNodesForGraph();
        nodes.forEach(graph::addNode);


        Node startNode = NodeUtils.findByName(nodes, "São Paulo");
        Node endNode = NodeUtils.findByName(nodes, "Goiânia");

        long startTime = System.currentTimeMillis();
        List<Node> path = AStar.findPath(graph, startNode, endNode);

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

    public static List<Node> findPath(Graph graph, Node start, Node end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getFScore));
        Map<Node, Double> gScore = new HashMap<>();
        Map<Node, Node> cameFrom = new HashMap();

        for (Node node : graph.getNodes()) {
            gScore.put(node, Double.POSITIVE_INFINITY);
        }

        gScore.put(start, 0.0);
        start.setFScore(start.getHeuristic(end));
        openSet.add(start);

        // Exploration
        while (!openSet.isEmpty()) {
            iterations++;
            Node current = openSet.poll();

            if (current.equals(end)) {
                return reconstructPath(cameFrom, current);
            }

            // Exploitation
            for (Edge edge : current.getEdges()) {
                Node neighbor = edge.getTarget();
                nodeChecks++;
                double tentativeGScore = gScore.get(current) + edge.getDistance();

                if (tentativeGScore < gScore.get(neighbor)) {
                    changes++;
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    double fScore = tentativeGScore + neighbor.getHeuristic(end);
                    neighbor.setFScore(fScore);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    private static List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Node> path = new ArrayList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }
        return path;
    }
}
