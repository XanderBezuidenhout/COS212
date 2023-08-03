@SuppressWarnings("unchecked")
public class BST<T extends Comparable<? super T>> {

	protected BSTNode<T> root = null;

	public BST() {
	}

	public void clear() {
		root = null;
	}

	// returns a verbose inorder string of the BST
	public String inorder(BSTNode<T> node) {
		boolean verbose = true;
		if (node != null) {
			String result = "";
			result += inorder(node.left);
			if (verbose) {
				result += node.toString() + "\n";
			} else {
				result += node.element.toString() + " ";
			}
			result += inorder(node.right);
			return result;
		}
		return "";
	}

	////// You may not change any code above this line //////

	////// Implement the functions below this line //////

	public boolean isEmpty() {
		// Your code goes here
		return (root==null);
	}

	public BSTNode<T> clone() {
		// Your code goes here
		BST<T> newTree=new BST<>();
		if (this.root==null)
		{
			return (null);
		}
		newTree.root=new BSTNode<>(this.root.element);
		newTree.root.left=newTree.copytree(newTree.root.left,this.root.left);
		newTree.root.right=newTree.copytree(newTree.root.right,this.root.right);
		//newTree.copytree(newTree.root, this.root);
		return (newTree.root);
	}

	public BSTNode<T> copytree(BSTNode<T> currNode, BSTNode<T> orgNode)
	{
		if (orgNode==null)
		{
			currNode=null;
			return null;
		}
		else
		{
			currNode= new BSTNode<>(orgNode.element);
			currNode.left=new BSTNode<>();
			currNode.right=new BSTNode<>();

			currNode.left=copytree(currNode.left, orgNode.left);
			
			currNode.right=copytree(currNode.right, orgNode.right);
			
			return currNode;
		}
		//currNode=new BSTNode<>();
		
		
		/*if (orgNode!=null)
		{
			
			BSTNode<T> newNode=new BSTNode<>(orgNode.element);
			if (prevCopiedNode!=null)
			{
				prevCopiedNode.left=newNode
				if (prevCopiedNode.element.compareTo(newNode.element)<0)
				{
					prevCopiedNode.left=newNode;
				}
				else
				{
					prevCopiedNode.right=newNode;
				}
			}
			copytree(newNode.left, orgNode.left);
			copytree(newNode.right, orgNode.right);
		}
		else
		{
			prevCopiedNode=null;
		}*/
	}
	
	public BSTNode<T> mirrortree(BSTNode<T> currNode, BSTNode<T> orgNode)
	{
		if (orgNode==null)
		{
			return null;
		}/*
		else if (currNode==null)
		{
			System.out.println("Hmmm, null pointer passed");
			return null;
		}*/
		else
		{
			currNode= new BSTNode<>(orgNode.element);
			currNode.right=new BSTNode<>();
			currNode.left=new BSTNode<>();

			currNode.right=mirrortree(currNode.right, orgNode.left);
			
			currNode.left=mirrortree(currNode.left, orgNode.right);
			
			return currNode;
		}
	}
	public BSTNode<T> mirror() 
	{
		BST<T> newTree=new BST<>();
		if (this.root==null)
		{
			return (newTree.root);
		}
		newTree.root=new BSTNode<>(this.root.element);
		newTree.root.right=newTree.mirrortree(newTree.root.left,this.root.left);
		newTree.root.left=newTree.mirrortree(newTree.root.right,this.root.right);
		//newTree.copytree(newTree.root.left, this.root.right);
		return (newTree.root);
		// Your code goes here
	}

	public void insert(T element) 
	{
		/*BSTNode<T> newNode=new BSTNode<>(element);
		if (this.root==null)
		{
			this.root=newNode;
			return;
		}
		if (search(element).equals(element))
		{
			return;
		}
		if (root.element.compareTo(element)>0)
		{
			T largestButSmaller=largest(newNode, null, root.right);
		}
		else
		{

		}*/
			if (search(element)==element)
			{
				return;
			}
			BSTNode<T> p = root, prev = null;
			while (p != null) 
			{ // find a place for inserting new node;
				prev = p;
				if (p.element.compareTo(element) < 0)
				{
					p = p.right;
				}
				else
				{
					p = p.left;
				}
			}
			if (root == null) // tree is empty;
			{
				root = new BSTNode<>(element);
			}
			else if (prev.element.compareTo(element) < 0)
			{
				prev.right = new BSTNode<>(element);
			}
			else
			{
				prev.left = new BSTNode<>(element);
			} 

		// Your code goes here
	}

