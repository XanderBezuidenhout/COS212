/* Complete this class to implement a fully functional min-max d-heap. Read the comments to determine what each aspect of the class is supposed to do.
You must add any additional features (methods, references) which may aid you in your task,
BUT you are not allowed to remove or change the names or properties of any of the features you were given.

Importing Java's built in data structures will result in a mark of 0.
*/
@SuppressWarnings({"unchecked"})
public class MinMaxDHeap<T extends Comparable<? super T>>
{
	public Node<T>[] values=null;

	public MinMaxDHeap(int d)
	{
		/* Parameter d specifies the order of your min-max heap. If d = 2, you should construct a binary heap, 
		   if d = 3, you should construct a ternary heap, etc. You may implement this constructor to suit your 
		   needs, or you may add additional constructors. This is the constructor which will be used for marking. */ 
		this.d=d;
		this.size=0;
		this.clear();
	}
	public MinMaxDHeap(MinMaxDHeap<T> copy)
	{
		values= new Node[copy.size];
		for (int i=0;i<copy.size;i++)
		{
			values[i]=new Node<>(copy.values[i]);
			values[i].level=copy.values[i].level;
		}
		this.size=copy.size;
		this.d=copy.d;
	}
	
	/* Insertion */
	public void insert(T data, int key)
	{
		/* Insert a Node object according to its key (priority).
			 The Node object has to be initialised with the given data/key values.
		   Refer to the assignment spec for insertion algorithm details. */
		   if (findNode(data)!=-1)
		   {
			   return;
		   }
		   Node<T> newNode=new Node<>(data,key);
		   if (size==0)
		   {
			   newNode.level=0;
		   }
		   sizeUp();
		   if (size-1>0)
		   {
			   if (size-1<d)
			   {
				   newNode.level=1;
			   }
			   else
			   {
					newNode.level=values[getParentIndex(size-1)].level+1;
			   }	
		   }
		   values[size-1]=newNode;
		   checkLevels();
		   bottomUp(size-1);
		   //System.out.println(this);
	}

	public void bottomUp(int insertIndex)
	{
		checkLevels();
		if (values[insertIndex].level%2==0) //inserted on a  local min level
		{
			if (hasParent(insertIndex)&&values[insertIndex].key>values[getParentIndex(insertIndex)].key)
			{
				//System.out.println("Executed ensureMax with "+ values[insertIndex]+ "that had level"+values[insertIndex].level);
				swap(insertIndex, getParentIndex(insertIndex));
				
				
				ensureMax(getParentIndex(insertIndex));
				
				//bottomUp(getParentIndex(insertIndex));
			}
			else
			{
				//System.out.println("Executed ensureMin with "+ values[insertIndex]);
				ensureMin(insertIndex);
				
			}
		}
		else //inserted on local max level
		{
			if (hasParent(insertIndex)&&values[insertIndex].key<values[getParentIndex(insertIndex)].key)
			{
				//System.out.println("Executed ensureMin with "+ values[insertIndex]+ "that had level"+values[(insertIndex)].level);
				swap(insertIndex, getParentIndex(insertIndex));
				
				ensureMin(getParentIndex(insertIndex));
				//bottomUp(getParentIndex(insertIndex));
			}
			else
			{
				//System.out.println("Executed ensureMax with "+ values[insertIndex]+ "that had level"+values[(insertIndex)].level);
				ensureMax(insertIndex);
			}
		}
	}
	/* Read-only access */
	public T peekMin()
	{
		/* Return the data of the min priority Node. Min-max heap should not be modified by this function. */
		if (size>0 && values[0]!=null)
		{
			return values[0].getData();
		}
		return null;
	}
	
	public T peekMax()
	{
		if (size>1)
		{
			if (findAbsoluteMax()!=-1)
			{
				return values[findAbsoluteMax()].getData();
			}
		}
		return null;
		/* Return the data of the max priority Node. Min-max heap should not be modified by this function. */
	}
	
