package general;

public class Edge {
    public Node target;
    public double distance;

    public Edge(Node target, double distance) {
        if (target == null) {
            throw new IllegalArgumentException("Destino da aresta não pode ser nulo.");
        }
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