	public boolean deleteByMerge(T element) 
	{
		if (isEmpty())
		{
			return false;
		}
		BSTNode <T> toDelete= finder(root, element);
		if (search(element)==null)
		{
			return false;
		}

		if (toDelete==null)
		{
			return false;
		}
		if (toDelete==root)
		{
			if (root.left==null)
			{
				root=root.right;
				return true;
			}
			else
			{
				if (getSuccessor(root.left.element)!=null)
				{
					finder(root,getSuccessor(root.left.element)).right=root.right;
					root=root.left;
				}
				else
				{
					root.left.right=root.right;
					root=root.left;
				}
				return true;
			}
		}
		
		BSTNode<T> currNode= root;
		BSTNode<T> prev=null;
		while (currNode!=toDelete)
		{
			prev=currNode;
			if (currNode.element.compareTo(element)>0)
			{
				currNode=currNode.left;
			}
			else
			{
				currNode=currNode.right;
			}
		}
		if (prev.right==toDelete)
		{
			prev.right=copytree(prev.right, toDelete.left);
			if (toDelete.right!=null)
			{
				finder(toDelete.left, largest(toDelete.left)).right=toDelete.right;
			}
			return true;			
		}
		else 
		{
			prev.left=copytree(prev.left, toDelete.left);
			if (toDelete.right!=null)
			{
				finder(toDelete.left, largest(toDelete.left)).right=toDelete.right;
			}
			return true;
		}
		

	//	BST<T> mergeTree=new BST<>();
		//mergeTree.root= toDelete;
		//mergeTree.root.left=mergeTree.copytree(mergeTree.root.left,toDelete.left);
		//mergeTree.root.right=mergeTree.copytree(mergeTree.root.right,toDelete.right);

		/*BSTNode<T> p=root,prev=null;
		while (p != null && p.element != element) 
		{ // find the node p
			prev = p; // with element el;
			if (p.element.compareTo(element) < 0)
			{
				p = p.right;
			}
			else
			{
				p = p.left;
			}
		}
		BSTNode<T> currNode=toDelete;
		BSTNode <T> temp;*/
		/*
		if (currNode.right==null)
		{
			currNode=currNode.left;
		}
		else if (currNode.left==null)
		{
			currNode=currNode.right;
		}
		else
		{
			temp=currNode.left;
			while (temp.right!=null)
			{
				temp=temp.right;
			}
			temp.right=currNode.right;
			currNode=currNode.left;
		}
		if (toDelete==root)
		{
			root=currNode;
		}
		else if (prev.left==toDelete)
		{
			prev.left=currNode;
		}
		else
		{
			prev.right=currNode;
		}
		return true;*/
		// Your code goes here
	}

