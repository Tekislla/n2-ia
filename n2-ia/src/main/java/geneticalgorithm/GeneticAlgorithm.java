package geneticalgorithm;

import general.Edge;
import general.Graph;
import general.Node;
import utils.NodeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    static int iterations = 0;
    static int nodeChecks = 0;
    static int changes = 0;
    static int distance = 0;

    public static void main(String[] args) {
        Graph graph = new Graph();

        List<Node> nodesToVisit = NodeUtils.getNodesToVisit();
        List<Node> nodesForGraph = NodeUtils.getNodesForGraph();
        nodesForGraph.forEach(graph::addNode);

        int populationSize = 100;
        int generations = 5000;

        long startTime = System.currentTimeMillis();
        List<Node> bestRoute = GeneticAlgorithm.findOptimalRoute(graph, NodeUtils.findByName("São Paulo"), nodesToVisit, populationSize, generations);

        if (bestRoute != null) {
            bestRoute.remove(bestRoute.get(0));
            System.out.println("Rota mais eficiente encontrada:");

            for (int i = 0; i < bestRoute.size() - 1; i++) {
                String actualCityName = bestRoute.get(i).getName();
                List<Edge> nextNodeEdge = bestRoute.get(i + 1).getEdges();

                for (Edge edge : nextNodeEdge) {
                    if(edge.target.getName() == actualCityName)
                        distance += edge.distance;
                }
            }

            for (Node city : bestRoute)
                System.out.println(city.getName());

            long endTime = System.currentTimeMillis();
            long runtime = endTime - startTime;

            System.out.println("Número de Iterações: " + iterations);
            System.out.println("Número de Verificações: " + nodeChecks);
            System.out.println("Número de Trocas: " + changes);
            System.out.println("Tempo de Execução (ms): " + runtime);
            System.out.println("Distância: " + distance + "km");
        } else {
            System.out.println("Não foi possível encontrar uma rota eficiente.");
        }
    }

    public static List<Node> findOptimalRoute(Graph graph, Node startPoint, List<Node> nodesToVisit, int populationSize, int generations) {
        List<List<Node>> population = initializePopulation(graph, populationSize, startPoint, nodesToVisit);

        for (int generation = 0; generation < generations; generation++) {
            List<RouteFitness> routeFitnessList = evaluatePopulation(population);
            List<List<Node>> selectedRoutes = selectRoutes(routeFitnessList, populationSize);
            //List<List<Node>> newPopulation = singlePointCrossover(selectedRoutes);

            //mutatePopulation(newPopulation);

            population = selectedRoutes;
        }

        return getBestRoute(population, startPoint, nodesToVisit);
    }

    private static List<List<Node>> initializePopulation(Graph graph, int populationSize, Node startPoint, List<Node> destinations) {
        List<List<Node>> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Node> randomRoute = new ArrayList<>();
            randomRoute.add(startPoint);

            Node currentNode = startPoint;
            List<Node> unvisitedDestinations = new ArrayList<>(destinations);

            while (!unvisitedDestinations.isEmpty()) {
                Node nextNode = getRandomNeighbor(graph, currentNode, currentNode.getEdgeTargets());
                if (nextNode == null) {
                    nextNode = startPoint;
                }
                randomRoute.add(nextNode);
                currentNode = nextNode;
                unvisitedDestinations.remove(currentNode);
            }

            population.add(randomRoute);
        }

        return population;
    }

    private static Node getRandomNeighbor(Graph graph, Node currentNode, List<Node> neighbors) {
        List<Node> validNeighbors = new ArrayList<>();

        for (Node neighbor : neighbors) {
            if (areConnected(graph, currentNode, neighbor)) {
                validNeighbors.add(neighbor);
            }
        }

        if (validNeighbors.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * validNeighbors.size());
        return validNeighbors.get(randomIndex);
    }


    private static List<RouteFitness> evaluatePopulation(List<List<Node>> population) {
        List<RouteFitness> routeFitnessList = new ArrayList();

        for (List<Node> route : population) {
            double totalDistance = calculateTotalDistance(route);
            routeFitnessList.add(new RouteFitness(route, totalDistance));
        }

        Collections.sort(routeFitnessList);

        return routeFitnessList;
    }

    private static boolean areConnected(Graph graph, Node node1, Node node2) {
        for (Edge edge : node1.getEdges()) {
            if (edge.getTarget() == node2) {
                return true;
            }
        }
        return false;
    }

    private static List<List<Node>> selectRoutes(List<RouteFitness> routeFitnessList, int populationSize) {
        List<List<Node>> selectedRoutes = new ArrayList<>();
        int maxRoutes = Math.min(routeFitnessList.size(), populationSize);

        for (int i = 0; i < maxRoutes; i++) {
            selectedRoutes.add(routeFitnessList.get(i).getRoute());
        }

        return selectedRoutes;
    }


    private static List<List<Node>> singlePointCrossover(List<List<Node>> selectedRoutes) {
        List<List<Node>> newPopulation = new ArrayList<>();
        Random random = new Random();

        while (selectedRoutes.size() >= 2) {
            List<Node> parent1 = selectedRoutes.get(random.nextInt(selectedRoutes.size()));
            List<Node> parent2 = selectedRoutes.get(random.nextInt(selectedRoutes.size()));

            int routeSize = parent1.size();
            int crossoverPoint = random.nextInt(routeSize);

            List<Node> child1 = new ArrayList<>();
            List<Node> child2 = new ArrayList<>();

            for (int i = 0; i < routeSize; i++) {
                if (i < crossoverPoint) {
                    child1.add(parent1.get(i));
                    if (i < parent2.size()) {
                        child2.add(parent2.get(i));
                    }
                } else {
                    if (i < parent2.size()) {
                        child1.add(parent2.get(i));
                    }
                    child2.add(parent1.get(i));
                }
            }

            newPopulation.add(child1);
            newPopulation.add(child2);

            selectedRoutes.remove(parent1);
            selectedRoutes.remove(parent2);
        }

        newPopulation.addAll(selectedRoutes);

        return newPopulation;
    }

    private static void mutatePopulation(List<List<Node>> population) {
        Random random = new Random();
        for (List<Node> route : population) {
            if (random.nextDouble() < 0.1) {
                int mutationPoint1 = random.nextInt(route.size());
                int mutationPoint2 = random.nextInt(route.size());

                Collections.swap(route, mutationPoint1, mutationPoint2);
            }
        }
    }

    private static double calculateTotalDistance(List<Node> route) {
        double totalDistance = 0.0;

        if (route != null && !route.isEmpty()) {
            for (int i = 0; i < route.size() - 1; i++) {
                Node currentNode = route.get(i);
                Node nextNode = route.get(i + 1);

                if (currentNode != null && nextNode != null) {
                    totalDistance += currentNode.getDistance(nextNode);
                } else {
                    return 0;
                }
            }

            Node startPoint = route.get(0);
            Node endPoint = route.get(route.size() - 1);

            if (startPoint != null && endPoint != null) {
                totalDistance += startPoint.getDistance(endPoint);
            }
        }

        return totalDistance;
    }

    private static List<Node> getBestRoute(List<List<Node>> population, Node startPoint, List<Node> destinations) {
        List<Node> bestRoute = null;
        double bestDistance = Double.POSITIVE_INFINITY;

        for (List<Node> route : population) {
            if (containsAllDestinations(route, destinations)) {
                double totalDistance = calculateTotalDistance(route);

                if (route.size() >= 2) {
                    totalDistance += startPoint.getDistance(route.get(1));
                }

                if (totalDistance < bestDistance) {
                    bestRoute = route;
                    bestDistance = totalDistance;
                }
            }
        }

        if (bestRoute != null) {
            bestRoute.add(0, startPoint);
        }

        return bestRoute;
    }

    private static boolean containsAllDestinations(List<Node> route, List<Node> destinations) {
        return route.containsAll(destinations);
    }
}