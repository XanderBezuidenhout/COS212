/*
	You may not change the signatures of any of the given methods.  You may 
	however add any additional methods and/or field(s) which you may require to aid 
	you in the completion of this practical.
 */

public class BPlusTree {
	int order;
	int minKeys;
	int maxKeys;
	BPlusNode root; // do not modify
	//BPTree<Integer,Integer> actualTree;

	public BPlusTree(int m) {
		/*
		The constructor.  Creates a BPlusTree object of order m,
		where m is passed as a parameter to the constructor. 
		You may assume that m >= 3.
		*/
		order = m;
		minKeys = (int) Math.ceil(m / 2.0) - 1;
		maxKeys = order - 1;
		root = new BPlusNode(m, true); /* root starts as leaf node and root's parent is null */
		//actualTree=new BPTree<>(m);
	}

	/* insert an element into the BPlusTree, you may assume duplicates will not be inserted. */
	public void insertElement(int element) 
	{
		// your code goes here
		root=root.insert(element);
	}

	/*  
	    This method should return the left-most leaf node in the tree.
		If the tree is empty, return null.
	 */
	public BPlusNode getFirstLeaf() 
	{
		BPlusNode currNode=root;
		while (!currNode.leaf&&currNode.branches[0]!=null)
		{
			currNode=currNode.branches[0];
		}
		return currNode;
		// your code goes here
	}
}
