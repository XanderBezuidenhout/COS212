/**
 * Name: Xander Bezuidenhout
 * Student Number: 20425997
 */

public class SplayTree<T extends Comparable<T>> {

	protected enum SplayType {
		SPLAY,
		SEMISPLAY,
		NONE
	}	

	protected Node<T> root = null;
	
	/**
	 * Prints out all the elements in the tree
	 * @param verbose
	 *			If false, the method simply prints out the element of each node in the tree
	 *			If true, then the output provides additional detail about each of the nodes.
	 */
	public void printTree(boolean verbose) {
		String result;
		result = preorder(root, verbose);
		System.out.println(result);
	}
	
	protected String preorder(Node<T> node, boolean verbose) {
		if (node != null) {
			String result = "";
			if (verbose) {
				result += node.toString()+"\n";
			} else {
				result += node.elem.toString() + " ";
			}
			result += preorder(node.left, verbose);
			result += preorder(node.right, verbose);
			return result;
		}
		return "";
	}
	
	////// You may not change any code above this line //////

	////// Implement the functions below this line //////
	
	/**
	* Inserts the given element into the tree, but only if it is not already in the tree.
	* @param elem 
	* 		 	The element to be inserted into the tree
	* @return 
	*			Returns true if the element was successfully inserted into the tree. 
	*			Returns false if elem is already in the tree and no insertion took place.
	*
	*/
	public boolean insert(T elem) {

		//Your code goes here
		if (root==null)
        {
            root=new Node<>(elem);  //if the tree has no nodes yet
            return true;
        }
        else if (contains(elem))
        {
            return false;  // avoid duplicates
        }
        Node<T> currNode=root;
        Node<T> prev=null;
        while (currNode!=null)
        {
            prev=currNode;
            if (currNode.elem.compareTo(elem)>0)    //checks if current node is larger
            {
                currNode=currNode.left;  //moves to left subtree if the elem is smaller than current node
            }
            else
            {
                currNode=currNode.right; //moves to right subtree if the elem is bigger than current node
            }
        }
        if (prev.elem.compareTo(elem)>0)  //checks whether to make it left or right child
        {
            prev.left=new Node<>(elem);
        }
        else
        {
            prev.right=new Node<>(elem);
        }
		return true;
	}
	
	/**
	* Checks whether a given element is already in the tree.
	* @param elem 
	* 		 	The element being searched for in the tree
	* @return 
	*			Returns true if the element is already in the tree
	*			Returns false if elem is not in the tree
	*
	*/
	public boolean contains(T elem) {
		Node<T> currNode=root;
        while (currNode!=null)
        {
            if (currNode.elem.equals(elem))
            {
                return true;  //the node has been found in the tree
            }
            if (currNode.elem.compareTo(elem)>0)    //checks if current node is larger
            {
                currNode=currNode.left;  //moves to left subtree if the element is smaller than current node
            }
            else
            {
                currNode=currNode.right; //moves to right subtree if the element is bigger than current node
            }
        }
        return false; //no node was found
		//Your code goes here
	}
	
	/**
	 * Accesses the node containing elem. 
	 * If no such node exists, the node should be inserted into the tree.
	 * If the element is already in the tree, the tree should either be semi-splayed so that 
	 * the accessed node moves up and the parent of that node becomes the new root or be splayed 
	 * so that the accessed node becomes the new root.
	 * @param elem
	 *			The element being accessed
	 * @param type
	 *			The adjustment type (splay or semi-splay or none)
	 */
	public void access(T elem, SplayType type) {

		//Your code goes here
		if (!contains(elem))
		{
			return;
		}
		if (type==SplayType.SPLAY)
		{
			splay(finder(elem));
		}
		else if(type==SplayType.SEMISPLAY)
		{
			semisplay(finder(elem));
		}
	}
	
	/**
	 * Semi-splays the tree using the parent-to-root strategy
	 * @param node
	 *			The node the parent of which will be the new root
	 */
	protected void semisplay(Node<T> node) 
	{
		
		//Your code goes here
		if (node==null)
		{
			return;
		}
		//Node<T> parent=findParent(node.elem);
			Node<T> parent=findParent(node.elem);
			Node<T> grandparent=null;
			
			if (root==parent)
			{
				Rotate(node);
				root=node;
				return;
			}
			/*else*/ if (determineHomo(node))
			{
				while (parent!=root)
				{
					if (parent!=null)
					{
						grandparent=findParent(parent.elem);
					}
					Rotate(parent);
					if (root==grandparent)
					{
						root=parent;
					}
				}
				return;
			}
			else
			{
				splay(node);
				/*
				while (node!=root)
				{
					Rotate(node);
					Rotate(node);
					if (grandparent==root)
					{
						root=node;
					}
				}*/
			}
	}

