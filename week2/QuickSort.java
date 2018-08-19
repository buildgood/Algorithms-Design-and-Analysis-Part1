import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class QuickSort {
	
	public static long count = 0;
	
	public static void sort(List<Integer> list) {
		int len = list.size();
		quicksort(list, 0, len - 1);
	}
	
	public static void quicksort(List<Integer> list, int lo, int hi) {
		if(lo >= hi) return;
		int k = partition(list, lo, hi);
		quicksort(list, lo, k - 1);
		quicksort(list, k + 1, hi);
	}
	
	public static int partition(List<Integer> list, int lo, int hi) {
		if(lo >= hi) return lo;
		int key = choose(list, lo, (lo + hi)/2, hi); //get the median
		swap(list, lo, key);
		int pivot = list.get(lo);
		int i = lo + 1;
		for(int j = lo + 1; j <= hi; j++) {
			if(list.get(j) < pivot) {
				swap(list, i, j);
				i++;
			}
		}
		swap(list, lo, i - 1);
		count += hi - lo;
		return i - 1;
	}
	
	public static void swap(List<Integer> list, int i, int j) {
		int tmp = list.get(i);
		list.set(i,  list.get(j));
		list.set(j, tmp);
	}
	
	public static int choose(List<Integer> list, int lo, int mid, int hi) {
		int[] key = new int[3];
		int[] value = new int[3];
		key[0] = lo; key[1] = mid; key[2] = hi;
		value[0] = list.get(lo); value[1] = list.get(mid); value[2] = list.get(hi);
		for(int i = 0; i < 3; i++)
			for(int j = i; j < 3; j++) {
				if(value[i] > value[j]) {
					int tmp = value[i];
					value[i] = value[j];
					value[j] = tmp;
					tmp = key[i];
					key[i] = key[j];
					key[j] = tmp;
				}
			}
		return key[1];
	}

	public static void main(String[] args) throws Exception{
		File file = new File("QuickSort.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		List<Integer> list = new ArrayList<Integer>();
		String tmp;
		while((tmp=br.readLine()) != null) {
			list.add(Integer.parseInt(tmp));
		}
		br.close();
		
		sort(list);
		System.out.println(count);
	}
}
