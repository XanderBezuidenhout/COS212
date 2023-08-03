/**
 * Name: Xander Bezuidenhout
 * Student Number: 20425997
 */

public class TransposeOrganisingList extends OrganisingList {

    ////// Implement the function below this line //////

    /**
     * Retrieve the node with the specified key and swap the
     * accessed node with its predecessor, then recalculate all data fields.
     * @return The node with its data value before it has been moved,
     * otherwise 'null' if the key does not exist.
     */
    @Override
    public ListNode searchNode(Integer key) 
    {
        if (!contains(key))
        {
            return null;
        }
        ListNode currNode=root;
        ListNode prevNode=null;
        ListNode returnNode=null;
        ListNode tempNode=null;
        while (currNode.next!=null)
        {
            if (currNode.next.key==key)
            {
                returnNode=new ListNode(currNode.next.key);
                returnNode.data=currNode.next.data;
                if (root!=currNode)
                {
                    tempNode=currNode.next;
                    currNode.next=currNode.next.next;
                    prevNode.next=tempNode;
                    tempNode.next=currNode;
                    //calculateData was here
                }
                else
                {
                    root.next=currNode.next.next;
                    root=currNode.next;
                }
                calculateData();
                return returnNode;
            }
            else
            {
                prevNode=currNode;
                currNode=currNode.next;
            }
        }
        calculateData();
        return null;
	//Your code goes here

    }


}