	/**
	 * Splays the tree using the node-to-root strategy
	 * @param node
	 *			The node which will be the new root
	 */
	protected void splay(Node<T> node) {
		
		//Your code goes here
		if (node==null)
		{
			return;
		}
		//Node<T> parent=findParent(node.elem);
		Node<T> parent=findParent(node.elem);
		Node<T> grandparent=null;
		if (parent!=null)
		{
			grandparent=findParent(parent.elem);
		}
		while (node!=root)
		{	
			parent=findParent(node.elem);
			if (parent!=null)
			{
				grandparent=findParent(parent.elem);
			}
			if (root==parent)
			{
				Rotate(node);
				root=node;
				return; 
			}
			else if (determineHomo(node))
			{
				Rotate(parent);
				if (root==grandparent)
				{
					root=parent;
				}
				Rotate(node);
				if (root==parent)
				{
					root=node;
				}
			}
			else
			{
				Rotate(node);
				Rotate(node);
				if (grandparent==root)
				{
					root=node;
				}	
			}
		}
	}
	
	//Helper functions
	public Node<T> finder(T element)
    {
        Node<T> currNode=root;
        while (currNode!=null)
        {
            if (currNode.elem.equals(element))
            {
                return currNode;  //the node has been found in the tree
            }
            if (currNode.elem.compareTo(element)>0)    //checks if current node is larger
            {
                currNode=currNode.left;  //moves to left subtree if the element is smaller than current node
            }
            else
            {
                currNode=currNode.right; //moves to right subtree if the element is bigger than current node
            }
        }
        return null; //no node was found 
    }
	public Node<T> findParent(T element)
    {
        if (!contains(element))
        {
            return null;
        }
        Node<T> currNode=root;
        Node<T> toSearch=finder(element);
        while (currNode!=null)
        {
            if (currNode.left==toSearch||currNode.right==toSearch)
            {
                return currNode;
            }
            else if(currNode.elem.compareTo(element)>0)
            {
                currNode=currNode.left;
            }
            else
            {
                currNode=currNode.right;
            }
        }
        return null;
    }
	public boolean determineHomo(Node<T> node)
	{
		//check for nullpointers, the way this is used now, there wont be any
		Node<T> parent=findParent(node.elem);
		Node<T> grandparent=findParent(parent.elem);
		if (grandparent.left==parent&&parent.left==node)
		{
			return true;
		}
		else if (grandparent.right==parent&&parent.right==node)
		{
			return true;
		}
		return false;
	}
	private Node<T> rotateLeft(Node<T> node)    //returns the node that is now in the place of the rotated node
    {
        if (node==null)
        {
            return null;
        }
        Node<T> grandParent=findParent(node.elem);
        Node<T> parent=node;
        Node<T> child=parent.right;
        parent.right=child.left;
        child.left=parent;
        if (grandParent!=null) //if rotated parent has a parent 
        {
            if (grandParent.right==parent)
            {
                grandParent.right=child;
            }
            else
            {
                grandParent.left=child;
            }
        }
        return child;
    }
    private Node<T> rotateRight(Node<T> node)  //returns the node that is now in the place of the rotated node
    {
        if (node==null)
        {
            return null;
        }
        Node<T> grandParent=findParent(node.elem);
        Node<T> parent=node;
        Node<T> child=parent.left;
        parent.left=child.right;
        child.right=parent;
        if (grandParent!=null) //if rotated parent has a parent 
        {
            if (grandParent.right==parent)
            {
                grandParent.right=child;
            }
            else
            {
                grandParent.left=child;
            }
        }
        return child;
    }
	private void Rotate(Node<T> childToRotate)
	{
		Node<T> parent=findParent(childToRotate.elem);
		if (parent.left==childToRotate)
		{
			rotateRight(parent);
		}
		else
		{
			rotateLeft(parent);
		}
	}
/*	protected void fullSplay(Node<T> node)
	{
		if (node==null)
		{
			return;
		}
		else if (root==node)
		{
			return;
		}
		Node<T> parent=findParent(node.elem);
		Node<T> grandparent=findParent(parent.elem);
		if (root==parent)
		{
			Rotate(node);
			root=node;
		}
		else if (determineHomo(node))
		{
			Rotate(parent);
			if (root==grandparent)
			{
				root=parent;
			}
			Rotate(node);
			if (root==parent)
			{
				root=node;
			}
		}
		else
		{
			Rotate(node);
			Rotate(node);
			if (grandparent==root)
			{
				root=node;
			}
		}
	}*/

}