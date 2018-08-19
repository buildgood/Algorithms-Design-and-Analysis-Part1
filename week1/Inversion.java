import java.io.*;
import java.lang.*;
import java.util.*;

public class Inversion {
	
	public static long count(List<Integer> array) {
		int len = array.size();
		if(len == 1) return 0;
		if(len == 2) {
			if(array.get(0).compareTo(array.get(1)) < 0) return 0;
			else return 1;
		}
		return sort(array, 0, len - 1);
	}
	
	public static long sort(List<Integer> array, int lo, int hi) {
		if(lo >= hi) return 0;
		if(hi - lo == 1) {
			if(array.get(lo).compareTo(array.get(hi)) > 0) {
				Integer tmp = array.get(lo);
				array.set(lo, array.get(hi));
				array.set(hi, tmp);
				return 1;
			}
			else return 0;
		}
		int mid = (lo + hi) / 2;
		long left = sort(array, lo, mid);
		long right = sort(array, mid + 1, hi);
		long split = merge(array, lo, mid, hi);
		return left + right + split;
	}
	
	public static long merge(List<Integer> array, int lo, int mid, int hi) {
		List<Integer> array1 = new ArrayList<Integer>(array.subList(lo, mid + 1));
		List<Integer> array2 = new ArrayList<Integer>(array.subList(mid + 1, hi + 1));
		int size1 = array1.size();
		int size2 = array2.size();
		int index = lo;
		int index1 = 0;
		int index2 = 0;
		long split = 0;
		while(index1 < size1 && index2 < size2 && index < array.size()) {
			if(array1.get(index1).compareTo(array2.get(index2)) > 0) {
				split += size1 - index1;
				array.set(index, array2.get(index2));
				index ++;
				index2 ++;
			}
			else {
				array.set(index, array1.get(index1));
				index ++;
				index1 ++;
			}
		}
		if(index2 == size2) {
			for(int i = index1; i < size1; i++) {
				array.set(index, array1.get(i));
				index ++;
			}
		}
		return split;
	}
	
	public static void main(String[] args) throws Exception{
		
		File file = new File("IntegerArray.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		List<Integer> list = new ArrayList<Integer>();
		String tmp;
		while((tmp=br.readLine()) != null) {
			list.add(Integer.parseInt(tmp));
		}
		br.close();
		
		long res = count(list);
		System.out.println("Res:" + res);
	}
}
