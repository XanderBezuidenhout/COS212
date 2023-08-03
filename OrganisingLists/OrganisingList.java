/**
 * Name: Xander Bezuidenhout
 * Student Number: 20425997
 */

abstract class OrganisingList {

    public ListNode root = null;

    public OrganisingList() {

    }
    
    /**
     * Calculate the data field of all nodes in the list using the recursive functions.
     */
    public void calculateData() {
        if (root != null) {
            dataRec(root);
        }
    }

    ////// You may not change any code above this line //////

    ////// Implement the functions below this line //////

    
    //Recursive functions

    /**
     * Calculate the sum of keys recursively, starting with the given node until the end of the list
     * @return The sum of keys from the current node to the last node in the list
     * NOTE: 'int' and not 'Integer' here because it cannot return 'null'
     */
    public static int sumRec(ListNode node) 
    {
        if (node.next==null)
        {
            return node.key;
        }
        else
        {
            //System.out.println("INFINITE RECURSION");
            return (node.key+sumRec(node.next));
        }
        //Your code goes here

    }

    /**
     * Calculate and set the data for the given node.
     * @return The calculated data for the given node
     * NOTE: 'int' and not 'Integer' here because it cannot return 'null'
     */
    public static int dataRec(ListNode node) 
    {
        if (node.next==null)
        {
            node.data=node.key;
        }
        else
        {
            node.data=(node.key*sumRec(node))-(dataRec(node.next));
        }
        return node.data;
        //Your code goes here

    }


    //Organising List functions

    /**
     * Retrieve the node with the specified key, move the node as required and recalculate all data fields.
     * @return The node with its data value before it has been moved, otherwise 'null' if the key does not exist.
     * Implement only in specific subclass!
     */
    public abstract ListNode searchNode(Integer key);

    /**
     * Check if a key is contained in the list
     * @return true if the key is in the list, otherwise false
     */
    public boolean contains(Integer key) 
    {
        ListNode currNode=root;
        while (currNode!=null)
        {
            if (currNode.key==key)
            {
                return true;
            }
            else
            {
                currNode=currNode.next;
            }
        }
        return false;
        //Your code goes here

    }

    /**
     * Insert a new key into the linked list.
     * New nodes should be inserted at the back.
     * calculateData() should be called after insertion.
     * Duplicate keys should not be added.
     */
    public void insert(Integer key) 
    {
        if (contains(key))
        {
            return;
        }
        else
        {
            if (root==null)
            {
                root=new ListNode(key);
                calculateData();
                return;
            }
            ListNode currNode=root;
            ListNode newNode=new ListNode(key);
            while (currNode.next!=null)
            {
                if (currNode.key==key)
                {
                    return;
                }
                else
                {
                    currNode=currNode.next;
                }
            }
            currNode.next=newNode;
            newNode.next=null;
            calculateData();
        }
        
        //newNode.data=dataRec(newNode);
        //Your code goes here

    }
	
    /**
     * Delete the node with the given key.
     * calculateData() should be called after deletion.
     * If the key does not exist, do nothing.
     */
    public void delete(Integer key) 
    {
        if (!contains(key))
        {
            return;
        }
        ListNode currNode=root;
        ListNode prevNode=null;
        while (currNode!=null)
        {
            if (currNode.key==key)
            {
                if (prevNode==null)
                {
                    root=currNode.next;  
                    
                }
                else
                {
                    prevNode.next=currNode.next;
                }
                calculateData();
                return;
            }
            else
            {
                prevNode=currNode;
                currNode=currNode.next;
            }
           // System.out.println("Hoh!");
        }
        
        //System.out.println("Hoh!");
        //Your code goes here

    }


    //Helper functions

    


}