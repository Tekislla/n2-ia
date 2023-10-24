package astar;

import java.util.*;

public class Node {
    private String name;
    private double latitude;
    private double longitude;
    private List<Edge> edges;
    private double gScore;
    private double fScore;
    private Node cameFrom;

    public Node(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.edges = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Node target, double distance) {
        Edge edge = new Edge(target, distance);
        edges.add(edge);
    }

    public double getHeuristic(Node end) {
        // Calcula a distância Euclidiana entre as coordenadas deste nó e o nó de destino.
        double lat1 = this.latitude;
        double lon1 = this.longitude;
        double lat2 = end.getLatitude();
        double lon2 = end.getLongitude();

        // Fórmula da distância Euclidiana
        double distance = Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
        return distance;
    }

    public double getDistance(Node neighbor) {
        // Calcula a distância Euclidiana entre as coordenadas deste nó e o vizinho.
        double lat1 = this.latitude;
        double lon1 = this.longitude;
        double lat2 = neighbor.getLatitude();
        double lon2 = neighbor.getLongitude();

        // Fórmula da distância Euclidiana
        double distance = Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
        return distance;
    }

    public double getGScore() {
        return gScore;
    }

    public void setGScore(double gScore) {
        this.gScore = gScore;
    }

    public double getFScore() {
        return fScore;
    }

    public void setFScore(double fScore) {
        this.fScore = fScore;
    }

    public Node getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(Node cameFrom) {
        this.cameFrom = cameFrom;
    }
}
