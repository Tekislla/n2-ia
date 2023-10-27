package astar;

import utils.NodeUtils;
import general.Graph;
import general.Node;

import java.util.*;

public class AStarTSP {

    static int iterations = 0;
    static int nodeChecks = 0;
    static int changes = 0;

    public static void main(String[] args) {
        Graph graph = new Graph();

        List<Node> nodes = NodeUtils.getNodesForGraph();
        nodes.forEach(graph::addNode);

        Node startNode = new Node("Rio de Janeiro", 1, 5);

        long startTime = System.currentTimeMillis();
        List<Node> path = findPath(graph, startNode);

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

    public static List<Node> findPath(Graph graph, Node start) {
        List<Node> unvisited = new ArrayList<>(graph.getNodes());
        unvisited.remove(start);

        List<Node> path = new ArrayList<>();
        path.add(start);

        while (!unvisited.isEmpty()) {
            iterations++;
            Node current = path.get(path.size() - 1);
            Node nearestNeighbor = null;
            double minDistance = Double.POSITIVE_INFINITY;

            for (Node neighbor : unvisited) {
                nodeChecks++;
                double distance = current.getDistance(neighbor);
                if (distance < minDistance) {
                    changes++;
                    minDistance = distance;
                    nearestNeighbor = neighbor;
                }
            }

            if (nearestNeighbor != null) {
                path.add(nearestNeighbor);
                unvisited.remove(nearestNeighbor);
            }
        }

        return path;
    }
}
