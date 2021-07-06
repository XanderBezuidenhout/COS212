import java.util.Arrays;

public class Sort {

	////// Implement the functions below this line //////
	/********** HEAP **********/
	public static <T extends Comparable<? super T>> void heapsort(T[] data, boolean debug) 
	{
		// Your code here
		for (int i = data.length/2 - 1; i >= 0; --i)
		{
			movedown(data,i,data.length-1,debug);
		}	
		for (int i = data.length-1; i>=1; i--) 
		{
			swap(data, i, 0);
			movedown(data, 0, i-1, debug);	
		}
	}

	private static <T extends Comparable<? super T>> void movedown(T[] data, int first, int last, boolean debug) { //big Floyd algorithm
			int largest = 2*first + 1;
			while (largest <= last) 
			{
				largest=getLargestChildIndex(first, data, last);
				if (data[first].compareTo(data[largest]) < 0) 
				{
				
					swap(data,first,largest); // if necessary, swap values
					first = largest; // and move down;
					largest = 2*first + 1;
				
				}
				else largest = last + 1;// to exit the loop: the heap property
			}
			// DO NOT MOVE OR REMOVE!
		if (debug)
		{System.out.println(Arrays.toString(data));}
			//return data;
	}

	private static <T extends Comparable<? super T>> T[] swap(T[] data,int Index1, int Index2)
    {
        if (Index1>=data.length||Index2>=data.length)
        {
            return data;
        }
        T temp=data[Index1];
        data[Index1]=data[Index2];
        data[Index2]=temp;
		return data;
    }

	private static <T extends Comparable<? super T>> boolean hasChild(T[] data,int Index)
	{
		return (Index*2+1<data.length);
	}

	private static <T extends Comparable<? super T>> int getNthChildIndex(int Index,int numchild)
	{
		return Index*2+numchild;
	}

	private static <T extends Comparable<? super T>> int getLargestChildIndex(int index,T[] data,int last)
	{
		if (index>data.length||!hasChild(data, index))
		{
			return -1;
		}
		int largest=index*2+1;
		if (largest < last &&data[largest].compareTo(data[largest+1]) < 0)
		{
			largest++;
		}
		return largest;
	}
	/********** MERGE **********/


	public static <T extends Comparable<? super T>> void mergesort(T[] data, int first, int last, boolean debug) 
	{
		// Your code here
		if (first<last)
		{
			int mid=(first+last)/2;
			mergesort(data, first, mid, debug);
			mergesort(data, mid+1, last, debug);
			merge(data, first, last, debug);
		}
	}

	private static <T extends Comparable<? super T>> void merge(T[] data, int first, int last, boolean debug) {
		// Your code here
		int mid=(first+last)/2;
		//int iterator1=0;
		int iterator2=first;
		int iterator3=mid+1;
		LinkedList<T> temp=new LinkedList<>();
		
		while(iterator2<=mid&&iterator3<=last)
		{
			if (data[iterator2].compareTo(data[iterator3])<0)
			{
				temp.add(data[iterator2++]);
			}
			else
			{
				temp.add(data[iterator3++]);
			}
		}
		while (iterator2<=mid)
		{
			temp.add(data[iterator2++]);
		}
		while (iterator3<=last)
		{
			temp.add(data[iterator3++]);
		}
		for (int i = first; i <= last; i++) 
		{
			data[i]=temp.poll();
		}

		// DO NOT MOVE OR REMOVE!
		if (debug)
			System.out.println(Arrays.toString(data));
	}

}

