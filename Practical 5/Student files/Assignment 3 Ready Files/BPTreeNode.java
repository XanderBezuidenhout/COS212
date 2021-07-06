/**
 * A B+ tree generic node
 * Abstract class with common methods and data. Each kind of node implements
 * this class.
 * 
 * @param <TKey>
 *            the data type of the key
 */
@SuppressWarnings("unchecked")
abstract class BPTreeNode<TKey extends Comparable<TKey>, TValue> {

	protected Object[] references;
	public Object[] keys;
	public boolean leaf=false;
	protected int keyTally;
	protected int m;
	protected BPTreeNode<TKey, TValue> parentNode;
	protected BPTreeNode<TKey, TValue> leftSibling;
	protected BPTreeNode<TKey, TValue> rightSibling;
	protected static int level = 0; // do not modify this variable's value as it is used for printing purposes. You can create another variable with a different name if you need to store the level.
	int minKeys;
	int maxKeys;
	protected BPTreeNode() {
		//this.m = order;
		this.keyTally = 0;
		this.parentNode = null;
		this.leftSibling = null;
		this.rightSibling = null;
		
	}

	public int getKeyCount() {
		return this.keyTally;
	}

	@SuppressWarnings("unchecked")
	public TKey getKey(int index) {
		return (TKey) this.keys[index];
	}

	public void setKey(int index, TKey key) {
		this.keys[index] = key;
	}

	public BPTreeNode<TKey, TValue> getParent() {
		return this.parentNode;
	}

	public void setParent(BPTreeNode<TKey, TValue> parent) {
		this.parentNode = parent;
	}

	public abstract boolean isLeaf();

	/**
	 * Print all nodes in a subtree rooted with this node
	 */
	@SuppressWarnings("unchecked")
	public void print(BPTreeNode<TKey, TValue> node) {
		level++;
		if (node != null) {
			System.out.print("Level " + level + " ");
			node.printKeys();
			System.out.println();

			// If this node is not leaf, then
			// print all the subtrees rooted with this node.
			if (!node.isLeaf()) {
				BPTreeInnerNode<TKey, TValue> inner = (BPTreeInnerNode<TKey, TValue>) node;
				for (int j = 0; j < (node.m); j++) {
					this.print((BPTreeNode<TKey, TValue>) inner.references[j]);
				}
			}
		}
		level--;
	}

	/**
	 * Print all the keys in this node
	 */
	protected void printKeys() {
		System.out.print("[");
		for (int i = 0; i < this.getKeyCount() - 1; i++) {
			System.out.print(this.keys[i] + " | ");
		}
		if (this.getKeyCount() - 1 >= 0) {
			System.out.print(this.keys[this.getKeyCount() - 1]);
		}

		System.out.print("]");
	}

	public String toString() {
		String o = "[";
		for (int i = 0; i < this.keyTally - 1; i++) {
			o += this.keys[i] + " | ";
		}
		if (this.keyTally - 1 >= 0) {
			o += this.keys[this.keyTally - 1];
		}
		o += "]";
		return o;
	}
	////// You may not change any code above this line you may add extra variables if need be //////

	////// Implement the functions below this line //////

	/**
	 * Search a key value on the B+ tree and return its associated ValueNode. If not found return null
	 */
	public ValueNode<TValue> search(TKey key) 
	{
		return null;
	}
	/**
	 * Insert a new key and its associated value into the B+ tree. Returns the updated root of the tree.
	 */
	abstract public BPTreeNode<TKey, TValue> insert(TKey key, ValueNode<TValue> value);



	/**
	 * Delete a key and its associated value from the B+ tree. Returns the updated root of the tree.
	 */
	public BPTreeNode<TKey, TValue> delete(TKey key) 
	{
		return null; //not used in this practical
	}

	/**
	 * Return all associated head ValueNodes on the B+ tree in ascending key order. Return null if tree is empty
	 */
	@SuppressWarnings("unchecked")
	public ValueNode<TValue>[] values() 
	{
		return null; //not currently used
	}

	protected int findWithinNode(TKey key)
	{
		for (int i=0;i<keyTally;i++)
		{
			if (((TKey) keys[i]).equals(key))
			{
				return i;
			}
		}
		return -1;
	}

	public void moveArrayRight(int startindex)
	{
		for (int i=keyTally-1;i>=startindex;i++)
		{
			keys[i+1]=keys[i];
			if (!isLeaf())
			{
				((BPTreeInnerNode<TKey,TValue>) this).references[i+2]=((BPTreeInnerNode<TKey,TValue>) this).references[i+1];
			}
			else
			{
				((BPTreeLeafNode<TKey,TValue>) this).values[i+1]=((BPTreeLeafNode<TKey,TValue>) this).values[i];
			}
		}
	}

	public void moveArrayLeft(int startindex)
	{
		for (int i=keyTally-1;i>=startindex;i++)
		{
			keys[i]=keys[i+1];
			if (!isLeaf())
			{
				((BPTreeInnerNode<TKey,TValue>) this).references[i]=((BPTreeInnerNode<TKey,TValue>) this).references[i+1];
			}
			else
			{
				((BPTreeLeafNode<TKey,TValue>) this).values[i]=((BPTreeLeafNode<TKey,TValue>) this).values[i+1];
			}
		}
	}

}