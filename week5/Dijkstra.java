import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class Dijkstra {

    private static final int NUM_NODE = 200;

    public static void dijkstra(int[][] edges, int[] distance) {

        for(int i = 2; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        Set<Integer> visited = new HashSet<>();

        visited.add(1);

        while (visited.size() < NUM_NODE) {
            int minEdge = Integer.MAX_VALUE;
            int minNode = Integer.MAX_VALUE;

            for(int source : visited) {
                for(int i = 1; i <= NUM_NODE; i++) {
                    if(!visited.contains(i) && edges[source][i] > 0) {
                        int sum = edges[source][i] + distance[source];
                        if(sum < distance[i]) {
                            distance[i] = sum;
                        }
                        if(distance[i] <= minEdge) {
                            minEdge = distance[i];
                            minNode = i;
                        }
                    }
                }
            }


            visited.add(minNode);
        }
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

        int[] distance = new int[NUM_NODE + 1];

        dijkstra(edges, distance);

        int[] index = new int[]{7,37,59,82,99,115,133,165,188,197};

        for(int i : index) {
            System.out.print(distance[i] + ",");
        }
    }
}
