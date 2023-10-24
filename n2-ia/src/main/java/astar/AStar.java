package astar;

import java.util.*;

public class AStar {
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

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(end)) {
                return reconstructPath(cameFrom, current);
            }

            for (Edge edge : current.getEdges()) {
                Node neighbor = edge.getTarget();
                double tentativeGScore = gScore.get(current) + edge.getDistance();

                if (tentativeGScore < gScore.get(neighbor)) {
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
