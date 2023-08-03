public class Main {
	
	//Some helper functions to help you test
	
	public static void searchElement(BST<Integer> tree, Integer element) {
		if (tree.isEmpty())
			System.out.println("Tree is empty");
		else {
			Integer result = tree.search(element);
			if (result != null)
				System.out.println("Found element " + result);
			else
				System.out.println("Element " + element + " not found");
		}
	}

	public static void deleteElement(BST<Integer> tree, Integer element, String type) {
		if (tree.isEmpty())
			System.out.println("Tree is empty");
		else {
			boolean result = false;
			if (type.trim().equals("m"))
				result = tree.deleteByMerge(element);
			else if (type.equals("c"))
				result = tree.deleteByCopy(element);

			if (result)
				System.out.println("Deleted element " + element);
			else
				System.out.println("Element " + element + " not found for deletion");
		}
	}

	public static void printTree(BST<Integer> tree) {
		String result;
		System.out.println("Binary Search Tree Content:");
		result = tree.inorder(tree.root);
		System.out.println(result);
	}

	public static void main(String[] args) {
		// Practical 3 - Some code to help you test (by no means exhaustive)

		BST<Integer> tree = new BST<Integer>();

		System.out.println("Tree is empty: " + tree.isEmpty());

		tree.insert(8);
		tree.insert(5);
		tree.insert(12);
		tree.insert(16);
		tree.insert(13);
	//tree.insert(200);
		//tree.insert(200);


		printTree(tree); // print tree

		BST<Integer> mirror = new BST<Integer>();
		mirror.root = tree.mirror();

		printTree(mirror); // print mirrored tree

		searchElement(tree, 10); // search for 10

		tree.insert(14);
		tree.insert(7);
		tree.insert(10);
		tree.insert(3);

		searchElement(tree, 10); // search for 10

		BST<Integer> clone = new BST<Integer>();
		clone.root = tree.clone();
		
		System.out.println("\nTree before deletion: ");
		printTree(tree); // print tree before deletion
		//System.out.println("Clone: ");
		//printTree(clone);
		deleteElement(tree, 8, "m");

		printTree(tree); // print tree after delete by merge

		deleteElement(clone, 8, "c");

		printTree(clone); // print clone after delete by copy

		System.out.println("Predecessor of 7: " + tree.getPredecessor(7));
		System.out.println("Predecessor of 16: " + tree.getPredecessor(16));
		System.out.println("Successor of 7: " + tree.getSuccessor(7));
		System.out.println("Successor of 16: " + tree.getSuccessor(16));
		deleteElement(clone, 12, "c");
		printTree(clone);

		/* Expected Output:
		 
		Tree is empty: true
		Binary Search Tree Content:
		5 [L: null]  [R: null] 
		8 [L: 5]  [R: 12] 
		12 [L: null]  [R: 16] 
		13 [L: null]  [R: null] 
		16 [L: 13]  [R: null] 

		Binary Search Tree Content:
		16 [L: null]  [R: 13] 
		13 [L: null]  [R: null] 
		12 [L: 16]  [R: null] 
		8 [L: 12]  [R: 5] 
		5 [L: null]  [R: null] 

		Element 10 not found
		Found element 10

		Tree before deletion: 
		Binary Search Tree Content:
		3 [L: null]  [R: null] 
		5 [L: 3]  [R: 7] 
		7 [L: null]  [R: null] 
		8 [L: 5]  [R: 12] 
		10 [L: null]  [R: null] 
		12 [L: 10]  [R: 16] 
		13 [L: null]  [R: 14] 
		14 [L: null]  [R: null] 
		16 [L: 13]  [R: null] 

		Deleted element 8
		Binary Search Tree Content:
		3 [L: null]  [R: null] 
		5 [L: 3]  [R: 7] 
		7 [L: null]  [R: 12] 
		10 [L: null]  [R: null] 
		12 [L: 10]  [R: 16] 
		13 [L: null]  [R: 14] 
		14 [L: null]  [R: null] 
		16 [L: 13]  [R: null] 

		Deleted element 8
		Binary Search Tree Content:
		3 [L: null]  [R: null] 
		5 [L: 3]  [R: null] 
		7 [L: 5]  [R: 12] 
		10 [L: null]  [R: null] 
		12 [L: 10]  [R: 16] 
		13 [L: null]  [R: 14] 
		14 [L: null]  [R: null] 
		16 [L: 13]  [R: null] 

		Predecessor of 7: 5
		Predecessor of 16: 14
		Successor of 7: 10
		Successor of 16: null
		
		*/

}
}