	public String toString()
	{
		/* Return a breadth-first traversal representation of the Min-Max d-heap by constructing 
		   a comma-separated string of the data stored in the heap. To construct the string,
       iterate over the heap, and append each Node object by invoking the toString() method.
       NB: The output format should contain no spaces and/or new line characters. 
       Individual nodes must be comma-separated. Eg., if alphabetical characters A, B, and C 
       were stored in the min-max heap in this order, you should return the string "A,B,C" 
       */
		String output="";
		for (int i=0;i<size;i++)
		{
			output+=values[i].getData();
			
			if (i<size-1)
			{
				output+=",";
			}
		}
		return output;
	}
	
	
	/* Deletion */
	public T deleteMin()
	{
		/* Remove the Node with the min priority, and return its data. 
			 Min-max heap has to be restructured accordingly: see spec for details. */
		T returnval=null;
		if (size==0)
		{
			return null;
		}
		else if (size==1)
		{
			returnval=values[0].getData();
			clear();
			return returnval;			
		}
		returnval=values[0].getData();
		swap(0, size-1);
		sizeDown();
		checkLevels();
		//System.out.println(this);
		trickleDown(0);
		return returnval;
	}
	
	public T deleteMax()
	{
		T returnval=null;
		if (size==0)
		{
			return null;
		}
		else if (size==1)
		{
			return returnval;			
		}
		if (findAbsoluteMax()!=-1)
		{
			
			int deletedIndex=findAbsoluteMax();
			returnval=values[findAbsoluteMax()].getData();
			swap(deletedIndex, size-1);
			sizeDown();
			//System.out.println(this);
			if (deletedIndex!=size)
			{
				trickleDown(deletedIndex);
			}
			
		}
		return returnval;
		/* Remove the Node with the max priority, and return its data. 
			 Min-max heap has to be restructured accordingly: see spec for details. */
	}
	
	/* Construction */
	public void construct(Node<T>[] arr)
	{
		/* Given an array of Node objects in arbitrary order, construct a min-max heap by 
       applying Floyd's algorithm modified for min-max d-heaps. */

	   clear();
	   int counter=0;
	   for(int i=0;i<arr.length;i++)
	   {
		   if(arr[i]!=null)
		   {
			   ++counter;
		   }
	   }
	   this.size=counter;
	   values=arr;
	   for(int i=size-1;i>=0;i--)
	   {
		   trickleDown(i);
	   }
	}
	
	public void changeD(int newD)
	{
		d=newD;
		Node<T>[] newarr=new Node[size];
		newarr=copyArray(size-1, newarr);
		construct(newarr);
		/* Given a new order d, restructure the current min-max d-heap such that it is a d-heap with d = newD. */
	}
		
	/* Clearing */
	public void clear()
	{
		/* Clear the min-max heap by removing all nodes. */
		values=null;
		size=0;
	}

	/*private int getLevel(int index)
	{
		//System.out.println((int)(Math.log(index) / Math.log(d)));
		if (index==0)
		{
			return 0;
		}
		return ((int)(Math.log(index) / Math.log(d))+1);
	}*/

	private int d; // The d-order of the min-max d-heap
	//custom functions
	public int size=0;

	private int findNode(T element)
	{
		for (int i=0;i<size;i++)
		{
			if (values[i]!=null&&values[i].data.equals(element))
			{
				return i;
			}
		}
		return -1; //node was never found
	}

	private void sizeUp()
	{
		if (size==0)
		{
			values=new Node[1];
			size++;
			return;
		}
		Node<T>[] newarr=new Node[size+1];
		copyArray(size-1, newarr);
		size++;
		values=newarr;
	}

	private void sizeDown()
	{
		if (size==1)
		{
			values=null;
		}
		Node<T>[] newarr=new Node[size-1];
		copyArray(size-2, newarr);
		size--;
		values=newarr;

	}

	public Node<T>[] copyArray(int copyindex, Node<T>[] newarr)
    {
        for (int i=0;i<=copyindex;i++)
        {
            newarr[i]=values[i];
        }
        return newarr;
    }

