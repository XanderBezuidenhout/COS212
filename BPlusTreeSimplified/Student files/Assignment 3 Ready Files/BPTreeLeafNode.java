/**
 * A B+ tree leaf node
 * 
 * @param <TKey>
 *            the data type of the key
 * @param <TValue>
 *            the data type of the value
 */
@SuppressWarnings("unchecked")
class BPTreeLeafNode<TKey extends Comparable<TKey>, TValue> extends BPTreeNode<TKey, TValue> {

	protected ValueNode<TValue>[] values; // Each ValueNode here is the head of a linked list for a particular key corresponding to the keys[] array for this leaf node.
	protected int numValues[];

	@SuppressWarnings("unchecked")
	public BPTreeLeafNode(int order) {
		this.m = order;
		// The strategy used here first inserts and then checks for overflow. 
		// Thus an extra space is required i.e. m instead of m-1.
		// You can change this if needed. 
		this.keys = new Object[m]; 
		this.values = new ValueNode[m]; //stores heads of each linked list associated with each key
		numValues=new int[m]; 
		leaf=isLeaf();
		keyTally = 0;
		parentNode = null;
		maxKeys=m+1;
		minKeys=(int) Math.ceil(m / 2.0) - 1;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
	
	////// You should not change any code above this line //////

	////// Implement functions below this line ////// 
	
	// A getValue and setValue could help
	public void setValue(int keyindex,int valuenodeindex, TValue newValue) //works as both overwrite and setting function
	{
		if (keyindex<keyTally&&keyindex>-1)
		{
			ValueNode<TValue> currNode=values[keyindex];
			ValueNode<TValue> prevNode=null;
			ValueNode<TValue> newValueNode=new ValueNode<TValue>(newValue);
			for (int i=0;i<valuenodeindex&&currNode!=null;i++)
			{
				prevNode=currNode;
				currNode=currNode.next;
			}
			if (currNode==null)
			{
				prevNode.next=newValueNode;
				numValues[keyindex]++;
			}
			else
			{
				currNode.value=newValue;
			}
		}
		return;
	}

	public TValue getValue(int keyindex,int valuenodeindex)//keyindex is the nth key of the node, valuenodeindex is the nth value in linked list associated with key
	{
		if (keyindex<keyTally&&keyindex>-1)
		{
			ValueNode<TValue> currNode=values[keyindex];
			for (int i=0;i<valuenodeindex;i++)
			{
				currNode=currNode.next;
			}
			if (currNode!=null)
			{
				return currNode.value;
			}
		}
		return null; //value was not found or invalid index
	}

	public BPTreeNode<TKey,TValue> insert(TKey element,ValueNode<TValue> newValue)
	{
		return insertPractical5(element);
	}

	protected BPTreeNode<TKey,TValue> insertPractical5(TKey element)
	{
		if (super.findWithinNode(element)!=-1) //no duplicate
		{
			return null;
		}
		if (keys[0]==null)
		{
			keys[0]=element;	
		}
		else if (element.compareTo((TKey) keys[0])<0) //smaller than smallest key
		{
			moveArrayRight(0);
			
		}
		else
		{
			for (int i=0;i<keyTally;i++)
			{
				if (element.compareTo((TKey) keys[i])>0)
				{
					moveArrayRight(i);
					keys[i]=element;
					break;
				}
			}
		}
		keyTally++;
		//at this stage, the node will either be overflowed, or full(er) to have enough space for insert
		if (keyTally<=maxKeys) //no overflow
		{
			return this;
		}
		else //overflow...here we go
		{
			handleFullNode();
			return this;
		}
	}

	protected void handleFullNode()
	{
		int middlenode=(int) (Math.ceil(((double) keyTally-1))/2.0);
		TKey midkey=(TKey) keys[middlenode];
		BPTreeLeafNode<TKey,TValue> rightnode=splitToRight(middlenode);
		((BPTreeInnerNode) this.parentNode).insertUpwards(midkey,rightnode);
	}

	protected BPTreeLeafNode<TKey,TValue> splitToRight(int middlenode)
	{
		BPTreeLeafNode<TKey,TValue> rightnode=new BPTreeLeafNode<>(m,parentNode,this,this.rightSibling);
		this.rightSibling=rightnode;
		for (int i=middlenode;i<keyTally;i++)
		{
			rightnode.insert((TKey) keys[i], values[i]);
			rightnode.numValues[i]=this.numValues[i];
			this.numValues[i]=0;
			this.keys[i]=null;
			this.keyTally--;
		}
		return rightnode;
	}

	BPTreeLeafNode(int order,BPTreeNode<TKey,TValue> parent,BPTreeNode<TKey,TValue> leftSibling,BPTreeNode<TKey,TValue> rightSibling) {
		this.m = order;
		// The strategy used here first inserts and then checks for overflow. 
		// Thus an extra space is required i.e. m instead of m-1.
		// You can change this if needed. 
		this.keys = new Object[m]; 
		this.values = new ValueNode[m]; //stores heads of each linked list associated with each key
		numValues=new int[m]; 
		this.leftSibling=leftSibling;
		this.rightSibling=rightSibling;
		this.parentNode=parent;
	}

}
