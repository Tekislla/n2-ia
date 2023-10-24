import astar.AStar;
import astar.Graph;
import astar.Node;

import java.util.List;

public class Main {
    public static void main(String[] args) {
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

}
