package geneticalgorithm;

import general.Node;

import java.util.List;

class RouteFitness implements Comparable<RouteFitness> {
    private List<Node> route;
    private double distance;

    public RouteFitness(List<Node> route, double distance) {
        this.route = route;
        this.distance = distance;
    }

    public List<Node> getRoute() {
        return route;
    }

    @Override
    public int compareTo(RouteFitness other) {
        return Double.compare(this.distance, other.distance);
    }
}