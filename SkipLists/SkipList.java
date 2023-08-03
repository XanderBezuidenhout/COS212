import java.util.Random;

@SuppressWarnings("unchecked")
public class SkipList<T extends Comparable<? super T>> {

	public int maxLevel;
	public SkipListNode<T>[] root;
	private int[] powers;
	private Random rd = new Random();

	SkipList(int i) {
		maxLevel = i;
		root = new SkipListNode[maxLevel];
		powers = new int[maxLevel];
		for (int j = 0; j < i; j++)
			root[j] = null;
		choosePowers();
		rd.setSeed(202003); // do not modify this line
	}

	SkipList() {
		this(4);
	}

	public void choosePowers() {
		powers[maxLevel - 1] = (2 << (maxLevel - 1)) - 1;
		for (int i = maxLevel - 2, j = 0; i >= 0; i--, j++)
			powers[i] = powers[i + 1] - (2 << j);
	}

	public int chooseLevel() {
		int i, r = Math.abs(rd.nextInt()) % powers[maxLevel - 1] + 1;
		for (i = 1; i < maxLevel; i++) {
			if (r < powers[i])
				return i - 1;
		}
		return i - 1;
	}

	public boolean isEmpty() 
	{
		// your code goes here
		for (int level=maxLevel-1;level>=0;level--)
		{
			if (root[level]!=null)
			{
				return false;
			}
		}
		return true;
	}

	public void insert(T key) {
		// your code goes here
		SkipListNode<T>[] currNode = new SkipListNode[maxLevel];
		SkipListNode<T>[] prevNode = new SkipListNode[maxLevel];
		
		currNode[maxLevel-1] = root[maxLevel-1];
		prevNode[maxLevel-1] = null;
		for (int level = maxLevel - 1; level >= 0; level--) 
		{
			while (currNode[level] != null && currNode[level].key.compareTo(key)<0) 
			{ // go to the next
				prevNode[level] = currNode[level]; // if smaller;
				currNode[level] = currNode[level].next[level];
			}
			if (currNode[level] != null && currNode[level].key.equals(key)) // don't include
			{
				return; // duplicates;
			}
			if (level>0)
			{
				if (prevNode[level] == null) 
				{ // if not the lowest
					currNode[level-1] = root[level-1]; // level, using a link
					prevNode[level-1] = null; // either from the root
				}
				else 
				{ // or from the predecessor;
					currNode[level-1] = prevNode[level].next[level-1];
					prevNode[level-1] = prevNode[level];
				}
			}
			
		}
		int nodelevel = chooseLevel(); // generate randomly level
		// for newNode;
		SkipListNode<T> newNode = new SkipListNode<T>(key,nodelevel+1);
		for (int i = 0; i <= nodelevel; i++) 
		{ // initialize next fields of
			newNode.next[i] = currNode[i]; // newNode and reset to newNode
			if (prevNode[i] == null) // either fields of the root
			{
				root[i] = newNode;
			} // or next fields of newNode's
			else
			{
				prevNode[i].next[i] = newNode; // predecessors;
			} 
		}

	}

	public boolean delete(T key) 
	{
		// your code goes here
		boolean found=false;
		SkipListNode<T> currNode = null;
		SkipListNode<T> prevNode = null; 
		for (int level=maxLevel-1;level>=0;level--)
		{
			currNode=root[level];
			prevNode=null;

			while (currNode!=null)
			{
				if (currNode.key.equals(key))
				{
					found=true;
					break;
				}
				prevNode=currNode;
				currNode=currNode.next[level];
			}
			if (prevNode==null)
			{
				if (currNode==null)
				{
					root[level]=null;
				}
				else
				{
					root[level]=currNode.next[level];
				}	
			}
			else
			{
				if (currNode==null)
				{
					prevNode.next[level]=null;
				}
				else
				{
					prevNode.next[level]=currNode.next[level];
				}
				
			}
		}
		return found;
		/*for (int level=maxLevel-1;level>=0;level--)
		{
			SkipListNode<T> currNode = root[level];
		SkipListNode<T> prevNode = null;  
		
		while (currNode!=null)
		{
			if (currNode.key.equals(key))
			{
				found=true;
				break;
			}
			prevNode=currNode;
			currNode=currNode.next[level];
		}
		if (currNode==null)
		{
			continue;
		}
		else
		{
			if (prevNode==null)
			{
				
					root[level]=null;
				
				
				continue;
			}
			
			
				if (currNode!=null)
				{
					prevNode.next[level]=currNode.next[level];
				}
				else
				{
					prevNode.next[level]=null;
				}
		
		}
		
	}
		
	return found;*/
		
}

	public T first() {
		// your code goes here
		return (root[0].key);
	}

	public T search(T key) {
		// your code goes here
		boolean found=false;
		T keyreturn=null;
		SkipListNode<T> currNode = root[maxLevel-1];
		SkipListNode<T> prevNode=null; 
		for (int level =maxLevel-1;level>=0;level--)
		{
			if (currNode==null || currNode.key.compareTo(key) >0)
			{
				if (prevNode==null)
				{
					if (level==0)
					{
						return null; 
					}
					else
					{
						currNode=root[level-1];
					}
					
				}
				else
				{
					if (level==0)
					{
						return null; 
					}
					else
					{
						currNode=prevNode.next[level-1];
					}
				}
				continue;
			}
			while (currNode!=null)
			{
				if (currNode.key.equals(key))
				{
					found=true;
					keyreturn=currNode.key;
					return currNode.key;
				}
				currNode=currNode.next[level];
			}
			if (found)
			{
				return keyreturn;
			}
		}
		return null;
	}
	
	public String getPathToLastNode()
	{

		String path="";
		int mylevel=maxLevel-1;
		SkipListNode<T>[] currNode =new SkipListNode[maxLevel];
		if (isEmpty())
		{
			return "";
		}
		else
		{
			 
			for (int level=maxLevel-1;level>=0;level--)
			{
				currNode[level]=root[level];				
			}	
			while (mylevel>=0)
			{
				if (currNode[mylevel]!=null)
				{
					
					if (currNode[mylevel].next[mylevel]==null)
					{
						if (mylevel>0)
						{
							currNode[mylevel-1]=currNode[mylevel];
						}
						else
						{
							path+="["+currNode[mylevel].key.toString()+"]";
						}
						mylevel--;
					}
					else
					{
						path+="["+currNode[mylevel].key.toString()+"]";
						currNode[mylevel]=currNode[mylevel].next[mylevel];
					}
				}				
				else
				{
					mylevel--;
				}
			}
			return path;
		}
		

	}
	

}