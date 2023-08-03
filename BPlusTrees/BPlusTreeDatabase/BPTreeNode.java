
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

	protected Object[] keys;
	protected int keyTally;
	protected int m;
	protected int maxKeys;
	protected int minKeys;
	protected TKey nullnode;
	protected BPTreeNode<TKey, TValue> parentNode;
	protected BPTreeNode<TKey, TValue> leftSibling;
	protected BPTreeNode<TKey, TValue> rightSibling;
	protected static int level = 0; // do not modify this variable's value as it is used for printing purposes. You can create another variable with a different name if you need to store the level.

	protected BPTreeNode() {
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

	////// You may not change any code above this line you may add extra variables if need be //////

	////// Implement the functions below this line //////

	/**
	 * Search a key value on the B+ tree and return its associated ValueNode. If not found return null
	 */
	
	public ValueNode<TValue> search(TKey key) 
	{
		BPTreeLeafNode<TKey,TValue> currNode=goDownFarLeft();
		while (currNode!=null)
		{
			int keyindex=currNode.findWithinNode(key);
			if (keyindex!=-1)
			{
				return currNode.values[keyindex];
			}
			currNode=(BPTreeLeafNode<TKey,TValue>) currNode.rightSibling;
		}
		return null;
	}

	/**
	 * Insert a new key and its associated value into the B+ tree. Returns the updated root of the tree.
	 */
	public abstract BPTreeNode<TKey, TValue> insert(TKey key, TValue value);



	/**
	 * Delete a key and its associated value from the B+ tree. Returns the updated root of the tree.
	 */
	public BPTreeNode<TKey, TValue> delete(TKey key) 
	{
		BPTreeLeafNode<TKey,TValue> toDelete= findNodeInSequenceSet(key);
		
		if (toDelete==null)
		{
			return findRoot();
		}
		return toDelete.deleteKey(key);
	}

	/**
	 * Return all associated head ValueNodes on the B+ tree in ascending key order. Return null if tree is empty
	 */
	//@SuppressWarnings("unchecked")
	public ValueNode<TValue>[] values() 
	{
		BPTreeLeafNode<TKey,TValue> currNode=goDownFarLeft();
		if (currNode==null)
		{
			return null;
		}
		return currNode.getValues();
	}

	public void moveArrayRight(int startindex)
	{
		//int keyTally=this.keyTally;
		
		for (int i=keyTally-1;i>=startindex;i--)
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
		if (startindex==0&&!isLeaf())
		{
				((BPTreeInnerNode<TKey,TValue>) this).references[1]=((BPTreeInnerNode<TKey,TValue>) this).references[0];
		}
		if (isLeaf())
		{
			((BPTreeLeafNode<TKey,TValue>) this).values[startindex]=null;
		}
		
		
	}

	public void moveArrayLeft(int startindex)
	{
		for (int i=startindex,j=startindex+1;i<keyTally;i++,j++)
		{
			keys[i]=keys[i+1];
			if (!isLeaf())
			{	
				((BPTreeInnerNode<TKey,TValue>) this).references[j]=((BPTreeInnerNode<TKey,TValue>) this).references[j+1];
			}
			else
			{
				((BPTreeLeafNode<TKey,TValue>) this).values[i]=((BPTreeLeafNode<TKey,TValue>) this).values[i+1];
			}
		}
		if (!isLeaf())
		{
			if (startindex==0)
			{
					//((BPTreeInnerNode<TKey,TValue>) this).references[1]=((BPTreeInnerNode<TKey,TValue>) this).references[0];
			}
			((BPTreeInnerNode<TKey,TValue>) this).references[keyTally]=null;
		}
		else
		{
			((BPTreeLeafNode<TKey,TValue>) this).values[keyTally]=null;
		}
	}

	protected abstract void handleFullNode();

	protected int findWithinNode(TKey key)
	{
		if (key==null)
		{
			return -1;
		}
		for (int i=0;i<keyTally;i++)
		{
			if ((keys[i]).equals(key))
			{
				return i;
			}
		}
		return -1;
	}

	protected void clearKeys()
	{
		for (int i = 0; i < keyTally; i++) 
		{
			//keys[i]=nullnode;
		}
	}
	public abstract void setChild(int index, BPTreeNode<TKey, TValue> child);
	public abstract BPTreeNode<TKey,TValue>insertUpwards(TKey key,BPTreeNode<TKey,TValue> rightnode);

	public BPTreeNode<TKey,TValue> findRoot()
	{
		BPTreeNode<TKey,TValue> currNode=this;
		while (currNode.parentNode!=null)
		{
			currNode=(BPTreeNode<TKey, TValue>) currNode.parentNode;
		}
		return currNode;
	}
	
	public BPTreeLeafNode<TKey,TValue> findNodeInSequenceSet(TKey key)
	{
		BPTreeLeafNode<TKey,TValue> currNode=null;
		if (!this.isLeaf()||this.leftSibling!=null)
		{
			currNode=goDownFarLeft();
			if (currNode==null)
			{
				return null;
			}
			return currNode.findNodeInSequenceSet(key);
		}
		else
		{
			currNode=(BPTreeLeafNode<TKey,TValue>) this;
		}
		if (key.compareTo((TKey) currNode.keys[0])<0 ||currNode.keyTally==0) //smaller than the first node first key
		{
			return currNode;
		}
		while (currNode!=null&&currNode.rightSibling!=null) //finds which node the key should be placed into
		{
			if (currNode.getKey(currNode.keyTally-1).compareTo(key)<=0&&currNode.rightSibling.getKey(0).compareTo(key)>0) //key between last key of this node and first key of next node
			{
				return currNode;
			}
			else if (currNode.getKey(0).compareTo(key)<=0&&currNode.getKey(currNode.keyTally-1).compareTo(key)>0)//key between first and last key of this node
			{
				return currNode;
			}
			currNode=(BPTreeLeafNode<TKey,TValue>) currNode.rightSibling;
		}
		return currNode; //key is in biggest node
	}

	public abstract BPTreeLeafNode<TKey,TValue> goDownFarLeft();

	public TValue getValue(int linkedlistindex,TKey key)
	{
		ValueNode<TValue> currNode=search(key);
		if (currNode==null)
		{
			return null;
		}
		for (int i=0;i<linkedlistindex;i++)
		{
			if (currNode==null)
			{
				return null;
			}
			currNode=currNode.next;
		}
		if (currNode==null)
		{
			return null;
		}
		return currNode.value;
	}

	public ValueNode<TValue> getValueNode(TValue val,TKey key)
	{
		ValueNode<TValue> currNode=search(key);
		if (currNode==null)
		{
			return null;
		}
		while(currNode!=null)
		{
			if (currNode.value.equals(val))
			{
				return currNode;
			}
			currNode=currNode.next;
		}
		return null;
	}

	public abstract BPTreeNode<TKey,TValue> handleUnderflowNode();
	public void checkSiblings()
	{
		if (isLeaf())
		{
			return;
		}
		else
		{
			BPTreeInnerNode<TKey,TValue>currNode=(BPTreeInnerNode<TKey,TValue>) this;
			if (leftSibling!=null&&this.castReference(0)!=null)
			{	
				BPTreeNode<TKey,TValue> leftFarRightChild=leftSibling.castReference(leftSibling.keyTally);
				if (leftFarRightChild!=null)
				{
					leftFarRightChild.rightSibling=this.castReference(0);
					this.castReference(0).leftSibling=leftFarRightChild;
				}
				
			}
			if (rightSibling!=null&&this.castReference(keyTally)!=null)
			{
				BPTreeNode<TKey,TValue> rightFarLeftChild=rightSibling.castReference(0);
				if (rightFarLeftChild!=null)
				{
					this.castReference(keyTally).rightSibling=rightFarLeftChild;
					rightFarLeftChild.leftSibling=this.castReference(keyTally);
				}
				
			}
			checkSiblingsWithinNode();
			for (int i=0;i<=keyTally;i++) 
			{
				if (this.castReference(i)!=null)
				{
					this.castReference(i).setParent(this);
					this.castReference(i).checkSiblings();
				}	
			}
		}
	}
	public boolean mayShareWithSibling(BPTreeNode<TKey,TValue> sibling)
	{
		return (sibling!=null&&sibling.parentNode==this.parentNode&&sibling.keyTally-1>=minKeys);
	}

	public boolean mayMergeWithSibling(BPTreeNode<TKey,TValue> sibling)
	{
		return (sibling!=null&&sibling.parentNode==this.parentNode);
	}

	public TKey getLargestKey()
	{
		return getKey(keyTally-1);
	}

	public TKey getSmallestKey()
	{
		return getKey(0);
	}
	
	public TKey takeKeyFrom(int index) 
	{
		TKey returnval=this.getKey(index);
		this.keys[index]=null;
		return returnval;
	}
	public ValueNode<TValue> takeValueFrom(int index)
	{
		ValueNode<TValue> returnval=this.getValueHead(index);
		this.castToLeaf().values[index]=null;
		return returnval;
	}

	public ValueNode<TValue> getValueHead(int index)
	{
		return this.castToLeaf().values[index];
	}

	public int findParentReference()
	{
		BPTreeInnerNode<TKey,TValue> parent=(BPTreeInnerNode<TKey,TValue>) parentNode;
		if (parentNode==null)
		{
			return -1;
		}
		else
		{
			for (int i=0; i<=parent.keyTally;i++)
			{
				if (parent.references[i]==this)
				{
					return i;
				}
			}
		}
		return -1;
	}

	public abstract boolean trySharingLeft();
	public abstract boolean trySharingRight();
	public abstract BPTreeNode<TKey,TValue> tryMergingLeft();
	public abstract BPTreeNode<TKey,TValue> tryMergingRight();
	public abstract BPTreeNode<TKey,TValue> deleteAt(int index);

	public BPTreeLeafNode<TKey,TValue> castToLeaf()
	{
		return (BPTreeLeafNode<TKey,TValue>) this;
	}

	public BPTreeInnerNode<TKey,TValue> castToInner()
	{
		return (BPTreeInnerNode<TKey,TValue>) this;
	}

	public BPTreeNode<TKey,TValue> castReference(int index)
	{
		if (index>keyTally+1)
		{
			return null;
		}
		return (BPTreeNode<TKey,TValue>) this.castToInner().references[index];
	}

	public void checkSiblingsWithinNode()
	{
		for (int i=1;i<=keyTally;i++)
		{
			if (castReference(i)==null)
			{
				continue;
			}
			BPTreeNode<TKey,TValue> leftSib=castReference(i-1);
			BPTreeNode<TKey,TValue> middle=castReference(i);
			BPTreeNode<TKey,TValue> rightSib=castReference(i+1);
			if (leftSib!=null)
			{
				leftSib.rightSibling=middle;
				middle.leftSibling=leftSib;
			}
			if (rightSib!=null)
			{
				rightSib.leftSibling=middle;
				middle.rightSibling=rightSib;
			}
		}
	}

	public void fixSequenceSetReferences()
	{
		BPTreeLeafNode<TKey,TValue> currNode=goDownFarLeft();
		while (currNode.rightSibling!=null)
		{
			currNode.rightSibling.leftSibling=currNode;
			currNode=currNode.rightSibling.castToLeaf();
		}
	}

	public void removeValue(TValue val)
	{
		ValueNode<TValue>[] valueHeads=values();
		ValueNode<TValue> prev=null;
		ValueNode<TValue> currNode=null;
		TKey key=null;
		for (ValueNode<TValue> head : valueHeads) 
		{
			currNode=head;
			prev=null;
			key=findValueKey(head.value);
			while (currNode!=null) 
			{
				if (currNode.value.equals(val))
				{
					if (prev==null)
					{
						head=currNode.next;
					}
					else
					{
						prev.next=currNode.next;
					}
					break;//only one identical row index
				}	
				prev=currNode;
				currNode=currNode.next;
			}
			if (head==null)
			{
				delete(key);
			}
		}
	}
	public TKey findValueKey(TValue val)
	{
		BPTreeLeafNode<TKey,TValue> currNode=goDownFarLeft();
		ValueNode<TValue> currVal=null;
		int index=0;
		while (currNode!=null)
		{
			index=0;
			for (ValueNode<TValue> head : currNode.values) 
			{
				currVal=head;
				while (currVal!=null) 
				{
					if (currVal.value.equals(val))
					{
						return currNode.getKey(index);
					}
					currVal=currVal.next;
				}
				index++;
			}

			currNode=(BPTreeLeafNode<TKey,TValue>)currNode.rightSibling;
		}
		return null;
	}
}