	public void swap(int Index1, int Index2)
    {
        if (Index1>=size||Index2>=size)
        {
            return;
        }
        Node<T> temp=values[Index1];
		int templev=values[Index2].level;
		values[Index1]=values[Index2];
		values[Index1].level=temp.level;
		values[Index2]=temp;
		values[Index2].level=templev;
		
    }

	

	public void trickleDown(int deleteIndex)
	{
		checkLevels();
		if (getNthChildIndex(deleteIndex, 1)==-1)
		{
			return;
		}
		if (values[deleteIndex].level==0) //deleted at min level
		{
			trickleDownMin(deleteIndex);
			//System.out.println("Trickling "+values[deleteIndex].getData()+" down using min");
			
		}
		else if (values[deleteIndex].level%2==1||values[deleteIndex].level==1) //deleted on absolute max level
		{
			//System.out.println("Trickling "+values[deleteIndex].getData()+" down using max");
			trickleDownMax(deleteIndex);
		}
		else
		{
			trickleDownMin(deleteIndex);
			//System.out.println("Trickling "+values[deleteIndex].getData()+" down using min");
			
		}
		checkStructure();
	}

	public void trickleDownMin(int nodeindex)
	{
		int stuck=0;
		while (true)
		{
			stuck++;
			if (stuck>1000)
			{
				return;
			}
			
			/*if (getSmallestChildIndex(nodeindex)!=-1&&values[nodeindex].key>values[getSmallestChildIndex(nodeindex)].key)
			{
				int smallestchildindex=getSmallestChildIndex(nodeindex);
				swap(nodeindex, getSmallestChildIndex(nodeindex));
				nodeindex=smallestchildindex;
			}
			else
			{
					int smallestgrandchildindex=getSmallestGrandChildIndex(nodeindex);
					if (hasGrandChild(nodeindex)&&values[nodeindex].key>values[smallestgrandchildindex].key)
					{
						swap(nodeindex, smallestgrandchildindex);
						nodeindex=smallestgrandchildindex;
						if (values[nodeindex].key>values[getParentIndex(nodeindex)].key)
						{
							swap(nodeindex, getParentIndex(nodeindex));
							nodeindex=getParentIndex(nodeindex);
						}
					}
					else
					{
						return;
					}
			}*/
				if (getNthChildIndex(nodeindex, 1)==-1)
				{
					return;
				}
				int minchildindex=getSmallestChildIndex(nodeindex);
				//System.out.println("min child for "+values[nodeindex].getData()+" is "+ values[minchildindex].getData());
				int mingrandchild=getSmallestGrandChildIndex(nodeindex);
				if (hasGrandChild(nodeindex)&&values[mingrandchild].key<values[minchildindex].key)
				{		
					if (values[mingrandchild].key<values[nodeindex].key)
					{
						swap(nodeindex, mingrandchild);
					//	System.out.println("Swapped "+values[mingrandchild].getData()+" with grandchild "+values[nodeindex].getData());
						/*if (nodeindex!=0&&values[nodeindex].key<values[getParentIndex(nodeindex)].key)
						{
							System.out.println("Swapped min grandchild"+values[mingrandchild].getData()+" with node parent "+values[getParentIndex(nodeindex)].getData());
							swap(nodeindex, getParentIndex(nodeindex));
							
						}*/
						nodeindex=mingrandchild;
						if (values[nodeindex].key>values[getParentIndex(nodeindex)].key)
						{
							int temp=getParentIndex(nodeindex);
							//System.out.println("Swapped " + values[nodeindex].getData()+ " with larger parent "+ values[temp].getData());
							swap(nodeindex, temp);
							nodeindex=temp;
							//ensuremin(nodeindex);
							return;
						}
						
					}
					else
					{
						return; //smallest grandchild/child is still smaller than node, so it is in right place
					}
				}
				else if (values[minchildindex].key<values[nodeindex].key)
				{
					swap(nodeindex, minchildindex);
				//	System.out.println("Swapped "+values[minchildindex].getData()+" with min child "+values[nodeindex].getData());
					nodeindex=minchildindex;
					continue;
				}
				else
				{
					return; //smallest grandchild/child is still smaller than node, so it is in right place
				}
		}
	}