	public boolean deleteByCopy(T element)  
	{
		if (isEmpty())
		{
			return false;
		}
		BSTNode <T> toDelete= finder(root, element);
		if (toDelete==null)
		{
			return false;
		}
		
		BSTNode<T> currNode= root;
		BSTNode<T> previous=null;
		//T largestleft=largest(toDelete);
		BSTNode<T> newNode=finder(root,largest(toDelete.left));

		BSTNode<T> prev=null;
		previous=findPrev(root, newNode.element, null);
		prev=findPrev(root, element, null);
		//System.out.println("New head will be: "+newNode.element);
		//System.out.println("The prev node it will be unlinked from is: "+ previous.element);
	
		
		if (toDelete==root)
		{
			if (root.left==null)
			{
				root=root.right;
				return true;
			}
			newNode.left=root.left;
			newNode.right=root.right;
			root=newNode;
			if (previous.right==toDelete)
			{
				previous.right=null;
			}
			else
			{
				previous.left=null;
			}
			
			return true;
		}

		if (toDelete.left==toDelete.right)
		{
			if (prev.left==toDelete)
			{
				prev.left=newNode;
			}
			else
			{
				prev.right=newNode;
			}
			return true;
		}
		if (prev.right==toDelete)
		{
			prev.right=null;
		}
		else
		{
			prev.left=null;
		}

		if (newNode!=toDelete.left)
		{
			newNode.left=toDelete.left;
		}
		if (newNode!=toDelete.right)
		{
			newNode.right=toDelete.right;
		}
		if (toDelete==prev.right)
		{
			prev.right=newNode;
		}
		else
		{
			prev.left=newNode;
		}
		

		if (toDelete==previous.right)
		{
			previous.right=null;
		}
		else
		{
			previous.left=null;
		}
		
		/*if (previous.right!=null)
			{
				previous.right=null;
			}
			else
			{
				previous.left=null;
			}*/
		return true;
		/*
		BSTNode<T> node, p = root, prev = null;
		while (p != null && p.element != element) 
		{ // find the node p
			prev = p; // with element el;
			if (p.element.compareTo(element) < 0)
			{
				p = p.right;
			}
			else
			{
				p = p.left;
			}
		}
		node = p;
		
		if (p != null && p == element) 
		{
			if (node.right == null) // node has no right child;
			{
				node = node.left;
			}	
			else if (node.left == null) // no left child for node;
			{	
				node = node.right;
			}
			
			else 
			{
				BSTNode <T> tmp = node.left; // node has both children;
				BSTNode <T> previous = node; // 1.
				while (tmp.right != null) 
				{ // 2. find the rightmost
					previous = tmp; // position in the
					tmp = tmp.right; // left subtree of node;
				}
				node.element = tmp.element; // 3. overwrite the reference
				// of the key being deleted;
				if (previous == node) // if node's left child's
				{
					previous.left = tmp.left;
				} // right subtree is null;
				else 
				{
					previous.right = tmp.left;
				} // 4.
			}
			if (p == root)
			{
				root = node;
			}
			else if (prev.left == p)
			{
				prev.left = node;
			}
			else
			{
				prev.right = node;
			} 
		}

		return true;*/
		// Your code goes here
	}

	public T search(T element) 
	{
		if (finder(this.root,element)!=null)
		{
			return element;
		}
		else
		{
			return null;
		}
		/*BSTNode<T> p=root;
		while (p != null)
		{
			if (p.element.equals(element))
			{
				return p.element;
			}
			else if (p.element.compareTo(element) > 0)
			{
				p = p.left;
			}
			else
			{
				p = p.right;
			} 
		}
		return null;*/

		// Your code goes here
	}

	public BSTNode<T> finder(BSTNode<T> currNode,T element)
	{
		if (currNode==null)
		{
			return null;
		}
		else
		{
			if (currNode.element.equals(element))
			{
				return currNode;
			}
			else if (currNode.element.compareTo(element)>0)
			{
				return (finder(currNode.left,element));	
			}
			else
			{
				return (finder(currNode.right,element));
			}
			
		}
	}
	public T largest(BSTNode<T> orgNode)
	{
		
		if (orgNode==null)
		{
			return null;
		}
		if (orgNode.right==null)
		{
			return orgNode.element;
		}
		else
		{
			T max=orgNode.element;
			BSTNode<T> currNode=orgNode;
			while(currNode!=null)
			{
				if (currNode.right!=null)
				{
					max=currNode.right.element;
					
				}
				currNode=currNode.right;
				
			}
			return max;
		}

		/*if (currNode==null)
		{
			return max;
		}
		else if (orgNode==null)
		{
			return null;
		}
		else
		{
			if (max==null)
			{
				max=currNode.element;
			}
			else if ((currNode.element.compareTo(max)>0&&currNode.element.compareTo(orgNode.element)<0))
			{
				max=currNode.element;
			}
			T largeLeft=largest(orgNode, max, currNode.left);
			T largeRight=largest(orgNode, max, currNode.right);
			if (largeLeft.compareTo(max)>0)
			{
				max=largeLeft;
			}
			if (largeRight.compareTo(max)>0)
			{
				max=largeRight;
			}
			return max;
		}*/
	}
	public T getPredecessor(T element) 
	{
		/*if (isEmpty())
		{
			return null;
		}
		else if (element==search(element))
		{
			BSTNode <T> foundNode=finder(this.root, element);
			return largest(foundNode, null, root);
		}
		else
		{
			return null;
		}*/
		
		// Your code goes here
		if (root==null)
		{
			return null;
		}
		else if (root.left==null)
		{
			return null;
		}
		
		if (search(element)==null)
		{
			return null;
		}
		BSTNode<T> foundNode=finder(root, element);
		BSTNode<T> currNode=root;
		BSTNode<T> prev=null;
		BSTNode<T> max=root.left;
		foundNode=foundNode.left;
		while (foundNode!=null)
		{
			prev=foundNode;
			foundNode=foundNode.right;
		}
		if (prev!=null)
		{
			return prev.element;   //found by going left then all the way right
		}
		else
		{
			foundNode=finder(root, element);
				while(currNode!=null)
				{
					//prev=currNode;
					if (currNode.right==foundNode)
					{
						return currNode.element;
					}
					else
					{
						if (currNode.left!=null && currNode.element.compareTo(element)>0)
						{
							currNode=currNode.left;
						}
						else
						{
							currNode=currNode.right;
						}
					}
				}
		return null;
		}
		
		


	}

