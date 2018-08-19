/**
 * Construct a chaining hash table containing buckets of size 20000;
 * Mapping input numbers to corresponding bucket (getting index of
 * bucket) using function (num / 20000).
 *
 * And for each number x, the y should be in the the same bucket of -x,
 * or its neighboring bucket. So for each number x, we just need to
 * find y from three bucket (-x / 20000 - 1, -x / 20000, -x / 20000 +1).
 *
 * Since there is a small number of data mapping to each bucket, the time
 * complexity is O(n).
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TwoSum {

    private static final long LOWER_BOUND = -10000L;
    private static final long UPPER_BOUND = 10000L;
    private static final long RANGE = 20000L;

    private ArrayList<Long> readData() throws IOException {

        File file = new File("algo1-programming_prob-2sum.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input;
        ArrayList<Long> array = new ArrayList<>();

        while ((input = br.readLine()) != null) {
            long num = Long.parseLong(input);
            array.add(num);
        }

        br.close();
        return array;
    }

    public HashMap<Long, ArrayList<Long>> buildMap(ArrayList<Long> arrayList) {

        HashMap<Long, ArrayList<Long>> map = new HashMap<>();

        for (long num : arrayList) {
            long index = num / RANGE;

            if (!map.containsKey(index)) {
                map.put(index, new ArrayList<>());
            }

            ArrayList<Long> array = map.get(index);
            array.add(num);
        }
        return map;

    }

    public int twoSum(ArrayList<Long> arrayList, HashMap<Long, ArrayList<Long>> map) {

        HashSet<Long> result = new HashSet<>();

        for (long num1 : arrayList) {
            long index = -num1 / RANGE;

            for (long i = index - 1; i <= index + 1; i++) {
                ArrayList<Long> array = map.get(i);

                if (array != null) {
                    for (long num2 : array) {
                        long sum = num1 + num2;
                        if (num1 != num2 && LOWER_BOUND <= sum && sum <= UPPER_BOUND) {
                            result.add(sum);
                        }
                    }
                }
            }
        }

        return result.size();
    }

    public static void main(String[] args) throws Exception {

        TwoSum obj = new TwoSum();
        ArrayList<Long> inputArray = obj.readData();
        HashMap<Long, ArrayList<Long>> map = obj.buildMap(inputArray);
        int result = obj.twoSum(inputArray, map);

        System.out.println(result);
    }

}
