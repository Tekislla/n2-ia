package geneticalgorithm;

import utils.NodeUtils;
import general.Edge;
import general.Graph;
import general.Node;

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

        int populationSize = 1000;
        int generations = 1000;

        long startTime = System.currentTimeMillis();
        List<Node> bestRoute = GeneticAlgorithm.findOptimalRoute(graph, NodeUtils.findByName(nodesForGraph, "São Paulo"), nodesToVisit, populationSize, generations);

        if (bestRoute != null) {
            System.out.println("Rota mais eficiente encontrada:");
            for (Node city : bestRoute) {
                for (Edge edge : city.getEdges()) {
                    distance += edge.getDistance();
                }
                System.out.println(city.getName());
            }
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
        List<List<Node>> population = initializePopulation(graph, populationSize, nodesToVisit);

        for (int generation = 0; generation < generations; generation++) {
            List<RouteFitness> routeFitnessList = evaluatePopulation(population);
            List<List<Node>> selectedRoutes = selectRoutes(routeFitnessList, populationSize);
            List<List<Node>> newPopulation = crossover(selectedRoutes);

            mutatePopulation(newPopulation);

            population = newPopulation;
        }

        return getBestRoute(population, startPoint);
    }


    private static List<List<Node>> initializePopulation(Graph graph, int populationSize, List<Node> nodesToVisit) {
        List<List<Node>> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Node> randomRoute = new ArrayList<>(nodesToVisit);
            Collections.shuffle(randomRoute);
            population.add(randomRoute);
        }

        return population;
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

    private static List<List<Node>> selectRoutes(List<RouteFitness> routeFitnessList, int populationSize) {
        List<List<Node>> selectedRoutes = new ArrayList<>();
        int maxRoutes = Math.min(routeFitnessList.size(), populationSize);

        for (int i = 0; i < maxRoutes; i++) {
            selectedRoutes.add(routeFitnessList.get(i).getRoute());
        }

        return selectedRoutes;
    }


    private static List<List<Node>> crossover(List<List<Node>> selectedRoutes) {
        List<List<Node>> newPopulation = new ArrayList<>();
        iterations++;
        Random random = new Random();

        while (selectedRoutes.size() >= 2) {
            List<Node> parent1 = selectedRoutes.remove(random.nextInt(selectedRoutes.size()));
            List<Node> parent2 = selectedRoutes.remove(random.nextInt(selectedRoutes.size()));

            int crossoverPoint1 = random.nextInt(parent1.size());
            int crossoverPoint2 = random.nextInt(parent1.size());

            int start = Math.min(crossoverPoint1, crossoverPoint2);
            int end = Math.max(crossoverPoint1, crossoverPoint2);

            List<Node> child1 = new ArrayList<>(parent1.subList(start, end));
            List<Node> child2 = new ArrayList<>(parent2.subList(start, end));

            for (Node city : parent2) {
                if (!child1.contains(city)) {
                    changes++;
                    child1.add(city);
                }
            }
            for (Node city : parent1) {
                if (!child2.contains(city)) {
                    changes++;
                    child2.add(city);
                }
            }

            newPopulation.add(child1);
            newPopulation.add(child2);
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
        for (int i = 0; i < route.size() - 1; i++) {
            totalDistance += route.get(i).getDistance(route.get(i + 1));
        }
        totalDistance += route.get(route.size() - 1).getDistance(route.get(0));
        return totalDistance;
    }

    private static List<Node> getBestRoute(List<List<Node>> population, Node startPoint) {
        List<Node> bestRoute = null;
        double bestDistance = Double.POSITIVE_INFINITY;

        for (List<Node> route : population) {
            double totalDistance = calculateTotalDistance(route);

            // Adicionando a distância de volta ao ponto de partida
            totalDistance += startPoint.getDistance(route.get(0));

            if (totalDistance < bestDistance) {
                bestRoute = route;
                bestDistance = totalDistance;
            }
        }

        // Adicionando o ponto de partida no início da melhor rota
        bestRoute.add(0, startPoint);

        return bestRoute;
    }

}
