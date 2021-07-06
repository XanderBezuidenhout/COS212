/**
 * A B+ tree internal node
 * 
 * @param <TKey> the data type of the key
 * @param <TValue> the data type of the value
 */
@SuppressWarnings("unchecked")
class BPTreeInnerNode<TKey extends Comparable<TKey>, TValue> extends BPTreeNode<TKey, TValue> {

	

	public BPTreeInnerNode(int order) {
		//this.m = order;
		// The strategy used here first inserts and then checks for overflow. 
		// Thus an extra space is required i.e. m instead of m-1/m+1 instead of m.
		// You can change this if needed. 
		this.keys = new Object[m];
		this.references = new Object[m + 1];
		leaf=isLeaf();
		keyTally = 0;
		parentNode = null;
		maxKeys=m+1;
		minKeys=(int) Math.ceil(m / 2.0) - 1;
	}

	@SuppressWarnings("unchecked")
	public BPTreeNode<TKey, TValue> getChild(int index) {
		return (BPTreeNode<TKey, TValue>) this.references[index];
	}
	
	public void setChild(int index, BPTreeNode<TKey, TValue> child) {
		this.references[index] = child;
		if (child != null)
			child.setParent(this);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	////// You should not change any code above this line //////

	////// Implement functions below this line //////
	public BPTreeNode<TKey,TValue> insert(TKey element,ValueNode<TValue> newValue)
	{
		insertDownwards(element);
		return this;
	}

	public BPTreeNode<TKey,TValue> insertUpwards(TKey key,BPTreeLeafNode<TKey,TValue> newrightchild)
	{
		int i=0;
		if (key.compareTo((TKey) keys[0])<0)
		{
			moveArrayRight(0);
			references[1]=newrightchild;
			keyTally++;
		}
		for (i=1;i<keyTally;i++)
		{
			if (key.compareTo((TKey) keys[i])>=0)
			{
				moveArrayRight(i);
				keys[i]=key;
				keyTally++;
				references[i+1]=newrightchild;
			}
		}
		if (keyTally<=maxKeys) //woohoo, we can stop splitting!
		{
			return null;
		}
		else //overflow, here we go splitting up again...
		{

		}
		return null;
	}
	public void handleFullNode()
	{
		int middlenode=(int) (Math.ceil(((double) keyTally-1))/2.0);
		TKey midkey=(TKey) keys[middlenode];
		BPTreeInnerNode<TKey,TValue> rightNode=splitToRight(middlenode);
	}

	protected BPTreeInnerNode<TKey,TValue> splitToRight(int middlenode)
	{
		BPTreeInnerNode<TKey,TValue> rightnode=new BPTreeInnerNode<>(m,parentNode,this,this.rightSibling);
		this.rightSibling=rightnode;
		for (int i=middlenode;i<keyTally;i++)
		{
			this.keys[i]=null;
			this.keyTally--;
		}
		return rightnode;
	}

	public BPTreeNode<TKey,TValue> insertDownwards(TKey key) //first find a leaf node to insert into and run from there
	{
		if (key.compareTo((TKey) keys[0])<0) //key is smaller than all else
		{
			if (((BPTreeInnerNode<TKey,TValue>) references[0]).isLeaf())
			{
				return ((BPTreeLeafNode<TKey,TValue>) references[0]).insertPractical5(key);
			}
			else
			{
				return ((BPTreeInnerNode<TKey,TValue>) references[0]).insertDownwards(key);
			}
		}
		for (int i=0;i<keyTally;i++)
		{
			TKey tempKey=(TKey) keys[i];
			if (tempKey.compareTo(key)<=0)
			{
				if (((BPTreeNode<TKey,TValue>) references[i+1]).isLeaf())
				{
					return ((BPTreeLeafNode<TKey,TValue>) references[i+1]).insertPractical5(key);
				}
				else
				{
					return ((BPTreeInnerNode<TKey,TValue>) references[0]).insertDownwards(key);
				}
			}
		}
		// it is not supposed to come this far, since it must either be smaller than everything or bigger/equal to something
		return null;
	}
	BPTreeInnerNode(int order,BPTreeNode<TKey,TValue> parent,BPTreeNode<TKey,TValue> leftSibling,BPTreeNode<TKey,TValue> rightSibling) {
		this.m = order;
		// The strategy used here first inserts and then checks for overflow. 
		// Thus an extra space is required i.e. m instead of m-1.
		// You can change this if needed. 
		this.keys = new Object[m]; 
		//this.values = new ValueNode[m]; //stores heads of each linked list associated with each key
		//numValues=new int[m]; 
		this.leftSibling=leftSibling;
		this.rightSibling=rightSibling;
		this.parentNode=parent;
	}

	
}