	public void trickleDownMax(int nodeindex)
	{
		//boolean swapped=true;
		
			//swapped=false;
			int stuck=0;
			while (true)
			{
				stuck++;
				if (stuck>1000)
				{
					return;
				}
				if (getNthChildIndex(nodeindex, 1)==-1)
				{
					return;
				}
				int maxchildindex=getMaxChildIndex(nodeindex);
				//System.out.println("Max child for "+values[nodeindex].getData()+" is "+ values[maxchildindex].getData());
				int maxgrandchild=getMaxGrandChildIndex(nodeindex);
				if (hasGrandChild(nodeindex)&&values[maxgrandchild].key>values[maxchildindex].key)
				{		
					if (values[maxgrandchild].key>values[nodeindex].key)
					{
						swap(nodeindex, maxgrandchild);
					//	System.out.println("Swapped "+values[maxgrandchild].getData()+" with grandchild "+values[nodeindex].getData());
						/*if (nodeindex!=0&&values[nodeindex].key<values[getParentIndex(nodeindex)].key)
						{
							System.out.println("Swapped max grandchild"+values[maxgrandchild].getData()+" with node parent "+values[getParentIndex(nodeindex)].getData());
							swap(nodeindex, getParentIndex(nodeindex));
							
						}*/
						nodeindex=maxgrandchild;
						if (values[nodeindex].key<values[getParentIndex(nodeindex)].key)
						{
							int temp=getParentIndex(nodeindex);
							//System.out.println("Swapped " + values[nodeindex].getData()+ " with larger parent "+ values[temp].getData());
							swap(nodeindex, temp);
							nodeindex=temp;
							//ensureMax(nodeindex);
							return;
						}
						continue;
					}
					else
					{
						return; //largest grandchild/child is still smaller than node, so it is in right place
					}
				}
				if (values[maxchildindex].key>values[nodeindex].key)
				{
					swap(nodeindex, maxchildindex);
					//System.out.println("Swapped "+values[maxchildindex].getData()+" with max child "+values[nodeindex].getData());
					nodeindex=maxchildindex;
					continue;
				}
				else
				{
					return; //largest grandchild/child is still smaller than node, so it is in right place
				}
			}
			
			/*
		
		boolean SWAP=false;
		int number=0;		
		boolean signal=true;
		while(signal==true)
		{
			if(hasGrandChild(nodeindex))
			{
				/*
				if(hasChild(index))
				{
					System.out.println("node "+Nodes[index].key+" has grandchild and child");
				}
				
				if(values[getSmallestChildIndex(nodeindex)].key<values[getMaxGrandChildIndex(nodeindex)].key)
				{
					if(values[nodeindex].key<values[getMaxGrandChildIndex(nodeindex)].key)
					{
						int maxchildindex=getMaxGrandChildIndex(nodeindex);
						swap((nodeindex),maxchildindex);
						nodeindex=maxchildindex;
						//System.out.println("swap 1");
						if(values[nodeindex].key<values[getParentIndex(nodeindex)].key)
						{
							number=nodeindex;
							int parentIndex=getParentIndex(nodeindex);
							swap(nodeindex, getParentIndex(nodeindex));
							nodeindex=parentIndex;
							SWAP=true;
							signal=false;	
						//	System.out.println("node is "+Nodes[index]+" daddy is "+getParentIndex(index));
						//	System.out.println("swap 2");
						}
					}
					else
					{
						if(signal==true)
							{
								signal=false;
							}
					}
				}
				else
				{
					if(values[getMaxChildIndex(nodeindex)]!=null)
					{
						if(values[getMaxChildIndex(nodeindex)].key>values[nodeindex].key)
						{
						//System.out.println("swap 3");
							int maxchildindex= getMaxChildIndex(nodeindex);
							swap(nodeindex,maxchildindex);
							nodeindex=maxchildindex;
						}
						else
						{
							if(signal==true)
							{
								signal=false;
							}
						}
					}
				}
			}
			else if(getNthChildIndex(nodeindex,1)!=-1)
			{
				//System.out.println("node "+Node.key+" has child ");
				if(values[getMaxChildIndex(nodeindex)]!=null)
				{
					if(values[nodeindex].key<values[getMaxChildIndex(nodeindex)].key)
					{
						int maxchildindex=getMaxChildIndex(nodeindex);
						swap(nodeindex,getMaxChildIndex(nodeindex));
						nodeindex=maxchildindex;
					}
					else
					{
						if(signal==true)
							{
								signal=false;
							}
					}
				}
			}
			else
			{
				if(signal==true)
							{
								signal=false;
							}
			}
		}
		if(signal==false&&SWAP==true)
		{
			trickleDownMax(nodeindex);
		}
			*/
	}

