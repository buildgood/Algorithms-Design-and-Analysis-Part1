import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.PriorityQueue;

public class Dijkstra {

    /**
     * The Node class represents a node with label
     * and distance from source.
     */
    private static class Node {
        private int vertex;
        private int distance;

        public Node(int vertex) {
            this.vertex = vertex;
            this.distance = Integer.MAX_VALUE;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }

    private static final int NUM_NODE = 200;

    public static Node[] dijkstra(int[][] edges) {

        Node[] nodes = new Node[NUM_NODE + 1];
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> (a.distance - b.distance));

        for (int i = 1; i <= NUM_NODE; i++) {
            nodes[i] = new Node(i);
            if (i == 1) {
                nodes[i].setDistance(0);
            }
            pq.offer(nodes[i]);
        }

        while (pq.size() > 0) {
            Node node = pq.poll();

            for (int j = 1; j <= NUM_NODE; j++) {
                if (node.vertex == j) continue;
                if (edges[node.vertex][j] > 0) {
                    int sum = edges[node.vertex][j] + nodes[node.vertex].distance;
                    if (sum < nodes[j].distance) {
                        pq.remove(nodes[j]);
                        nodes[j].setDistance(sum);
                        pq.offer(nodes[j]);
                    }
                }
            }
        }

        return nodes;

    }

    public static void main(String[] args) throws Exception {
        File file = new File("dijkstraData.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input;

        int[][] edges = new int[NUM_NODE + 1][NUM_NODE + 1];

        while ((input = br.readLine()) != null) {
            String[] neighbor = input.split("\t");

            int source = Integer.valueOf(neighbor[0]);

            for(int i = 1; i < neighbor.length; i++) {
                int node = Integer.valueOf(neighbor[i].split(",")[0]);
                int length = Integer.valueOf(neighbor[i].split(",")[1]);

                edges[source][node] = length;
            }
        }

        Node[] nodes = dijkstra(edges);

        int[] index = new int[]{7,37,59,82,99,115,133,165,188,197};

        for(int i : index) {
            System.out.print(nodes[i].distance + " ");
        }
    }
}