	public T getSuccessor(T element) 
	{
		if (root==null)
		{
			return null;
		}
		else if (root.left==null)
		{
			return null;
		}
		
		if (search(element)==null)
		{
			return null;
		}
		BSTNode<T> prev=null;
		BSTNode<T> foundNode=finder(root, element);
		if (foundNode.right!=null)
		{
			foundNode=foundNode.right;
			while (foundNode!=null)
			{
				prev=foundNode;
				foundNode=foundNode.left;
			}
			return prev.element;
			
		}
		else
		{
			prev=null;
			foundNode=finder(root, element);
			BSTNode<T> currNode=root;
			BSTNode<T> min=new BSTNode<>(root.element);
			if (foundNode.element.compareTo(root.element)<0)
			{
				currNode=root.left;
			}
			else
			{
				currNode=root.right;
				if (foundNode.right==null)
				{
					return null;
				}
			}
			while (currNode!=null)
			{
				prev=currNode;
				if (currNode.element.compareTo(min.element)<0 && currNode.element.compareTo(element)>=0 && (!currNode.element.equals(element)))
				{
					min=currNode;
				}
				if (currNode.left!=null&&currNode.left.element.compareTo(element)>0)
				{
					currNode=currNode.left;
				}
				else
				{
					currNode=currNode.right;
				}

			}
			return min.element;
		}
		
		
		/*
		
		if (foundNode==root)
		{
			if (foundNode.right==null)
			{
				return null;
			}
			foundNode=foundNode.right;
			while (foundNode!=null)
			{
				prev=foundNode;
				if (foundNode.left!=null)
				{
					foundNode=foundNode.left;
				}
				else
				{
					foundNode=foundNode.right;
				}
			}
		}
	
		foundNode=foundNode.right;
		while (foundNode!=null)
		{
			prev=foundNode;
			if (foundNode.left!=null)
			{
				foundNode=foundNode.left;
			}
			else
			{
				foundNode=foundNode.right;
			}
			
		}
		if (prev!=null)
		{
			return prev.element;   //found by going left then all the way right
		}
		else
		{
				foundNode=finder(root, element);
				while(currNode!=null)
				{
					if (currNode.left==foundNode)
					{
						return currNode.element;
					}
					else
					{
						if (min.element==element)
						{
							min.element=currNode.element;
						}
						if (currNode.element.compareTo(min.element)<0&&currNode.element.compareTo(element)>0)
						{
							min=currNode;
						}
						
						if (currNode.left!=null && currNode.element.compareTo(element)>0)
						{
							currNode=currNode.left;
						}
						else
						{
							currNode=currNode.right;
						}
					}
				}
			return min.element;
		}*/

		// Your code goes here
	}
	public T smallest(BSTNode<T> orgNode, T min, BSTNode<T> currNode)
	{
		if (currNode==null)
		{
			return min;
		}
		else
		{
			if (min==null)
			{
				min=currNode.element;
			}
			else if (currNode.element.compareTo(min)<0&&currNode.element.compareTo(orgNode.element)>0)
			{
				min=currNode.element;
			}
			T smallLeft=smallest(orgNode, min, currNode.left);
			T smallRight=smallest(orgNode, min, currNode.right);
			if (smallLeft.compareTo(min)<0)
			{
				min=smallLeft;
			}
			if (smallRight.compareTo(min)<0)
			{
				min=smallRight;
			}
			return min;
		}
	}

	public BSTNode<T> findPrev (BSTNode<T> currNode,T element, BSTNode<T> prev)
	{
		//BSTNode <T> prev=null;
		BSTNode <T> tempnode=root;
		while (!tempnode.element.equals(element))
		{
			prev=tempnode;
			if (tempnode.element.compareTo(element)>0)
			{
				tempnode=tempnode.left;
			}
			else
			{
				tempnode=tempnode.right;
			}

		}
		return prev;
	}
}