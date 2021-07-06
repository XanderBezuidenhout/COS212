//There is no explicit need to modify this file. But if you do, do not modify the existing method signatures
public class BPlusNode {

	public int keyTally; /* how many keys are currently in this node */
	public BPlusNode branches[];
	public int keys[];
	public boolean leaf; /* true if the node is a leaf*/
	public BPlusNode parent; /* refers to the parent node of this node. Use this if you feel it will assist you */
	protected BPlusNode next; /* refers to the next node only if this node is a leaf node. The last leaf has a next of null. */
	
	int minKeys;
	int maxKeys;
	int m;
	int nullnode=Integer.MIN_VALUE;
	/* constructor */
	public BPlusNode(int m, boolean isLeaf) {
		keys = new int[m];
		this.m=m;
		branches = new BPlusNode[m+1]; // order m
		keyTally = 0;
		leaf = isLeaf;
		parent = null;
		next = null; // initialize next to null
		maxKeys=m-1;
		minKeys= (int) Math.ceil(m / 2.0) - 1;
		clearKeys();
	}

	/* returns true if full and false otherwise */
	public boolean isFull() {
		return keyTally == keys.length;
	}

	/* returns a string representation of the node. DO NOT modify */
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

	public BPlusNode insert(int element)
	{
		if (leaf)
		{
			return insertLeaf(element);
		}
		else
		{
			return insertDownwards(element);
		}
	}

