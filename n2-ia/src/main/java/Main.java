import astar.AStar;
import astar.Graph;
import astar.Node;
import geneticalgorithm.GeneticAlgorithm;

import java.util.List;

public class Main {
    public static void astar(String[] args) {
        // Criando um grafo
        Graph graph = new Graph();

        // Criando os nós (cidades) e adicionando ao grafo
        Node cityA = new Node("City A", 0.0, 0.0);
        Node cityB = new Node("City B", 1.0, 1.0);
        Node cityC = new Node("City C", 2.0, 2.0);

        graph.addNode(cityA);
        graph.addNode(cityB);
        graph.addNode(cityC);

        // Conectando os nós com arestas (representando distâncias entre cidades)
        cityA.addEdge(cityB, 1.0);
        cityA.addEdge(cityC, 2.0);
        cityB.addEdge(cityC, 1.5);

        // Definindo o nó de origem e o nó de destino
        Node startNode = cityA;
        Node endNode = cityC;

        // Encontrando a rota mais eficiente usando o algoritmo A*
        List<Node> path = AStar.findPath(graph, startNode, endNode);

        if (path != null) {
            System.out.println("Rota mais eficiente:");
            for (Node node : path) {
                System.out.println(node.getName());
            }
        } else {
            System.out.println("Não foi possível encontrar uma rota.");
        }
    }

    public static void main(String[] args) {
        // Crie um grafo com as cidades e distâncias entre elas.
        Graph graph = new Graph();

        Node cityA = new Node("City A", 0.0, 0.0);
        Node cityB = new Node("City B", 1.0, 1.0);
        Node cityC = new Node("City C", 2.0, 2.0);

        graph.addNode(cityA);
        graph.addNode(cityB);
        graph.addNode(cityC);

        cityA.addEdge(cityB, 1.0);
        cityA.addEdge(cityC, 2.0);
        cityB.addEdge(cityC, 1.5);

        // Defina os parâmetros do algoritmo genético.
        int populationSize = 100;
        int generations = 1000;

        // Execute o algoritmo genético para encontrar a rota mais eficiente.
        List<Node> bestRoute = GeneticAlgorithm.findOptimalRoute(graph, populationSize, generations);

        if (bestRoute != null) {
            System.out.println("Rota mais eficiente encontrada:");
            for (Node city : bestRoute) {
                System.out.println(city.getName());
            }
        } else {
            System.out.println("Não foi possível encontrar uma rota eficiente.");
        }
    }

}
