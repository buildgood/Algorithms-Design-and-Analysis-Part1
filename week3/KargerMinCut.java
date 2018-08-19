import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class KargerMinCut {

    private static final int VERTICES = 200;
    private static final Random random = new Random();

    public static int minCut(Map<Integer, List<Integer>> graph, List<Integer> vertex) {
        while(vertex.size() > 2) {
            //random choose two vertices that will be merged
            int vertex1 = vertex.get(random.nextInt(vertex.size()));
            List<Integer> list1 = graph.get(vertex1);
            int vertex2 = list1.get(random.nextInt(list1.size()));
            if(!vertex.contains(vertex2) || vertex1 == vertex2) continue;
            List<Integer> list2 = graph.get(vertex2);
            //update vertex2's adjacent vertices and make them point to vertex1
            Iterator<Integer> ite = list2.iterator();
            while(ite.hasNext()) {
                int adjVertex = ite.next();
                List<Integer> adjList = graph.get(adjVertex);
                while(adjList.remove(new Integer(vertex2))) {
                    adjList.add(vertex1);
                }
                list1.add(adjVertex);
            }
            //remove self loop
            while(list1.remove(new Integer(vertex1)));
            while(list1.remove(new Integer(vertex2)));
            vertex.remove(new Integer(vertex2));
        }

        int minCut = graph.get(vertex.get(0)).size();
        return minCut;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("kargerMinCut.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Map<Integer, List<Integer>> graph = new HashMap<>();
        List<Integer> vertex = new ArrayList<>();
        String tmp;
        while((tmp = br.readLine()) != null) {
            String[] input = tmp.split("\\s+");
            List<Integer> list = new ArrayList<>();
            for(int i = 1; i < input.length; i++) {
                list.add(Integer.parseInt(input[i]));
            }
            vertex.add(Integer.parseInt(input[0]));
            graph.put(Integer.parseInt(input[0]), list);
        }
        int min = minCut(graph, vertex);
        System.out.println("Min Cut: " + min);
    }

}
