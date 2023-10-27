package utils;

import general.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeUtils {

    private static Map<String, Node> cityMap = new HashMap<>();

    public static void initializeData() {
        initializeCities();
        addEdges();
    }
    public static void initializeCities() {
        cityMap.put("Rio de Janeiro", new Node("Rio de Janeiro", -22.9068, -43.1729));
        cityMap.put("São Paulo", new Node("São Paulo", -23.5505, -46.6333));
        cityMap.put("Belo Horizonte", new Node("Belo Horizonte", -19.9167, -43.9345));
        cityMap.put("Brasília", new Node("Brasília", -15.7801, -47.9292));
        cityMap.put("Salvador", new Node("Salvador", -12.9716, -38.5016));
        cityMap.put("Recife", new Node("Recife", -8.0476, -34.8770));
        cityMap.put("Fortaleza", new Node("Fortaleza", -3.7172, -38.5433));
        cityMap.put("Curitiba", new Node("Curitiba", -25.4275, -49.2731));
        cityMap.put("Porto Alegre", new Node("Porto Alegre", -30.0277, -51.2287));
        cityMap.put("Manaus", new Node("Manaus", -3.1190, -60.0217));
        cityMap.put("Belém", new Node("Belém", -1.4550, -48.5023));
        cityMap.put("Goiânia", new Node("Goiânia", -16.6809, -49.2533));
        cityMap.put("Cuiabá", new Node("Cuiabá", -15.6010, -56.0974));
        cityMap.put("Natal", new Node("Natal", -5.7945, -35.2110));
        cityMap.put("Florianópolis", new Node("Florianópolis", -27.5954, -48.5480));
        cityMap.put("Porto Velho", new Node("Porto Velho", -8.7619, -63.9034));
        cityMap.put("Boa Vista", new Node("Boa Vista", 2.8235, -60.6758));
        cityMap.put("Maringá", new Node("Maringá", -23.4323, -51.9375));
        cityMap.put("Cascavel", new Node("Cascavel", -24.9578, -53.4590));
        cityMap.put("Campo Grande", new Node("Campo Grande", -20.4697, -54.6201));
        cityMap.put("Feira de Santana", new Node("Feira de Santana", -12.2733, -38.9556));
    }

    public static void addEdges() {
        cityMap.get("Rio de Janeiro").addEdge(cityMap.get("São Paulo"), 429.1);
        cityMap.get("Rio de Janeiro").addEdge(cityMap.get("Belo Horizonte"), 339.0);

        cityMap.get("São Paulo").addEdge(cityMap.get("Belo Horizonte"), 584.3);
        cityMap.get("São Paulo").addEdge(cityMap.get("Curitiba"), 408.7);
        cityMap.get("São Paulo").addEdge(cityMap.get("Rio de Janeiro"), 429.1);

        cityMap.get("Belo Horizonte").addEdge(cityMap.get("Rio de Janeiro"), 339.0);
        cityMap.get("Belo Horizonte").addEdge(cityMap.get("São Paulo"), 584.3);
        cityMap.get("Belo Horizonte").addEdge(cityMap.get("Brasília"), 621.7);
        cityMap.get("Belo Horizonte").addEdge(cityMap.get("Feira de Santana"), 1236);

        cityMap.get("Brasília").addEdge(cityMap.get("Belo Horizonte"), 621.7);
        cityMap.get("Brasília").addEdge(cityMap.get("Goiânia"), 209.3);
        cityMap.get("Brasília").addEdge(cityMap.get("Cuiabá"), 1113.3);

        cityMap.get("Salvador").addEdge(cityMap.get("Recife"), 824.4);
        cityMap.get("Salvador").addEdge(cityMap.get("Feira de Santana"), 115);

        cityMap.get("Feira de Santana").addEdge(cityMap.get("Salvador"), 115);
        cityMap.get("Feira de Santana").addEdge(cityMap.get("Belo Horizonte"), 1236);

        cityMap.get("Recife").addEdge(cityMap.get("Salvador"), 824.4);
        cityMap.get("Recife").addEdge(cityMap.get("Fortaleza"), 806.3);

        cityMap.get("Fortaleza").addEdge(cityMap.get("Recife"), 806.3);
        cityMap.get("Fortaleza").addEdge(cityMap.get("Natal"), 515.3);

        cityMap.get("Curitiba").addEdge(cityMap.get("São Paulo"), 408.7);
        cityMap.get("Curitiba").addEdge(cityMap.get("Florianópolis"), 315);

        cityMap.get("Florianópolis").addEdge(cityMap.get("Curitiba"), 315);
        cityMap.get("Florianópolis").addEdge(cityMap.get("Porto Alegre"), 476.7);

        cityMap.get("Porto Alegre").addEdge(cityMap.get("Florianópolis"), 476.7);
        cityMap.get("Manaus").addEdge(cityMap.get("Porto Velho"), 682.2);
        cityMap.get("Manaus").addEdge(cityMap.get("Boa Vista"), 777.3);
        cityMap.get("Belém").addEdge(cityMap.get("Manaus"), 2116.3);
        cityMap.get("Maringá").addEdge(cityMap.get("Cascavel"), 168.8);
        cityMap.get("Maringá").addEdge(cityMap.get("São Paulo"), 631.3);
        cityMap.get("Maringá").addEdge(cityMap.get("Curitiba"), 420);
        cityMap.get("Maringá").addEdge(cityMap.get("Campo Grande"), 558);
        cityMap.get("Cascavel").addEdge(cityMap.get("Maringá"), 168.8);
        cityMap.get("Cascavel").addEdge(cityMap.get("Campo Grande"), 633.7);
        cityMap.get("Cascavel").addEdge(cityMap.get("Curitiba"), 502);
        cityMap.get("Campo Grande").addEdge(cityMap.get("Cascavel"), 633);
        cityMap.get("Campo Grande").addEdge(cityMap.get("São Paulo"), 987.8);
        cityMap.get("Campo Grande").addEdge(cityMap.get("Maringá"), 558);
    }

    public static List<Node> getListForGraph() {
        if (cityMap.isEmpty()) {
            initializeData();
        }
        return new ArrayList<>(cityMap.values());
    }

    public static List<Node> getNodesForGraph() {
        return getListForGraph();
    }

    public static List<Node> getNodesToVisit() {
        if (cityMap.isEmpty())
            initializeData();

        List<Node> list = new ArrayList<>();
        list.add(cityMap.get("São Paulo"));
        list.add(cityMap.get("Porto Alegre"));
        list.add(cityMap.get("Belo Horizonte"));

        return list;
    }

    public static Node findByName(String name) {
        Node city = cityMap.get(name);
        if (city == null)
            throw new RuntimeException("City not found!");

        return city;
    }
}