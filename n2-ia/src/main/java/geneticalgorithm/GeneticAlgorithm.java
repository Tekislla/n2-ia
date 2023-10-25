package geneticalgorithm;

import astar.Graph;
import astar.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
    public static List<Node> findOptimalRoute(Graph graph, int populationSize, int generations) {
        // Inicialize uma população aleatória de rotas.
        List<List<Node>> population = initializePopulation(graph, populationSize);

        for (int generation = 0; generation < generations; generation++) {
            // Avalie a aptidão de cada rota na população.
            List<RouteFitness> routeFitnessList = evaluatePopulation(population);

            // Selecione as melhores rotas com base na aptidão.
            List<List<Node>> selectedRoutes = selectRoutes(routeFitnessList, populationSize);

            // Aplique operadores de cruzamento (crossover) para criar novas rotas.
            List<List<Node>> newPopulation = crossover(selectedRoutes);

            // Aplique operadores de mutação para introduzir variação.
            mutatePopulation(newPopulation);

            population = newPopulation;
        }

        // Retorne a melhor rota encontrada após todas as gerações.
        return getBestRoute(population, graph);
    }

    private static List<List<Node>> initializePopulation(Graph graph, int populationSize) {
        List<List<Node>> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Node> randomRoute = new ArrayList<>(graph.getNodes());
            Collections.shuffle(randomRoute);
            population.add(randomRoute);
        }

        return population;
    }

    private static List<RouteFitness> evaluatePopulation(List<List<Node>> population) {
        List<RouteFitness> routeFitnessList = new ArrayList<>();

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
        Random random = new Random();

        while (selectedRoutes.size() >= 2) {
            List<Node> parent1 = selectedRoutes.remove(random.nextInt(selectedRoutes.size()));
            List<Node> parent2 = selectedRoutes.remove(random.nextInt(selectedRoutes.size()));

            // Escolha pontos de corte aleatórios para crossover.
            int crossoverPoint1 = random.nextInt(parent1.size());
            int crossoverPoint2 = random.nextInt(parent1.size());

            int start = Math.min(crossoverPoint1, crossoverPoint2);
            int end = Math.max(crossoverPoint1, crossoverPoint2);

            // Realize o crossover trocando as seções entre os pontos de corte.
            List<Node> child1 = new ArrayList<>(parent1.subList(start, end));
            List<Node> child2 = new ArrayList<>(parent2.subList(start, end));

            // Preencha os filhos com os genes dos pais não selecionados.
            for (Node city : parent2) {
                if (!child1.contains(city)) {
                    child1.add(city);
                }
            }
            for (Node city : parent1) {
                if (!child2.contains(city)) {
                    child2.add(city);
                }
            }

            // Adicione os filhos à nova população.
            newPopulation.add(child1);
            newPopulation.add(child2);
        }

        // Adicione qualquer rota restante que não pôde ser emparelhada.
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
        totalDistance += route.get(route.size() - 1).getDistance(route.get(0)); // Volte ao ponto de partida.
        return totalDistance;
    }

    private static List<Node> getBestRoute(List<List<Node>> population, Graph graph) {
        List<Node> bestRoute = null;
        double bestDistance = Double.POSITIVE_INFINITY;

        for (List<Node> route : population) {
            double totalDistance = calculateTotalDistance(route);
            if (totalDistance < bestDistance) {
                bestRoute = route;
                bestDistance = totalDistance;
            }
        }

        return bestRoute;
    }

    private static class RouteFitness implements Comparable<RouteFitness> {
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
}
