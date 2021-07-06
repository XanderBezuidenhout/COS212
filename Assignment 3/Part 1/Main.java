public class Main {

	/* Returns a string representation of the values stored in a single ValueNode linked list (corresponding to one key)*/
	public static String getValuesLinkedList(ValueNode<Integer> node) {
		if (node == null) {
			return "null";
		}

		String o = "[";
		while (node.next != null) {
			o += node.value + ", ";
			node = node.next;
		}
		o += node.value + "]";
		return o;
	}

	/* Print an array of values in the order presented in the array*/
	public static void printValuesArray(ValueNode<Integer>[] arr) {
		if (arr != null) {
			String o = "Values: ";
			for (int i = 0; i < arr.length - 1; i++) {
				o += getValuesLinkedList(arr[i]) + ",";
			}
			o += getValuesLinkedList(arr[arr.length - 1]);
			System.out.println(o);
		} else {
			System.out.println("Values: null");
		}
	}

	public static void insertAll(BPTree<Integer, Integer> tree, Integer[] keys, Integer[] values) {
		for (int k = 0; k < keys.length; k++) {
			System.out.println("INSERT: (key, value) : (" + keys[k] + "," + values[k] + ")");
			if (keys[k]==8)
			{
				//this for breakpoint
				k=k;
			}
			tree.insert(keys[k], values[k]);
			tree.print();
			printValuesArray(tree.values());
			System.out.println("-----------------");
		}
	}

	public static void deleteValue(BPTree<Integer, Integer> tree, Integer val) {
		System.out.println("DELETE Key: " + val);
		if (val==6)
		{
			val=6;
		}
		tree.delete(val);
		tree.print();
		printValuesArray(tree.values());
		System.out.println("-----------------");
	}

	public static void searchForKey(BPTree<Integer, Integer> tree, Integer key) {
		String result = getValuesLinkedList(tree.search(key));
		if (result.equals("null")) {
			System.out.println("Key " + key + " not found!");
		} else {
			System.out.println("Found key " + key + " with values: " + result);
		}
		System.out.println();
	}

	public static void testOne()
	{
		Integer[] keys, values;
		BPTree<Integer, Integer> tree1 = new BPTree<Integer, Integer>(4); // A B+ Tree with order 4

		keys = new Integer[] { 8, 14, 2, 15, 3, 1, 16, 6, 5, 20, 27, 18, 4, 8, 8, 4, 4 };
		values = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
		insertAll(tree1, keys, values);

		System.out.println("###############################");
		System.out.println("Structure of the constructed tree is : ");
		tree1.print();
		printValuesArray(tree1.values());
		System.out.println("###############################");

		deleteValue(tree1, 16);
		deleteValue(tree1, 14);
		deleteValue(tree1, 15);
		deleteValue(tree1, 18);
		deleteValue(tree1, 20);
		deleteValue(tree1, 27);
		deleteValue(tree1, 8);
		deleteValue(tree1, 6);
	}

	public static void testTwo()
	{
		Integer[] keys, values;
		System.out.println("+++++++++++++++++++++++++++++++++ TREE 2 +++++++++++++++++++++++++++++++++++++++++");
		BPTree<Integer, Integer> tree2 = new BPTree<Integer, Integer>(5); // A B+ Tree with order 5

		keys = new Integer[] { 8, 14, 2, 15, 3, 1, 16, 6, 5, 20, 27, 18, 4 };
		values = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
		insertAll(tree2, keys, values);
		System.out.println("Results of first insertion:");
		tree2.print();

		keys = new Integer[] { 13, 22, 25, 23, 24, 37, 29, 19 };
		values = new Integer[] { 14, 15, 16, 17, 18, 19, 20, 21 };
		insertAll(tree2, keys, values);
		tree2.print();

		deleteValue(tree2, 2);

		deleteValue(tree2, 3);

		searchForKey(tree2, 4);

		deleteValue(tree2, 27);

		searchForKey(tree2, 27);
	}
	public static void main(String[] args) {
		

		/* Part 1 - Small tree
		 */
		//testOne();

		/* Part 2 - Bigger tree
		 */
		testTwo();
		/*
		INSERT: (key, value) : (8,1)
		Level 1 [8]
		
		Values: [1]
		-----------------
		INSERT: (key, value) : (14,2)
		Level 1 [8 | 14]
		
		Values: [1],[2]
		-----------------
		INSERT: (key, value) : (2,3)
		Level 1 [2 | 8 | 14]
		
		Values: [3],[1],[2]
		-----------------
		INSERT: (key, value) : (15,4)
		Level 1 [14]
		Level 2 [2 | 8]
		Level 2 [14 | 15]
		
		Values: [3],[1],[2],[4]
		-----------------
		INSERT: (key, value) : (3,5)
		Level 1 [14]
		Level 2 [2 | 3 | 8]
		Level 2 [14 | 15]
		
		Values: [3],[5],[1],[2],[4]
		-----------------
		INSERT: (key, value) : (1,6)
		Level 1 [3 | 14]
		Level 2 [1 | 2]
		Level 2 [3 | 8]
		Level 2 [14 | 15]
		
		Values: [6],[3],[5],[1],[2],[4]
		-----------------
		INSERT: (key, value) : (16,7)
		Level 1 [3 | 14]
		Level 2 [1 | 2]
		Level 2 [3 | 8]
		Level 2 [14 | 15 | 16]
		
		Values: [6],[3],[5],[1],[2],[4],[7]
		-----------------
		INSERT: (key, value) : (6,8)
		Level 1 [3 | 14]
		Level 2 [1 | 2]
		Level 2 [3 | 6 | 8]
		Level 2 [14 | 15 | 16]
		
		Values: [6],[3],[5],[8],[1],[2],[4],[7]
		-----------------
		INSERT: (key, value) : (5,9)
		Level 1 [3 | 6 | 14]
		Level 2 [1 | 2]
		Level 2 [3 | 5]
		Level 2 [6 | 8]
		Level 2 [14 | 15 | 16]
		
		Values: [6],[3],[5],[9],[8],[1],[2],[4],[7]
		-----------------
		INSERT: (key, value) : (20,10)
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 5]
		Level 3 [6 | 8]
		Level 2 [16]
		Level 3 [14 | 15]
		Level 3 [16 | 20]
		
		Values: [6],[3],[5],[9],[8],[1],[2],[4],[7],[10]
		-----------------
		INSERT: (key, value) : (27,11)
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 5]
		Level 3 [6 | 8]
		Level 2 [16]
		Level 3 [14 | 15]
		Level 3 [16 | 20 | 27]
		
		Values: [6],[3],[5],[9],[8],[1],[2],[4],[7],[10],[11]
		-----------------
		INSERT: (key, value) : (18,12)
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 5]
		Level 3 [6 | 8]
		Level 2 [16 | 20]
		Level 3 [14 | 15]
		Level 3 [16 | 18]
		Level 3 [20 | 27]
		
		Values: [6],[3],[5],[9],[8],[1],[2],[4],[7],[12],[10],[11]
		-----------------
		INSERT: (key, value) : (4,13)
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 3 [6 | 8]
		Level 2 [16 | 20]
		Level 3 [14 | 15]
		Level 3 [16 | 18]
		Level 3 [20 | 27]
		
		Values: [6],[3],[5],[13],[9],[8],[1],[2],[4],[7],[12],[10],[11]
		-----------------
		INSERT: (key, value) : (8,14)
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 3 [6 | 8]
		Level 2 [16 | 20]
		Level 3 [14 | 15]
		Level 3 [16 | 18]
		Level 3 [20 | 27]
		
		Values: [6],[3],[5],[13],[9],[8],[14, 1],[2],[4],[7],[12],[10],[11]
		-----------------
		INSERT: (key, value) : (8,15)
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 3 [6 | 8]
		Level 2 [16 | 20]
		Level 3 [14 | 15]
		Level 3 [16 | 18]
		Level 3 [20 | 27]
		
		Values: [6],[3],[5],[13],[9],[8],[15, 14, 1],[2],[4],[7],[12],[10],[11]
		-----------------
		INSERT: (key, value) : (4,16)
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 3 [6 | 8]
		Level 2 [16 | 20]
		Level 3 [14 | 15]
		Level 3 [16 | 18]
		Level 3 [20 | 27]
		
		Values: [6],[3],[5],[16, 13],[9],[8],[15, 14, 1],[2],[4],[7],[12],[10],[11]
		-----------------
		INSERT: (key, value) : (4,17)
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 3 [6 | 8]
		Level 2 [16 | 20]
		Level 3 [14 | 15]
		Level 3 [16 | 18]
		Level 3 [20 | 27]
		
		Values: [6],[3],[5],[17, 16, 13],[9],[8],[15, 14, 1],[2],[4],[7],[12],[10],[11]
		-----------------
		###############################
		Structure of the constructed tree is : 
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 3 [6 | 8]
		Level 2 [16 | 20]
		Level 3 [14 | 15]
		Level 3 [16 | 18]
		Level 3 [20 | 27]
		
		Values: [6],[3],[5],[17, 16, 13],[9],[8],[15, 14, 1],[2],[4],[7],[12],[10],[11]
		###############################
		DELETE Key: 16
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 3 [6 | 8]
		Level 2 [16 | 20]
		Level 3 [14 | 15]
		Level 3 [18]
		Level 3 [20 | 27]
		
		Values: [6],[3],[5],[17, 16, 13],[9],[8],[15, 14, 1],[2],[4],[12],[10],[11]
		-----------------
		DELETE Key: 14
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 3 [6 | 8]
		Level 2 [16 | 20]
		Level 3 [15]
		Level 3 [18]
		Level 3 [20 | 27]
		
		Values: [6],[3],[5],[17, 16, 13],[9],[8],[15, 14, 1],[4],[12],[10],[11]
		-----------------
		DELETE Key: 15
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 3 [6 | 8]
		Level 2 [20]
		Level 3 [18]
		Level 3 [20 | 27]
		
		Values: [6],[3],[5],[17, 16, 13],[9],[8],[15, 14, 1],[12],[10],[11]
		-----------------
		DELETE Key: 18
		Level 1 [14]
		Level 2 [3 | 6]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 3 [6 | 8]
		Level 2 [27]
		Level 3 [20]
		Level 3 [27]
		
		Values: [6],[3],[5],[17, 16, 13],[9],[8],[15, 14, 1],[10],[11]
		-----------------
		DELETE Key: 20
		Level 1 [6]
		Level 2 [3]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 2 [14]
		Level 3 [6 | 8]
		Level 3 [27]
		
		Values: [6],[3],[5],[17, 16, 13],[9],[8],[15, 14, 1],[11]
		-----------------
		DELETE Key: 27
		Level 1 [6]
		Level 2 [3]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5]
		Level 2 [8]
		Level 3 [6]
		Level 3 [8]
		
		Values: [6],[3],[5],[17, 16, 13],[9],[8],[15, 14, 1]
		-----------------
		DELETE Key: 8
		Level 1 [3 | 6]
		Level 2 [1 | 2]
		Level 2 [3 | 4 | 5]
		Level 2 [6]
		
		Values: [6],[3],[5],[17, 16, 13],[9],[8]
		-----------------
		DELETE Key: 6
		Level 1 [3 | 5]
		Level 2 [1 | 2]
		Level 2 [3 | 4]
		Level 2 [5]
		
		Values: [6],[3],[5],[17, 16, 13],[9]
		-----------------
		+++++++++++++++++++++++++++++++++ TREE 2 +++++++++++++++++++++++++++++++++++++++++
		INSERT: (key, value) : (8,1)
		Level 1 [8]
		
		Values: [1]
		-----------------
		INSERT: (key, value) : (14,2)
		Level 1 [8 | 14]
		
		Values: [1],[2]
		-----------------
		INSERT: (key, value) : (2,3)
		Level 1 [2 | 8 | 14]
		
		Values: [3],[1],[2]
		-----------------
		INSERT: (key, value) : (15,4)
		Level 1 [2 | 8 | 14 | 15]
		
		Values: [3],[1],[2],[4]
		-----------------
		INSERT: (key, value) : (3,5)
		Level 1 [8]
		Level 2 [2 | 3]
		Level 2 [8 | 14 | 15]
		
		Values: [3],[5],[1],[2],[4]
		-----------------
		INSERT: (key, value) : (1,6)
		Level 1 [8]
		Level 2 [1 | 2 | 3]
		Level 2 [8 | 14 | 15]
		
		Values: [6],[3],[5],[1],[2],[4]
		-----------------
		INSERT: (key, value) : (16,7)
		Level 1 [8]
		Level 2 [1 | 2 | 3]
		Level 2 [8 | 14 | 15 | 16]
		
		Values: [6],[3],[5],[1],[2],[4],[7]
		-----------------
		INSERT: (key, value) : (6,8)
		Level 1 [8]
		Level 2 [1 | 2 | 3 | 6]
		Level 2 [8 | 14 | 15 | 16]
		
		Values: [6],[3],[5],[8],[1],[2],[4],[7]
		-----------------
		INSERT: (key, value) : (5,9)
		Level 1 [3 | 8]
		Level 2 [1 | 2]
		Level 2 [3 | 5 | 6]
		Level 2 [8 | 14 | 15 | 16]
		
		Values: [6],[3],[5],[9],[8],[1],[2],[4],[7]
		-----------------
		INSERT: (key, value) : (20,10)
		Level 1 [3 | 8 | 15]
		Level 2 [1 | 2]
		Level 2 [3 | 5 | 6]
		Level 2 [8 | 14]
		Level 2 [15 | 16 | 20]
		
		Values: [6],[3],[5],[9],[8],[1],[2],[4],[7],[10]
		-----------------
		INSERT: (key, value) : (27,11)
		Level 1 [3 | 8 | 15]
		Level 2 [1 | 2]
		Level 2 [3 | 5 | 6]
		Level 2 [8 | 14]
		Level 2 [15 | 16 | 20 | 27]
		
		Values: [6],[3],[5],[9],[8],[1],[2],[4],[7],[10],[11]
		-----------------
		INSERT: (key, value) : (18,12)
		Level 1 [3 | 8 | 15 | 18]
		Level 2 [1 | 2]
		Level 2 [3 | 5 | 6]
		Level 2 [8 | 14]
		Level 2 [15 | 16]
		Level 2 [18 | 20 | 27]
		
		Values: [6],[3],[5],[9],[8],[1],[2],[4],[7],[12],[10],[11]
		-----------------
		INSERT: (key, value) : (4,13)
		Level 1 [3 | 8 | 15 | 18]
		Level 2 [1 | 2]
		Level 2 [3 | 4 | 5 | 6]
		Level 2 [8 | 14]
		Level 2 [15 | 16]
		Level 2 [18 | 20 | 27]
		
		Values: [6],[3],[5],[13],[9],[8],[1],[2],[4],[7],[12],[10],[11]
		-----------------
		Results of first insertion:
		Level 1 [3 | 8 | 15 | 18]
		Level 2 [1 | 2]
		Level 2 [3 | 4 | 5 | 6]
		Level 2 [8 | 14]
		Level 2 [15 | 16]
		Level 2 [18 | 20 | 27]
		
		INSERT: (key, value) : (13,14)
		Level 1 [3 | 8 | 15 | 18]
		Level 2 [1 | 2]
		Level 2 [3 | 4 | 5 | 6]
		Level 2 [8 | 13 | 14]
		Level 2 [15 | 16]
		Level 2 [18 | 20 | 27]
		
		Values: [6],[3],[5],[13],[9],[8],[1],[14],[2],[4],[7],[12],[10],[11]
		-----------------
		INSERT: (key, value) : (22,15)
		Level 1 [3 | 8 | 15 | 18]
		Level 2 [1 | 2]
		Level 2 [3 | 4 | 5 | 6]
		Level 2 [8 | 13 | 14]
		Level 2 [15 | 16]
		Level 2 [18 | 20 | 22 | 27]
		
		Values: [6],[3],[5],[13],[9],[8],[1],[14],[2],[4],[7],[12],[10],[15],[11]
		-----------------
		INSERT: (key, value) : (25,16)
		Level 1 [15]
		Level 2 [3 | 8]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5 | 6]
		Level 3 [8 | 13 | 14]
		Level 2 [18 | 22]
		Level 3 [15 | 16]
		Level 3 [18 | 20]
		Level 3 [22 | 25 | 27]
		
		Values: [6],[3],[5],[13],[9],[8],[1],[14],[2],[4],[7],[12],[10],[15],[16],[11]
		-----------------
		INSERT: (key, value) : (23,17)
		Level 1 [15]
		Level 2 [3 | 8]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5 | 6]
		Level 3 [8 | 13 | 14]
		Level 2 [18 | 22]
		Level 3 [15 | 16]
		Level 3 [18 | 20]
		Level 3 [22 | 23 | 25 | 27]
		
		Values: [6],[3],[5],[13],[9],[8],[1],[14],[2],[4],[7],[12],[10],[15],[17],[16],[11]
		-----------------
		INSERT: (key, value) : (24,18)
		Level 1 [15]
		Level 2 [3 | 8]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5 | 6]
		Level 3 [8 | 13 | 14]
		Level 2 [18 | 22 | 24]
		Level 3 [15 | 16]
		Level 3 [18 | 20]
		Level 3 [22 | 23]
		Level 3 [24 | 25 | 27]
		
		Values: [6],[3],[5],[13],[9],[8],[1],[14],[2],[4],[7],[12],[10],[15],[17],[18],[16],[11]
		-----------------
		INSERT: (key, value) : (37,19)
		Level 1 [15]
		Level 2 [3 | 8]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5 | 6]
		Level 3 [8 | 13 | 14]
		Level 2 [18 | 22 | 24]
		Level 3 [15 | 16]
		Level 3 [18 | 20]
		Level 3 [22 | 23]
		Level 3 [24 | 25 | 27 | 37]
		
		Values: [6],[3],[5],[13],[9],[8],[1],[14],[2],[4],[7],[12],[10],[15],[17],[18],[16],[11],[19]
		-----------------
		INSERT: (key, value) : (29,20)
		Level 1 [15]
		Level 2 [3 | 8]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5 | 6]
		Level 3 [8 | 13 | 14]
		Level 2 [18 | 22 | 24 | 27]
		Level 3 [15 | 16]
		Level 3 [18 | 20]
		Level 3 [22 | 23]
		Level 3 [24 | 25]
		Level 3 [27 | 29 | 37]
		
		Values: [6],[3],[5],[13],[9],[8],[1],[14],[2],[4],[7],[12],[10],[15],[17],[18],[16],[11],[20],[19]
		-----------------
		INSERT: (key, value) : (19,21)
		Level 1 [15]
		Level 2 [3 | 8]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5 | 6]
		Level 3 [8 | 13 | 14]
		Level 2 [18 | 22 | 24 | 27]
		Level 3 [15 | 16]
		Level 3 [18 | 19 | 20]
		Level 3 [22 | 23]
		Level 3 [24 | 25]
		Level 3 [27 | 29 | 37]
		
		Values: [6],[3],[5],[13],[9],[8],[1],[14],[2],[4],[7],[12],[21],[10],[15],[17],[18],[16],[11],[20],[19]
		-----------------
		Level 1 [15]
		Level 2 [3 | 8]
		Level 3 [1 | 2]
		Level 3 [3 | 4 | 5 | 6]
		Level 3 [8 | 13 | 14]
		Level 2 [18 | 22 | 24 | 27]
		Level 3 [15 | 16]
		Level 3 [18 | 19 | 20]
		Level 3 [22 | 23]
		Level 3 [24 | 25]
		Level 3 [27 | 29 | 37]
		
		DELETE Key: 2
		Level 1 [15]
		Level 2 [4 | 8]
		Level 3 [1 | 3]
		Level 3 [4 | 5 | 6]
		Level 3 [8 | 13 | 14]
		Level 2 [18 | 22 | 24 | 27]
		Level 3 [15 | 16]
		Level 3 [18 | 19 | 20]
		Level 3 [22 | 23]
		Level 3 [24 | 25]
		Level 3 [27 | 29 | 37]
		
		Values: [6],[5],[13],[9],[8],[1],[14],[2],[4],[7],[12],[21],[10],[15],[17],[18],[16],[11],[20],[19]
		-----------------
		DELETE Key: 3
		Level 1 [15]
		Level 2 [5 | 8]
		Level 3 [1 | 4]
		Level 3 [5 | 6]
		Level 3 [8 | 13 | 14]
		Level 2 [18 | 22 | 24 | 27]
		Level 3 [15 | 16]
		Level 3 [18 | 19 | 20]
		Level 3 [22 | 23]
		Level 3 [24 | 25]
		Level 3 [27 | 29 | 37]
		
		Values: [6],[13],[9],[8],[1],[14],[2],[4],[7],[12],[21],[10],[15],[17],[18],[16],[11],[20],[19]
		-----------------
		Found key 4 with values: [13]
		
		DELETE Key: 27
		Level 1 [15]
		Level 2 [5 | 8]
		Level 3 [1 | 4]
		Level 3 [5 | 6]
		Level 3 [8 | 13 | 14]
		Level 2 [18 | 22 | 24 | 27]
		Level 3 [15 | 16]
		Level 3 [18 | 19 | 20]
		Level 3 [22 | 23]
		Level 3 [24 | 25]
		Level 3 [29 | 37]
		
		Values: [6],[13],[9],[8],[1],[14],[2],[4],[7],[12],[21],[10],[15],[17],[18],[16],[20],[19]
		-----------------
		Key 27 not found!

		 */

	}
}