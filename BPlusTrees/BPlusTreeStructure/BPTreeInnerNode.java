
/**
 * A B+ tree internal node
 * 
 * @param <TKey> the data type of the key
 * @param <TValue> the data type of the value
 */
@SuppressWarnings("unchecked")
class BPTreeInnerNode<TKey extends Comparable<TKey>, TValue> extends BPTreeNode<TKey, TValue> {

	protected Object[] references;

	public BPTreeInnerNode(int order) {
		this.m = order;
		// The strategy used here first inserts and then checks for overflow. 
		// Thus an extra space is required i.e. m instead of m-1/m+1 instead of m.
		// You can change this if needed. 
		this.keys = new Object[m];
		this.references = new Object[m + 1];
		maxKeys=m-1;
		minKeys= (int) Math.floor(((double) (m-1)) / 2.0);
	}

//	@SuppressWarnings("unchecked")
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
	BPTreeInnerNode(int order,BPTreeNode<TKey,TValue> parentnode,BPTreeNode<TKey,TValue> left,BPTreeNode<TKey,TValue> right)
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
		references = new BPTreeNode[m+1]; // order m
		keyTally = 0;
	}

	public BPTreeNode<TKey,TValue> insertUpwards(TKey key,BPTreeNode<TKey,TValue> newrightchild) //a leaf or child has split and the key must be inserted
	{
		//int i=0;
		if (key.compareTo((TKey) keys[0])<0)
		{
			moveArrayRight(0);
			keys[0]=key;
			setChild(1, newrightchild);			
		}
		else if (key.compareTo((TKey) keys[keyTally-1])>=0)
		{
			keys[keyTally]=key;
			this.setChild(keyTally+1, newrightchild);
		}
		else
		{
			for (int i=0;i<keyTally;i++)
			{
				if (key.compareTo((TKey) keys[i])>0&&key.compareTo((TKey) keys[i+1])<0)
				{
					moveArrayRight(i+1);
					keys[i+1]=key;
					this.setChild(i+2, newrightchild);
					break;
				}
			}	
		}
		keyTally++;
		if (keyTally>maxKeys) //full node...here we go splitting
		{
			handleFullNode();
		}
		return (BPTreeNode<TKey,TValue>) findRoot();
	}


	public BPTreeNode<TKey,TValue> insert(TKey key, TValue value) //first find a leaf node to insert into and run from there
	{
		BPTreeLeafNode<TKey,TValue> toInsert=findNodeInSequenceSet(key);
		if (toInsert==null)
		{
			return findRoot();
		}
		return toInsert.insert(key,value);
	}
	
	protected BPTreeInnerNode<TKey,TValue> splitToRight(int middlenode)
	{
		BPTreeInnerNode<TKey,TValue> rightnode=new BPTreeInnerNode<TKey,TValue>(m,this.parentNode,this,this.rightSibling);
		this.rightSibling=rightnode;
		if (m%2==0)
		{
			for (int i=middlenode+1;i<=maxKeys;i++) //leftbiased, skip over the middlenode to insert it at parentNode, but keep left branch right of leftnode and right branch left of rightnode
			{
				rightnode.keys[i-middlenode-1]=this.keys[i];
				rightnode.keyTally++;
				this.keys[i]=nullnode;
				rightnode.setChild(i-middlenode,(BPTreeNode<TKey,TValue>) this.references[i+1]);
				references[i+1]=null;
				this.keyTally--;
			}
			this.keys[middlenode]=nullnode;
			this.keyTally--;
			rightnode.setChild(0,(BPTreeNode<TKey,TValue>) references[middlenode+1]);
			this.references[middlenode+1]=null;
			//rightnode.setChild(rightnode.keyTally, this.references[maxKeys+1]);
			//this.references[maxKeys+1]=null;
		}
		else
		{
			for (int i=middlenode;i<=maxKeys;i++) //leftbiased
			{
				rightnode.keys[i-middlenode]=this.keys[i];
				rightnode.keyTally++;
				this.keys[i]=nullnode;
				rightnode.setChild(i-middlenode,(BPTreeNode<TKey,TValue>) this.references[i]);
				references[i]=null;
				this.keyTally--;
			}
			rightnode.setChild(middlenode-1,(BPTreeNode<TKey,TValue>) references[maxKeys+1]);
			this.references[maxKeys+1]=null;
		}
		
		//rightnode.setChild(0, references[middlenode+1]);
		//this.references[middlenode+1]=null;
		return rightnode;
	}
	public void handleFullNode()
	{
		int middlenode=0;
		TKey midkey=nullnode;
		if (m%2==0)
		{
			middlenode=(int) (Math.ceil(((double) (keyTally)/2.0)));
			midkey=(TKey) keys[middlenode];
		}
		else
		{
			middlenode=(int) (Math.ceil((double) (keyTally)/2.0));
			midkey=(TKey) keys[middlenode-1];
		}
		 //left biased
		BPTreeInnerNode<TKey,TValue> rightnode=splitToRight(middlenode);
		this.rightSibling=rightnode;
		rightnode.leftSibling=this;
		if (this.parentNode==null)//this is the root
		{
			this.parentNode=new BPTreeInnerNode<TKey,TValue>(this.m);
			parentNode.keys[0]=midkey;
			parentNode.keyTally++;
			if (m%2==1)
			{
				keys[middlenode-1]=nullnode;
				keyTally--;
			}
			parentNode.setChild(0,(BPTreeNode<TKey,TValue>) this);
			parentNode.setChild(1,(BPTreeNode<TKey,TValue>) rightnode);
			//System.out.println(this+" "+this.next);
		}
		else
		{
			if (m%2==1)
			{
				keys[middlenode-1]=nullnode;
				keyTally--;
			}
			(this.parentNode).insertUpwards(midkey,rightnode);
		}
	}

	@Override
	public BPTreeLeafNode<TKey,TValue> goDownFarLeft()
	{
		BPTreeNode<TKey,TValue> currNode=findRoot();
		while (!currNode.isLeaf()&&((BPTreeInnerNode<TKey,TValue>) currNode).references[0]!=null) //finding leftmost leaf
		{
			currNode=(BPTreeNode<TKey,TValue>) ((BPTreeInnerNode<TKey,TValue>) currNode).references[0];
		}
		return (BPTreeLeafNode<TKey,TValue>) currNode;
	}	

	public BPTreeNode<TKey,TValue> deleteAt(int index)
	{
		if (index>=keyTally) //delete rightmost key with rightmost reference
		{
			keys[keyTally-1]=null;
			references[keyTally]=null;
		}
		else
		{
			moveArrayLeft(index); //overwites key with all other keys or a null value
		}
		keyTally--;
		if (parentNode==null&&keyTally!=0)//we deleted from the root and it could have udnerflowed but is not empty
		{
			return this;
		}
		else if (parentNode==null&&keyTally==0)//root disappear, make its only child the root
		{
			if (castReference(0)!=null)
			{
				castReference(0).setParent(null);;
				return castReference(0);
			}
			else
			{
				castReference(1).setParent(null);
				return castReference(1);
			}
			
			
		}
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
			//checkSiblingsWithinNode();
			return findRoot();
		}
		else if (trySharingRight())
		{
			//checkSiblingsWithinNode();
			return findRoot();
		}
		else if (mayMergeWithSibling(leftSibling))
		{
			return tryMergingLeft();
		}
		else//if this does not work, the structure is completely flawed
		{
			return tryMergingRight();
		}
		
	}
	public boolean trySharingLeft()
	{
		if (!mayShareWithSibling(leftSibling))
		{
			return false;
		}
		keyTally++;
		this.moveArrayRight(0);
		this.keys[0]=leftSibling.takeKeyFrom(leftSibling.keyTally-1);
		this.setChild(0, leftSibling.castReference(leftSibling.keyTally));
		((BPTreeInnerNode<TKey,TValue>)leftSibling).deleteAt(leftSibling.keyTally); //delete last key and reference
		int parentKeyIndex=findParentReference()-1;
		TKey temp=(TKey)this.keys[0];
		this.keys[0]=parentNode.keys[parentKeyIndex];
		parentNode.keys[parentKeyIndex]=temp;
		//checkSiblingsWithinNode();
		return true;
	}
	public boolean trySharingRight()
	{
		if (!mayShareWithSibling(rightSibling))
		{
			return false;
		}
		keys[keyTally]=rightSibling.takeKeyFrom(0);
		this.setChild(keyTally+1, rightSibling.castReference(0));
		rightSibling.moveArrayLeft(0);
		rightSibling.keyTally--;
		int parentKeyIndex=findParentReference(); //the key that contains left ref to this node and right ref to rightnode
		TKey temp=this.getKey(keyTally);
		this.keys[keyTally]=parentNode.keys[parentKeyIndex];
		parentNode.keys[parentKeyIndex]=temp;
		keyTally++;
		//checkSiblingsWithinNode();
		return true;
	}
	public BPTreeNode<TKey,TValue> tryMergingLeft()
	{

		int parentKeyIndex=findParentReference()-1;
		BPTreeInnerNode<TKey,TValue> mergedNode=mergeNodes(leftSibling.castToInner(), this, parentNode.getKey(parentKeyIndex));
		parentNode.castToInner().setChild(parentKeyIndex, mergedNode);
		BPTreeNode<TKey,TValue> returnroot=parentNode.castToInner().deleteAt(parentKeyIndex);
		//mergedNode.checkSiblingsWithinNode();
		return returnroot;
	}
	public BPTreeNode<TKey,TValue> tryMergingRight()
	{

		int parentKeyIndex=findParentReference()-1;
		BPTreeInnerNode<TKey,TValue> mergedNode=mergeNodes(leftSibling.castToInner(), this, parentNode.getKey(findParentReference()));
		parentNode.castToInner().setChild(parentKeyIndex, mergedNode);
		BPTreeNode<TKey,TValue> returnroot=parentNode.castToInner().deleteAt(0);
		//mergedNode.checkSiblingsWithinNode();
		return returnroot;
	}
	public BPTreeInnerNode<TKey,TValue> mergeNodes(BPTreeInnerNode<TKey,TValue> leftNode,BPTreeInnerNode<TKey,TValue> rightNode,TKey parentKey)
	{
		int i=0;
		BPTreeInnerNode<TKey,TValue> mergeNode= new BPTreeInnerNode<TKey,TValue>(leftNode.m,leftNode.parentNode,leftNode.leftSibling,rightNode.rightSibling);
		for (i=0;i<leftNode.keyTally;i++)
		{
			mergeNode.keys[i]=leftNode.takeKeyFrom(i);
			mergeNode.references[i]=leftNode.castReference(i);
			mergeNode.keyTally++;
		}
		mergeNode.references[i]=leftNode.castReference(leftNode.keyTally);
		mergeNode.keys[i]=parentKey;
		mergeNode.keyTally++;
		leftNode.keyTally=0;
		int j=i+1;
		//mergeNode.keys[mergeNode.keyTally]=rightNode.takeKeyFrom(0);
		mergeNode.references[mergeNode.keyTally]=rightNode.castReference(0);
		for (j=i+1;j-(i+1)<rightNode.keyTally;j++)
		{	
				mergeNode.keys[j]=rightNode.takeKeyFrom(j-(i+1));
				mergeNode.keyTally++;
				mergeNode.references[j+1]=rightNode.castReference(j-(i));	
		}
		//mergeNode.references[j]=leftNode.castReference(rightNode.keyTally);
		rightNode.keyTally=0;
		return mergeNode;
	}
	public void overWrite(TKey newkey,int indextooverwrite,BPTreeNode<TKey,TValue> newLeftChild,BPTreeNode<TKey,TValue> newRightChild)
	{
		this.keys[indextooverwrite]=newkey;
		references[indextooverwrite]=newLeftChild;
		references[indextooverwrite+1]=newRightChild;
	}

	
}
