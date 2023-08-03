public class Main {
	
	//these methods can help you test your code
	
	public static void firstKey(SkipList<Integer> skiplist) {
		if (skiplist.isEmpty())
			System.out.println("List is empty");
		else
			System.out.println("First key : " + skiplist.first());
	}

	public static void pathToLastKey(SkipList<Integer> skiplist) {
		if (skiplist.isEmpty())
			System.out.println("List is empty");
		else
			System.out.println("Path to last key : " + skiplist.getPathToLastNode());
	}

	public static void deleteKey(SkipList<Integer> skiplist, Integer key) {
		if (skiplist.isEmpty())
			System.out.println("List is empty");
		else {
			boolean result = skiplist.delete(key);
			if (result)
				System.out.println("Deleted key " + key);
			else
				System.out.println("Key " + key + " not found for deletion");
		}
	}

	public static void searchKey(SkipList<Integer> skiplist, Integer key) {
		if (skiplist.isEmpty())
			System.out.println("List is empty");
		else {
			Integer result = skiplist.search(key);
			if (result != null)
				System.out.println("Found key " + result);
			else
				System.out.println("Key " + key + " not found");
		}
	}

	public static void printList(SkipList<Integer> skiplist) 
	{
		System.out.println();
		for (int i = 0; i < skiplist.maxLevel; i++) 
		{
			SkipListNode<Integer> node = skiplist.root[i];
			System.out.print("Level " + i + ": ");
			while (node != null) {
				System.out.print(node.key + " ");
				node = node.next[i];
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// Some code to help you test
		SkipList<Integer> skipList = new SkipList<Integer>();
		
		skipList.insert(5);
		skipList.insert(55);
		skipList.insert(10);
		skipList.insert(19);
		skipList.insert(22);
		skipList.insert(3);
		skipList.insert(3);
		skipList.insert(60);
		skipList.insert(99);
		skipList.insert(29);
		
		printList(skipList);
		firstKey(skipList);
		pathToLastKey(skipList);
		searchKey(skipList, 55);
		
	//	System.out.println(skipList.maxLevel);
		deleteKey(skipList, 55);
		deleteKey(skipList, 3);
		deleteKey(skipList, 99);
		deleteKey(skipList, 1000);
		
		printList(skipList);
		searchKey(skipList, 55);
		firstKey(skipList);
		pathToLastKey(skipList);
		
		/*Expected output

		Level 0: 3 5 10 19 22 29 55 60 99 
		Level 1: 3 10 19 29 55 99 
		Level 2: 29 55 99 
		Level 3: 29 
		
		First key : 3
		Path to last key : [29][55][99]
		Found key 55
		Deleted key 55
		Deleted key 3
		Deleted key 99
		Key 1000 not found for deletion
		
		Level 0: 5 10 19 22 29 60 
		Level 1: 10 19 29 
		Level 2: 29 
		Level 3: 29 
		
		Key 55 not found
		First key : 5
		Path to last key : [29][60]

		*/
	}
}