	public void ensureMax(int insertIndex)	//swaps with max parents until it finds its place
	{
		int currNodeIndex=insertIndex;
		while (hasGrandparent(currNodeIndex)&&values[currNodeIndex].key>values[getGrandParentIndex(currNodeIndex)].key)
		{
			swap(currNodeIndex, getGrandParentIndex(currNodeIndex));
			currNodeIndex=getGrandParentIndex(currNodeIndex);
		}
		if (getParentIndex(currNodeIndex)!=-1&&values[currNodeIndex].key<values[getParentIndex(currNodeIndex)].key)
		{
			swap(currNodeIndex, getParentIndex(currNodeIndex));
			ensureMin(currNodeIndex);
		}
	}

	public void ensureMin(int insertIndex) //swaps with min parents until it finds its place
	{
		int currNodeIndex=insertIndex;
		while (hasGrandparent(currNodeIndex)&&values[currNodeIndex].key<values[getGrandParentIndex(currNodeIndex)].key)
		{
			//System.out.println("Swapped grandparent "+values[getGrandParentIndex(currNodeIndex)].key+" with grand child "+values[currNodeIndex].getData());
			swap(currNodeIndex, getGrandParentIndex(currNodeIndex)); 
			currNodeIndex=getGrandParentIndex(currNodeIndex);
			
		}
		if (getParentIndex(currNodeIndex)!=-1&&values[currNodeIndex].key>values[getParentIndex(currNodeIndex)].key)
		{
			swap(currNodeIndex, getParentIndex(currNodeIndex));
			ensureMax(currNodeIndex);
		}
	}
	public int getNthChildIndex(int parentIndex, int n)
	{
		if (parentIndex<0)
		{
			return -1;
		}
		if (this.d * parentIndex + n>=size)
		{
			return -1;
		}
		return (this.d * parentIndex + n); 
	}

	public int getParentIndex(int childIndex)
	{
		if (childIndex<=d)
		{
			return 0;
		}
		return (int)(Math.floor((childIndex-1)/this.d));
	}

	public int getGrandParentIndex(int childIndex)
	{
		return getParentIndex(getParentIndex(childIndex)); 
	}

	public int getSmallestGrandChildIndex(int parentIndex)
	{
		int mingrandchild=getNthChildIndex(getNthChildIndex(parentIndex, 1), 1);
		if (mingrandchild==-1)
		{
			return -1;
		}
		int currchildindex=0;
		int currgrandchildindex=0;
		for (int i=1;i<=d;i++)
		{
			currchildindex=getNthChildIndex(parentIndex, i);
			if (currchildindex>=size||currchildindex==-1)
			{
				return mingrandchild;
			}
			for (int j=1;j<=d;j++)
			{
				currgrandchildindex=getNthChildIndex(currchildindex, j);
				if (currgrandchildindex>=size||currgrandchildindex==-1)
				{
					break;
				}
				else
				{	if (values[currgrandchildindex].key<values[mingrandchild].key)
					{
						mingrandchild=currgrandchildindex;
					}
				}
			}
		}
		return mingrandchild;
	}

