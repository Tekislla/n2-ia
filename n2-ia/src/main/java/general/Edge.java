package general;

public class Edge {
    private Node target;
    private double distance;

    public Edge(Node target, double distance) {
        this.target = target;
        this.distance = distance;
    }

    public Node getTarget() {
        return target;
    }

    public double getDistance() {
        return distance;
    }
}