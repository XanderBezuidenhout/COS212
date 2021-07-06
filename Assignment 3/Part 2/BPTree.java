/**
 * Class for a B+ tree.
 * Since the structures and behaviours between internal nodes and leaf nodes are
 * different, there are different classes for each kind of node. However, both classes
 * share attributes in a common parent node class.
 * 
 * @param <TKey> the data type of the key
 * @param <TValue> the data type of the value
 */
@SuppressWarnings("unchecked")
public class BPTree<TKey extends Comparable<TKey>, TValue> {

	private BPTreeNode<TKey, TValue> root;
	private int debugSearch;
	private int debugDelete;
	
	public BPTree(int order) {
		this.root = new BPTreeLeafNode<TKey, TValue>(order);
		this.debugSearch = 0;
		this.debugDelete = 0;

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
	public void insert(TKey key, TValue value) {

		if (root != null) {
			root = root.insert(key, value); /* insert returns the new root to set in the BPTree class */
			root.checkSiblings();
			root.fixSequenceSetReferences();
		}
	}
	public void removeValue(TValue val)
	{
		if (root!=null)
		root.removeValue(val);
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
			root.fixSequenceSetReferences();
			root.checkSiblings();
		}
	}

	/**
	 * Return all associated head ValueNodes on the B+ tree in ascending key order.
	 */
	public ValueNode<TValue>[] values() {
		if (root != null) {
			ValueNode<TValue>[] valArray=root.values();
			return root.values();
		} else {
			return null;
		}
	}

	

}