	// START OF INNER NODE FUNCTIONS
	public BPlusNode insertUpwards(int key,BPlusNode newrightchild) //a leaf or child has split and the key must be inserted
	{
		//int i=0;
		if (key<keys[0])
		{
			moveArrayRight(0);
			keys[0]=key;
			setChild(1, newrightchild);			
		}
		else if (key>=keys[keyTally-1])
		{
			keys[keyTally]=key;
			this.setChild(keyTally+1, newrightchild);
		}
		else
		{
			for (int i=0;i<keyTally;i++)
			{
				if (key>keys[i]&&key<keys[i+1])
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
			handleFullInnerNode();
		}
		return findRoot();
	}

	public BPlusNode insertDownwards(int key) //first find a leaf node to insert into and run from there
	{
		/*if (key<keys[0]||keyTally==0) //key is smaller than all else or is root for keyTally==0
		{
			return branches[0].insert(key);
		}
		for (int i=0;i<keyTally;i++)
		{
			if (keys[i]<=key)
			{
				return branches[i+1].insert(key);
			}
		}*/
		// it is not supposed to come this far, since it must either be smaller than everything or bigger/equal to something
		BPlusNode toInsert=findNodeInSequenceSet(key);
		if (toInsert==null)
		{
			return findRoot();
		}
		return toInsert.insertLeaf(key);
		
	}
	public BPlusNode findRoot()
	{
		BPlusNode currNode=this;
		while (currNode.parent!=null)
		{
			currNode=currNode.parent;
		}
		return currNode;
	}
	protected BPlusNode splitInnerToRight(int middlenode)
	{
		BPlusNode rightnode=new BPlusNode(m,parent,this.next,false);
		this.next=rightnode;
		if (m%2==0)
		{
			for (int i=middlenode+1;i<=maxKeys;i++) //leftbiased, skip over the middlenode to insert it at parent, but keep left branch right of leftnode and right branch left of rightnode
			{
				rightnode.keys[i-middlenode-1]=this.keys[i];
				rightnode.keyTally++;
				this.keys[i]=nullnode;
				rightnode.setChild(i-middlenode, this.branches[i+1]);
				branches[i+1]=null;
				this.keyTally--;
			}
			this.keys[middlenode]=nullnode;
			this.keyTally--;
			rightnode.setChild(0, branches[middlenode+1]);
			this.branches[middlenode+1]=null;
			//rightnode.setChild(rightnode.keyTally, this.branches[maxKeys+1]);
			//this.branches[maxKeys+1]=null;
		}
		else
		{
			for (int i=middlenode;i<=maxKeys;i++) //leftbiased
			{
				rightnode.keys[i-middlenode]=this.keys[i];
				rightnode.keyTally++;
				this.keys[i]=nullnode;
				rightnode.setChild(i-middlenode, this.branches[i]);
				branches[i]=null;
				this.keyTally--;
			}
			rightnode.setChild(middlenode-1, branches[maxKeys+1]);
			this.branches[maxKeys+1]=null;
		}
		
		//rightnode.setChild(0, branches[middlenode+1]);
		//this.branches[middlenode+1]=null;
		return rightnode;
	}

	public void handleFullInnerNode()
	{
		int middlenode=0;
		int midkey=0;
		if (m%2==0)
		{
			middlenode=(int) (Math.ceil(((double) (keyTally)/2.0)));
			midkey=keys[middlenode];
		}
		else
		{
			middlenode=(int) (Math.ceil((double) (keyTally)/2.0));
			midkey=keys[middlenode-1];
		}
		 //left biased
		 
		
		
		BPlusNode rightnode=splitInnerToRight(middlenode);
		if (this.parent==null)//this is the root
		{
			this.parent=new BPlusNode(this.m, false);
			parent.keys[0]=midkey;
			parent.keyTally++;
			if (m%2==1)
			{
				keys[middlenode-1]=nullnode;
				keyTally--;
			}
			parent.setChild(0, this);
			parent.setChild(1, rightnode);
			//System.out.println(this+" "+this.next);
		}
		else
		{
			if (m%2==1)
			{
				keys[middlenode-1]=nullnode;
				keyTally--;
			}
			(this.parent).insertUpwards(midkey,rightnode);
		}
	}

	public void setChild(int index, BPlusNode child) {
		this.branches[index] = child;
		if (child != null)
			child.parent=this;
	}
	
	//END OF INNER NODE FUNCTIONS
	//START OF LEAF NODE FUNCTIONS
	protected BPlusNode insertLeaf(int element)
	{
		if (findWithinNode(element)!=-1) //no duplicate
		{
			return findRoot();
		}
		else if (keys==null||keys.length==0)
		{
			return findRoot();
		}
		if (keys[0]==nullnode||keyTally==0)
		{
			keys[0]=element;
		}
		else if (element<keys[0]) //smaller than smallest key
		{
			moveArrayRight(0);
			keys[0]=element;
		}
		else if (element>keys[keyTally-1])
		{
			keys[keyTally]=element;
		}
		else
		{
			for (int i=0;i<keyTally;i++)
			{
				if (element>keys[i]&&element<keys[i+1])
				{
					moveArrayRight(i+1);
					keys[i+1]=element;
					break;
				}
			}	
		}
		keyTally++;
		//at this stage, the node will either be overflowed, or full(er) to have enough space for insert
		if (keyTally<=maxKeys) //no overflow
		{
			return findRoot();
		}
		else //overflow...here we go
		{
			//System.out.println(this);
			handleFullLeafNode();
			return findRoot();
		}
	}

	protected BPlusNode handleFullLeafNode()
	{
		int middlenode=0;
		int midkey= 0;
		if (m%2==0)
		{
			middlenode=(int) (Math.ceil((double) keyTally/2.0));
		}
		else
		{
			middlenode=(int) (Math.ceil((double) (keyTally-1)/2.0));
		}
		midkey=keys[middlenode];
		BPlusNode rightnode=splitLeafToRight(middlenode); //rightbiased
		if (this.parent==null)//this is the root
		{
			this.parent=new BPlusNode(this.m, false);
			parent.keys[0]=midkey;
			parent.keyTally++;
			parent.setChild(0, this);
			parent.setChild(1, rightnode);
		//	System.out.println(this+" "+this.next);
			return parent;
		}
		else
		{
			(this.parent).insertUpwards(midkey,rightnode);
			return findRoot();	
		}	
	}

	protected BPlusNode splitLeafToRight(int middlenode)
	{
		BPlusNode rightnode=new BPlusNode(m,parent,this.next,true);
		this.next=rightnode;
		for (int i=middlenode;i<=maxKeys;i++)
		{
			rightnode.insertLeaf(keys[i]);
		//	rightnode.numValues[i]=this.numValues[i];
			//this.numValues[i]=0;
			this.keys[i]=nullnode;
			this.keyTally--;
		}
		
		return rightnode;
	}

	protected BPlusNode findNodeInSequenceSet(int key)
	{
		BPlusNode currNode=findRoot();
		if (currNode==null)
		{
			return null;
		}

		while (!currNode.leaf&&currNode.branches[0]!=null) //finding leftmost leaf
		{
			currNode=currNode.branches[0];
		}

		if (key<currNode.keys[0]||currNode.keyTally==0) //smaller than the first node first key
		{
			return currNode;
		}

		while (currNode!=null&&currNode.next!=null) //finds which node the key should be placed into
		{
			if (currNode.keys[keyTally-1]<key&&currNode.next.keys[0]>key) //key between last key of this node and first key of next node
			{
				return currNode;
			}
			else if (currNode.keys[0]<key&&currNode.keys[keyTally-1]>key)//key between first and last key of this node
			{
				return currNode;
			}
			currNode=currNode.next;
		}
		return currNode; //key is in biggest node
	}
	//END OF LEAF NODE FUNCTIONS
	BPlusNode(int order,BPlusNode parentnode,BPlusNode nextnode, boolean isLeaf)
	{
		this.m = order;
		// The strategy used here first inserts and then checks for overflow. 
		// Thus an extra space is required i.e. m instead of m-1.
		// You can change this if needed. 
		this.keys = new int[m]; 
		maxKeys=m-1;
		minKeys= (int) Math.ceil(m / 2.0) - 1;
		clearKeys();
		this.next=nextnode;
		this.parent=parentnode;
		branches = new BPlusNode[m+1]; // order m
		keyTally = 0;
		leaf = isLeaf;
	}

	public void setParent(BPlusNode parentnode)
	{
		this.parent=parentnode;
	}

	public void moveArrayRight(int startindex)
	{
		//int keyTally=this.keyTally;
		
		for (int i=keyTally-1;i>=startindex;i--)
		{
			keys[i+1]=keys[i];
			if (!isLeaf())
			{
					((BPlusNode) this).branches[i+2]=((BPlusNode) this).branches[i+1];
			}
		}
		if (startindex==0&&!isLeaf())
		{
			branches[1]=branches[0];
		}
	}

	public boolean isLeaf()
	{
		return leaf;
	}

	protected int findWithinNode(int key)
	{
		for (int i=0;i<keyTally;i++)
		{
			if ((keys[i])==(key))
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
			keys[i]=nullnode;
		}
	}
}