	public int getSmallestChildIndex(int parentIndex)
	{
		int firstchildindex=getNthChildIndex(parentIndex, 1);
		
		if (firstchildindex==-1)
		{
			return firstchildindex; //will return first child as smallest or -1
		}
		int smallestchildindex=firstchildindex;
		int currchildindex=1;
		for (int i=1;i<=d;i++)
		{
			currchildindex=getNthChildIndex(parentIndex, i);
			if (currchildindex==-1)
			{
				break;
			}
			if (values[currchildindex].key<values[smallestchildindex].key)
			{
				smallestchildindex=i;
			}
		}
		return smallestchildindex;
	}
	public int getMaxChildIndex(int parentIndex)
	{
		int firstchildindex=getNthChildIndex(parentIndex, 1);
		if (firstchildindex==-1)
		{
			return -1;
		}
		int maxchildindex=firstchildindex;
		for (int i=firstchildindex;i<=d;i++)
		{
			if (getNthChildIndex(parentIndex, i)==-1)
			{
				break;
			}
			if (values[i].key>values[maxchildindex].key)
			{
				maxchildindex=i;
			}
		}
		return maxchildindex;
	}

	public int getMaxGrandChildIndex(int parentIndex)
	{
		int Maxgrandchild=getNthChildIndex(getNthChildIndex(parentIndex, 1), 1);
		if (!hasGrandChild(parentIndex))
		{
			return -1;
		}
		int currchildindex=0;
		int currgrandchildindex=0;
		for (int i=1;i<=d;i++)
		{
			currchildindex=getNthChildIndex(parentIndex, i);
			if (currchildindex>=size)
			{
				return Maxgrandchild;
			}
			for (int j=1;j<=d;j++)
			{
				currgrandchildindex=getNthChildIndex(currchildindex, j);
				if (currgrandchildindex==-1)
				{
					continue;
				}
				if (currgrandchildindex>=size)
				{
					break;
				}
				if (values[currgrandchildindex].key>values[Maxgrandchild].key)
				{
					Maxgrandchild=currgrandchildindex;
				}
			}
		}
		return Maxgrandchild;
		/*Node<T>[] maxgrandchildren= new Node[d*d];
		int[] maxgrandindexes= new int[d*d];
		int numchildren=0;
		int maxchildindex=-1;
		int maxgrandchild=0;
		for (int i=1;i<=d;i++)
		{
			maxchildindex=getMaxChildIndex(getNthChildIndex(parentIndex,i));
			if (maxchildindex==-1)
			{
				break;
			}
			else
			{
				maxgrandchildren[i-1]=values[maxchildindex];
				maxgrandindexes[i-1]=maxchildindex;
				numchildren++;
			}
		}
		maxgrandchild=maxgrandindexes[0];
		for (int i=0;i<numchildren;i++)
		{
			if (values[maxgrandchild].key<maxgrandchildren[i].key)
			{
				maxgrandchild=maxgrandindexes[i];
			}
		}
		return maxgrandchild;*/
	}
	

	
	public boolean hasParent(int index){ return getParentIndex(index) >= 0; }
	public boolean hasGrandparent(int index){ return getParentIndex(index) > 0 && getGrandParentIndex(index) >= 0; }
	public boolean hasGrandChild(int index){ return getNthChildIndex(getNthChildIndex(index, 1), 1)!=-1&&getNthChildIndex(getNthChildIndex(index, 1), 1) < size; }
	public int findAbsoluteMax()
	{
		int maxindex=0;
		for (int i=0;i<size;i++)
		{
			if (values[i].key>values[maxindex].key)
			{
				maxindex=i;
			}
		}
		return maxindex;
	}
	public void checkLevels()
	{
		for (int i=0;i<size;i++)
		{
			if (i==0)
			{
				values[i].level=0;
			}
			else
			{
				values[i].level=values[getParentIndex(i)].level+1;
			}
			
		}
	}
	public void checkStructure()
	{
		for (int i=0;i<size;i++)
		{
			bottomUp(i);
		}
	}
	
}
