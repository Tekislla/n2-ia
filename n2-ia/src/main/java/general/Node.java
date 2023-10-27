package general;

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

    public List<Node> getEdgeTargets() {
        List<Node> targets = new ArrayList<>();
        for (Edge edge : edges) {
            targets.add(edge.getTarget());
        }
        return targets;
    }

    public void addEdge(Node target, double distance) {
        Edge edge = new Edge(target, distance);
        edges.add(edge);
    }

    public double getHeuristic(Node end) {
        double lat1 = this.latitude;
        double lon1 = this.longitude;
        double lat2 = end.getLatitude();
        double lon2 = end.getLongitude();

        double distance = Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
        return distance;
    }

    public double getDistance(Node neighbor) {
        double distance = 0;

        for (Edge edge : neighbor.edges) {
            if(edge.target.name == this.name) {
                distance = edge.distance;
            }
        }

        return distance;
    }
}
