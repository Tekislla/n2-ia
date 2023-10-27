package Utils;

import general.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeUtils {

    static Node rioDeJaneiro = new Node("Rio de Janeiro", -22.9068, -43.1729);
    static Node saoPaulo = new Node("São Paulo", -23.5505, -46.6333);
    static Node beloHorizonte = new Node("Belo Horizonte", -19.9167, -43.9345);
    static Node brasilia = new Node("Brasília", -15.7801, -47.9292);
    static Node salvador = new Node("Salvador", -12.9716, -38.5016);
    static Node recife = new Node("Recife", -8.0476, -34.8770);
    static Node fortaleza = new Node("Fortaleza", -3.7172, -38.5433);
    static Node curitiba = new Node("Curitiba", -25.4275, -49.2731);
    static Node portoAlegre = new Node("Porto Alegre", -30.0277, -51.2287);
    static Node manaus = new Node("Manaus", -3.1190, -60.0217);
    static Node belem = new Node("Belém", -1.4550, -48.5023);
    static Node goiania = new Node("Goiânia", -16.6809, -49.2533);
    static Node cuiaba = new Node("Cuiabá", -15.6010, -56.0974);
    static Node natal = new Node("Natal", -5.7945, -35.2110);
    static Node florianopolis = new Node("Florianópolis", -27.5954, -48.5480);
    static Node portoVelho = new Node("Porto Velho", -8.7619, -63.9034);
    static Node boaVista = new Node("Boa Vista", 2.8235, -60.6758);


    public static void addEdges() {
        rioDeJaneiro.addEdge(saoPaulo, 429.1);
        rioDeJaneiro.addEdge(beloHorizonte, 339.0);
        saoPaulo.addEdge(beloHorizonte, 584.3);
        saoPaulo.addEdge(curitiba, 408.7);
        saoPaulo.addEdge(rioDeJaneiro, 429.1);
        beloHorizonte.addEdge(rioDeJaneiro, 339.0);
        beloHorizonte.addEdge(saoPaulo, 584.3);
        beloHorizonte.addEdge(brasilia, 621.7);
        brasilia.addEdge(beloHorizonte, 621.7);
        brasilia.addEdge(goiania, 209.3);
        brasilia.addEdge(cuiaba, 1113.3);
        salvador.addEdge(recife, 824.4);
        recife.addEdge(salvador, 824.4);
        recife.addEdge(fortaleza, 806.3);
        fortaleza.addEdge(recife, 806.3);
        fortaleza.addEdge(natal, 515.3);
        curitiba.addEdge(saoPaulo, 408.7);
        curitiba.addEdge(portoAlegre, 709.8);
        portoAlegre.addEdge(curitiba, 709.8);
        portoAlegre.addEdge(florianopolis, 476.7);
        manaus.addEdge(portoVelho, 682.2);
        manaus.addEdge(boaVista, 777.3);
        belem.addEdge(manaus, 2116.3);
    }

    public static List<Node> getListForGraph() {
        List<Node> list = new ArrayList<>();
        addEdges();

        list.add(rioDeJaneiro);
        list.add(saoPaulo);
        list.add(beloHorizonte);
        list.add(brasilia);
        list.add(salvador);
        list.add(recife);
        list.add(fortaleza);
        list.add(curitiba);
        list.add(portoAlegre);
        list.add(manaus);
        list.add(belem);
        list.add(goiania);
        list.add(cuiaba);
        list.add(natal);
        list.add(florianopolis);
        list.add(portoVelho);
        list.add(boaVista);

        return list;
    }

    public static List<Node> getEasyListToVisit() {
        List<Node> list = new ArrayList<>();
        addEdges();

        list.add(portoAlegre);
        list.add(rioDeJaneiro);

        return list;
    }

    public static List<Node> getNodesForGraph() {
        List<Node> list = getListForGraph();

        return list;
    }

    public static List<Node> getNodesToVisit() {
        List<Node> list = getEasyListToVisit();

        return list;
    }

    public static Node findByName(List<Node> list, String name) {
        Node retorno = null;
        for (Node node : list) {
            if (node.getName().equals(name)) {
                retorno = node;
            }
        }

        if (retorno == null) {
            throw new RuntimeException("Cidade não encontrada!");
        }

        return retorno;
    }

}
