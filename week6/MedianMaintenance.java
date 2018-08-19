/**
 * Use a max heap to store left half of the numbers. Use
 * a min heap to store right half of the numbers.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;

public class MedianMaintenance {

    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;
    private static int SIZE = 10000;

    public MedianMaintenance() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    }

    private int[] readData() throws IOException {

        int[] medians = new int[SIZE];

        File file = new File("Median.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input;
        int size = 0;

        while ((input = br.readLine()) != null) {
            int num = Integer.parseInt(input);
            insertNum(num);
            medians[size++] = getMedian();
        }

        return medians;
    }

    private void insertNum(int num) {

        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }

        if (maxHeap.size() - minHeap.size() >= 2) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() - maxHeap.size() >= 2) {
            maxHeap.offer(minHeap.poll());
        }
    }

    private int getMedian() {
        if (maxHeap.size() >= minHeap.size()) {
            return maxHeap.peek();
        } else {
            return minHeap.peek();
        }
    }

    public static void main(String[] args) throws Exception {

        MedianMaintenance obj = new MedianMaintenance();
        int[] medians = obj.readData();
        int result = 0;

        for(int num: medians) {
            result += num;
            result %= SIZE;
        }

        System.out.println(result);

    }
}
