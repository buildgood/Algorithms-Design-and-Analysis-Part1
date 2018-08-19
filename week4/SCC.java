import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class SCC {

    private static final int NUM_NODES = 875714;
    private static int order = 1;

    /*
     * First pass for the DFS. Calculate finish time for each node.
     */
    public static void firstPass(List<Integer>[] backward, int[] finishTime) {

        boolean[] visited = new boolean[NUM_NODES + 1];

        for(int i = NUM_NODES; i > 0; i--) {
            if(visited[i]) continue;
            visited[i] = true;
            findFinishTime(backward, i, finishTime, visited);
        }
    }

    /*
     * Using stack to implement iterative DFS. When popping nodes, if it's the first time we meet it,
     * we push it back to the stack.
     */
    public static void findFinishTime(List<Integer>[] backward, int index, int[] finishTime, boolean[] visited) {

        Set<Integer> set = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(index);
        while (!stack.isEmpty()) {
            int val = stack.pop();

            if(!set.contains(val)) {
                set.add(val);
                stack.push(val);
            } else {
                finishTime[order++] = val;
            }

            for(int node : backward[val]) {
                if(visited[node]) continue;
                stack.push(node);
                visited[node] = true;
            }
        }
    }

    /*
     * Second pass for the DFS. Traverse the nodes based on the reverse order of finish
     * time.
     */
    public static void secondPass(List<Integer>[] forward, int[] leadNodes, int[] finishTime) {

        for(int i = NUM_NODES; i > 0; i--) {
            if(leadNodes[finishTime[i]] > 0) continue;
            leadNodes[finishTime[i]] = i;
            findLeadNodes(forward, finishTime[i], leadNodes, i);
        }
    }

    /*
     *  Using stack to implement iterative DFS. All the nodes in one SCC have same lead node.
     */
    public static void findLeadNodes(List<Integer>[] forward, int index, int[] leadNodes, int lead) {

        Stack<Integer> stack = new Stack<>();
        stack.push(index);
        while(!stack.isEmpty()) {
            int val = stack.pop();
            for(int node : forward[val]) {
                if(leadNodes[node] > 0) continue;
                stack.push(node);
                leadNodes[node] = lead;
            }
        }

    }

    public static void main(String[] args) throws Exception {

        // Read the data from file input
        File file = new File("SCC.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input;

        // Use List to store edges
        List<Integer>[] forward = new List[NUM_NODES + 1];
        List<Integer>[] backward = new List[NUM_NODES + 1];

        for(int i = 0; i <= NUM_NODES; i++) {
            forward[i] = new ArrayList<>();
            backward[i] = new ArrayList<>();
        }

        while ((input = br.readLine()) != null) {
            int tail = Integer.valueOf(input.split(" ")[0]);
            int head = Integer.valueOf(input.split(" ")[1]);

            forward[tail].add(head);
            backward[head].add(tail);
        }

        // Get the finish time for each node
        int[] finishTime = new int[NUM_NODES + 1];
        firstPass(backward, finishTime);

        // Second pass for the DFS, get the lead node for each SCC
        int[] leadNodes = new int[NUM_NODES + 1];
        secondPass(forward, leadNodes, finishTime);

        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 1; i <= NUM_NODES; i++) {
            map.put(leadNodes[i], map.getOrDefault(leadNodes[i], 0) + 1);
        }

        List<Integer> list = new ArrayList<>();
        for(int num : map.values()) {
            list.add(num);
        }

        Collections.sort(list);
        for(int i = list.size() - 1; i >= list.size() - 5 && i >= 0; i--) {
            System.out.println(list.get(i));
        }
    }
}
