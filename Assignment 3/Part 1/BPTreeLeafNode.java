

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

	
	public BPTreeLeafNode(int order) {
		this.m = order;
		// The strategy used here first inserts and then checks for overflow. 
		// Thus an extra space is required i.e. m instead of m-1.
		// You can change this if needed. 
		this.keys = new Object[m]; 
		this.values = new ValueNode[m]; 
		maxKeys=m-1;
		minKeys= (int) Math.floor(((double) m-1) / 2.0);
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	BPTreeLeafNode(int order,BPTreeNode<TKey,TValue> parentnode,BPTreeNode<TKey,TValue> left,BPTreeNode<TKey,TValue> right)
	{
		super();
		this.m = order;
		// The strategy used here first inserts and then checks for overflow. 
		// Thus an extra space is required i.e. m instead of m-1.
		// You can change this if needed. 
		this.keys = new Object[m]; 
		maxKeys=m-1;
		minKeys= (int) Math.floor(((double) (m-1)) / 2.0);
		clearKeys();
		this.leftSibling=left;
		this.rightSibling=right;
		this.parentNode=parentnode;
		this.values = new ValueNode[m]; 
		//references = new BPTreeNode[m+1]; // order m
		keyTally = 0;
	}
	
	////// You should not change any code above this line //////

	////// Implement functions below this line ////// 
	
	// A getValue and setValue could help
	protected void handleFullNode()
	{
		int middlenode=0;
		TKey midkey= nullnode;
		if (m%2==0)
		{
			middlenode=(int) (Math.ceil((double) keyTally/2.0));
		}
		else
		{
			middlenode=(int) (Math.ceil((double) (keyTally-1)/2.0));
		}
		midkey=(TKey) keys[middlenode];
		BPTreeLeafNode<TKey,TValue> rightnode=splitToRight(middlenode); //rightbiased
		this.rightSibling=rightnode;
		rightnode.leftSibling=this;
		if (this.parentNode==null)//this is the root
		{
			this.parentNode=new BPTreeInnerNode<TKey,TValue>(this.m);
			parentNode.keys[0]=midkey;
			parentNode.keyTally++;
			parentNode.setChild(0, this);
			parentNode.setChild(1, rightnode);
		//	System.out.println(this+" "+this.next);
			return ;
		}
		else
		{
			(this.parentNode).insertUpwards(midkey,rightnode);
			return;	
		}	
	}

	protected BPTreeLeafNode<TKey,TValue> splitToRight(int middlenode)
	{
		BPTreeLeafNode<TKey,TValue> rightnode=new BPTreeLeafNode<TKey,TValue>(m,parentNode,this,this.rightSibling);
		this.rightSibling=rightnode;
		int thiskeytally=this.keyTally;
		for (int i=middlenode;i<thiskeytally;i++)
		{
			if (values[i]==null)
			{
				break;
			}
			rightnode.insert((TKey) keys[i],values[i].value);
			rightnode.values[i-middlenode]=this.values[i];
		//	rightnode.numValues[i]=this.numValues[i];
			//this.numValues[i]=0;
			this.keys[i]=nullnode;
			this.values[i]=null;
			this.keyTally--;
		}
		
		return rightnode;
	}

	@Override
	public BPTreeNode<TKey, TValue> insert(TKey element, TValue value) 
	{
		if (findWithinNode(element)!=-1) 
		{
			insertIntoKeyValues(findWithinNode(element), value);
		}
		else if (keys==null||keys.length==0)
		{
			return findRoot();
		}
		else if (keys[0]==nullnode||keyTally==0)
		{
			keys[0]=element;
			insertIntoKeyValues(0, value);
			keyTally++;
		}
		else if (element.compareTo((TKey) keys[0])<0) //smaller than smallest key
		{
			moveArrayRight(0);
			keys[0]=element;
			insertIntoKeyValues(0, value);
			keyTally++;
		}
		else if (element.compareTo((TKey) keys[keyTally-1])>0) //bigger than biggest key
		{
			keys[keyTally]=element;
			insertIntoKeyValues(keyTally, value);
			keyTally++;
		}
		else
		{
			for (int i=0;i<keyTally;i++)
			{
				if (element.compareTo((TKey) keys[i])>0&&element.compareTo((TKey) keys[i+1])<0)
				{
					moveArrayRight(i+1);
					keys[i+1]=element;
					insertIntoKeyValues(i+1, value);
					break;
				}
			}	
			keyTally++;
		}
		
		//at this stage, the node will either be overflowed, or full(er) to have enough space for insert
		if (keyTally<=maxKeys) //no overflow
		{
			return findRoot();
		}
		else //overflow...here we go
		{
			//System.out.println(this);
			handleFullNode();
			return findRoot();
		}
	}

	@Override
	public void setChild(int index, BPTreeNode<TKey, TValue> child) 
	{
			
	}

	@Override
	public BPTreeNode<TKey, TValue> insertUpwards(TKey key, BPTreeNode<TKey, TValue> rightnode) {
		// TODO Auto-generated method stub
		return null;
	}

	public ValueNode<TValue>[] getValues()
	{	if (this.leftSibling!=null)
		{
			return goDownFarLeft().getValues();
		}
		else if (this.rightSibling==null)
		{
			ValueNode<TValue>[] returnval=new ValueNode[this.keyTally];
			for (int i=0;i<keyTally;i++)
			{
				returnval[i]=this.values[i];
			}
			return returnval;
		}
		else
		{
			ValueNode<TValue>[] vals=new ValueNode[10000];
			int index=0;
			BPTreeLeafNode<TKey,TValue> currNode=this;
			while (currNode!=null)
			{
				for (int i=0;currNode.values[i]!=null;i++)
				{
					vals[index++]=currNode.values[i];
				}
				currNode=(BPTreeLeafNode<TKey,TValue>) currNode.rightSibling;
			}
			ValueNode<TValue>[] returnval=new ValueNode[index];
			for (int i=0;vals[i]!=null;i++)
			{
				returnval[i]=vals[i];
			}
			return returnval;
		}
	}

	public TValue getValue(int index)
	{
		if (index<0||index>keyTally)
		{
			return null;
		}
		return values[index].value;
	}

	@Override
	public BPTreeLeafNode<TKey, TValue> goDownFarLeft() {
		BPTreeLeafNode<TKey, TValue> currNode=this;
		while (currNode.leftSibling!=null)
		{
			currNode=(BPTreeLeafNode<TKey, TValue>) currNode.leftSibling;
		}
		return this;
	}

	private void insertIntoKeyValues(int keyindex,TValue value)
	{
		if (keyindex<0||keyindex>=values.length)
		{
			//System.out.println(keyindex);
			return;
		}

		ValueNode<TValue> currValue=values[keyindex];
		if (currValue==null)
		{
			values[keyindex]=new ValueNode<TValue>(value);//inserts head for first value inserted
			return;
		}
		ValueNode<TValue> newValue=new ValueNode<TValue>(value);
		newValue.next=currValue;
		values[keyindex]=newValue;
	}
	//END OF INSERT FUNCTIONS

	//START OF DELETE FUNCTIONS
	public BPTreeNode<TKey,TValue> deleteKey(TKey key)
	{
		int keyindex=findWithinNode(key);
		if (keyindex==-1) //no key exists in node
		{
			return findRoot();
		}
		moveArrayLeft(keyindex); //overwites key with all other keys or a null value
		keyTally--;
		if (keyTally<minKeys&&parentNode==null)//we deleted from the root and it underflowed
		{
			return this;
		}
		/*else if (keyTally==0)
		{
			getRidOfNode(key);
		}*/
		else if (keyTally<minKeys) //well, here we must take a key somewhere
		{
			return handleUnderflowNode();
		}
		return findRoot();
	}

	public BPTreeNode<TKey,TValue> handleUnderflowNode()
	{
		if (trySharingLeft())
		{
			return findRoot();
		}
		else if (trySharingRight())
		{
			return findRoot();
		}
		else if (mayMergeWithSibling(leftSibling))
		{
			return tryMergingLeft(); //return new root
		}
		else if (mayMergeWithSibling(rightSibling))
		{
			return tryMergingRight();//return new root
		}
		else
		{
			System.out.println("Somehow nothing could merge or share");
		}
		return null;
		

	}
//	int order,BPTreeNode<TKey,TValue> parentnode,BPTreeNode<TKey,TValue> left,BPTreeNode<TKey,TValue> right
	public BPTreeLeafNode<TKey,TValue> mergeNodes(BPTreeLeafNode<TKey,TValue> leftNode,BPTreeLeafNode<TKey,TValue> rightNode)
	{
		BPTreeLeafNode<TKey,TValue> mergeNode= new BPTreeLeafNode<TKey,TValue>(leftNode.m,leftNode.parentNode,leftNode.leftSibling,rightNode.rightSibling);
		int i=0;
		Object[] tempkeys=new Object[2*m];
		ValueNode<TValue>[] tempValueNodes=new ValueNode[2*m];
		for (i=0;i<leftNode.keyTally;i++)
		{
			tempkeys[i]=leftNode.keys[i];
			mergeNode.keyTally++;
			tempValueNodes[i]=leftNode.values[i];
		}

		for (int j=0;j<rightNode.keyTally;j++)
		{
			tempkeys[i+j]=rightNode.keys[j];
			mergeNode.keyTally++;
			tempValueNodes[i+j]=rightNode.values[j];
		}
		mergeNode.keys=tempkeys;
		mergeNode.values=tempValueNodes;
		return mergeNode;
	}

	public TKey takeKeyFrom(int index)
	{
		TKey temp=getKey(index);
		keys[index]=null;
		return temp;
	}
	@Override
	public ValueNode<TValue> takeValueFrom(int index)
	{
		ValueNode<TValue> temp= values[index];
		values[index]=null;
		return temp;
	}

	public BPTreeNode<TKey,TValue> deleteAt(int index)
	{

		keys[index]=null;
		values[index]=null;
		if (index!=keyTally-1)
		{
			moveArrayLeft(index);
		}
		keyTally--;
		return findRoot();
	}
	//END OF DELETE FUNCTIONS

	@Override
	public boolean trySharingLeft() {
		if (!mayShareWithSibling(leftSibling))
		{
			return false;
		}
		moveArrayRight(0);
		keys[0]=leftSibling.takeKeyFrom(leftSibling.keyTally-1);
		values[0]=leftSibling.takeValueFrom(leftSibling.keyTally-1);
		leftSibling.deleteAt(leftSibling.keyTally-1);
		int parentKeyIndex=findParentReference()-1;
		((BPTreeInnerNode<TKey,TValue>) parentNode).overWrite(this.getKey(0), parentKeyIndex, leftSibling,this);
		keyTally++;
		return true;
	}
	@Override
	public boolean trySharingRight() {
		if (!mayShareWithSibling(rightSibling))
		{
			return false;
		}
		keys[keyTally]=rightSibling.takeKeyFrom(0);
		values[keyTally]=rightSibling.takeValueFrom(0);
		rightSibling.deleteAt(0);
		int parentKeyIndex=findParentReference(); //this points to key between this and rightsibling
		parentNode.castToInner().overWrite(rightSibling.getKey(0), parentKeyIndex, this, rightSibling);
		keyTally++;
		
		return true;
	}

	@Override
	public BPTreeNode<TKey,TValue> tryMergingLeft() {

		BPTreeLeafNode<TKey,TValue> mergeNode=mergeNodes(leftSibling.castToLeaf(), this);
		mergeNode.leftSibling=leftSibling.leftSibling;
		if (leftSibling.leftSibling!=null)
		{
			leftSibling.leftSibling.rightSibling=mergeNode;
		}
		mergeNode.rightSibling=this.rightSibling;
		if (this.rightSibling!=null)
		{
			this.rightSibling.leftSibling=mergeNode;
		}
		int parentKeyIndex=findParentReference()-1;//key between left and this
		parentNode.setChild(parentKeyIndex, mergeNode); //left ref of parent change to merged node
		return parentNode.deleteAt(parentKeyIndex);//return new root
		
	}

	@Override
	public BPTreeNode<TKey,TValue> tryMergingRight() {

		BPTreeLeafNode<TKey,TValue> mergeNode=mergeNodes(this,rightSibling.castToLeaf());
		mergeNode.leftSibling=this.leftSibling;
		if (this.leftSibling!=null)
		{
			this.leftSibling.rightSibling=mergeNode;
		}
		mergeNode.rightSibling=rightSibling.rightSibling;
		if (rightSibling.rightSibling!=null)
		{
			rightSibling.rightSibling.leftSibling=mergeNode;
		}
		int parentKeyIndex=findParentReference();//key between this and right
		parentNode.setChild(parentKeyIndex, mergeNode);//;.castToInner().references[parentKeyIndex]=mergeNode;
		return parentNode.deleteAt(parentKeyIndex);
	}

}
