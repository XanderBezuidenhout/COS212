/**
 * Class for a B+ tree.
 * Since the structures and behaviours between internal nodes and leaf nodes are
 * different, there are different classes for each kind of node. However, both classes
 * share attributes in a common parent node class.
 * 
 * @param <TKey> the data type of the key
 * @param <TValue> the data type of the value
 */
public class BPTree<TKey extends Comparable<TKey>, TValue> {

	public BPTreeNode<TKey, TValue> root;
	private int debugSearch;
	private int debugDelete;
	int minKeys;
	int maxKeys;
	int m;
	public BPTree(int order) {
		this.root = new BPTreeLeafNode<TKey, TValue>(order);
		this.debugSearch = 0;
		this.debugDelete = 0;
		minKeys = (int) Math.ceil(m / 2.0) - 1;
		maxKeys = order - 1;
		m=order;
	}
	

	/* Ignore this function as it is for marking purposes */
	public String getDebugString() {
		String res = "";
		if (root != null) {
			res = res + debugSearch + " " + debugDelete;
		}
		return res;
	}
	
	/* Resets the tree */
	public void clearTree() {
		this.root = new BPTreeLeafNode<TKey, TValue>(root.m);
	}

	/**
	 * Print all keys of the B+ tree
	 */
	public void print() {
		if (root != null) {
			root.print(root);
			System.out.println();
		}
	}

	/**
	 * Insert a new key and its associated value into the B+ tree.
	 */
	public void insertElement(TKey key) // add TValue back in for assignment
	{  
		if (root==null)
		{
			root=new BPTreeLeafNode<>(m);
		}
		root = root.insert(key,null); /* insert returns the new root to set in the BPTree class */
		
	}

	public BPTreeNode<TKey,TValue> getFirstLeaf()
	{
		BPTreeNode<TKey,TValue> currNode=root;
		while (!currNode.leaf)
		{
			currNode=(BPTreeNode<TKey, TValue>) ((BPTreeInnerNode<TKey,TValue>) currNode).references[0];
		}
		return currNode;
	}
	/**
	 * Search a key value on the B+ tree and return its associated value.
	 */
	public ValueNode<TValue> search(TKey key) { 
		if (root != null) {
			debugSearch++; // do not remove
			return root.search(key);
		} else
			return null;
	}

	/**
	 * Delete a key and its associated value from the B+ tree.
	 */
	public void delete(TKey key) {

		if (root != null) {
			debugDelete++; // do not remove
			root = root.delete(key); /* the delete of the inner node or leaf node returns the new root to set in the BPTree class */
		}
	}

	/**
	 * Return all associated head ValueNodes on the B+ tree in ascending key order.
	 */
	public ValueNode<TValue>[] values() {
		if (root != null) {
			return root.values();
		} else {
			return null;
		}
	}

}
