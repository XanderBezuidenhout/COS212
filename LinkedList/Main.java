import java.util.Arrays;

public class Main {


	public static void HeapTest()
	{
		/*** HEAP SORT ***/
		// Unsorted array
		Integer[] arraynum = new Integer[] { 2, 6, 10, 5, 3 };

		// Call heap sort with integers
		Sort.heapsort(arraynum, true);

		// Print sorted array
		System.out.println("Sorted : " + Arrays.toString(arraynum));

		// Unsorted array
		String[] arraystr = new String[] { "presence", "threshold", "download", "chemicals", "basics" };

		// Call heap sort with strings
		Sort.heapsort(arraystr, true);

		// Print sorted array
		System.out.println("Sorted : " + Arrays.toString(arraystr));

		//System.out.println("END OF HEAPSORT");
	}
	public static void MergeTest()
	{
		Integer[] arraynum = new Integer[] { 2, 6, 3, 5, 1 };

		int first = 0;
		int last = arraynum.length - 1;

		// Call merge sort with integers
		Sort.mergesort(arraynum, first, last, true);

		// Print sorted array
		System.out.println("Sorted : " + Arrays.toString(arraynum));

		// Unsorted array
		String[] arraystr = new String[] { "presence", "threshold", "download", "chemicals", "basics" };

		first = 0;
		last = arraystr.length - 1;

		// Call merge sort with strings
		Sort.mergesort(arraystr, first, last, true);

		// Print sorted array
		System.out.println("Sorted : " + Arrays.toString(arraystr));
	}

	public static void main(String[] args) 
	{
		HeapTest();
		MergeTest();
		

		/*** MERGE SORT ***/
		// Unsorted array
		

		/* Expected Output
		[2, 6, 10, 5, 3]
		[10, 6, 2, 5, 3]
		[6, 5, 2, 3, 10]
		[5, 3, 2, 6, 10]
		[3, 2, 5, 6, 10]
		[2, 3, 5, 6, 10]
		Sorted : [2, 3, 5, 6, 10]
		[presence, threshold, download, chemicals, basics]
		[threshold, presence, download, chemicals, basics]
		[presence, chemicals, download, basics, threshold]
		[download, chemicals, basics, presence, threshold]
		[chemicals, basics, download, presence, threshold]
		[basics, chemicals, download, presence, threshold]
		Sorted : [basics, chemicals, download, presence, threshold]
		[2, 6, 3, 5, 1]
		[2, 3, 6, 5, 1]
		[2, 3, 6, 1, 5]
		[1, 2, 3, 5, 6]
		Sorted : [1, 2, 3, 5, 6]
		[presence, threshold, download, chemicals, basics]
		[download, presence, threshold, chemicals, basics]
		[download, presence, threshold, basics, chemicals]
		[basics, chemicals, download, presence, threshold]
		Sorted : [basics, chemicals, download, presence, threshold]
		*/
	}
}
