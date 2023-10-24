package astar;

import java.util.*;

public class AStarCTSP {
    public static List<Node> findPath(Graph graph, Node start) {
        List<Node> unvisited = new ArrayList<>(graph.getNodes());
        unvisited.remove(start); // Remove o ponto de partida da lista de nós não visitados.

        List<Node> path = new ArrayList<>();
        path.add(start);

        while (!unvisited.isEmpty()) {
            Node current = path.get(path.size() - 1);
            Node nearestNeighbor = null;
            double minDistance = Double.POSITIVE_INFINITY;

            for (Node neighbor : unvisited) {
                double distance = current.getDistance(neighbor);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestNeighbor = neighbor;
                }
            }

            if (nearestNeighbor != null) {
                path.add(nearestNeighbor);
                unvisited.remove(nearestNeighbor);
            }
        }

        // Volta para o ponto de partida para completar o ciclo.
        path.add(start);

        return path;
    }
}
