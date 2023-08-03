/**
 * Name: Xander Bezuidenhout
 * Student Number: 20425997
 */

public class MoveToFrontOrganisingList extends OrganisingList {

    ////// Implement the function below this line //////

    /**
     * Retrieve the node with the specified key and move the accessed node
     * to the front, then recalculate all data fields.
     * @return The node with its data value before it has been moved to the front,
     * otherwise 'null' if the key does not exist.
     */
    @Override
    public ListNode searchNode(Integer key) 
    {
        if (!contains(key))
        {
            return null;
        }
       // System.out.println("No infinite yet");
        ListNode currNode=root;
        //ListNode prevNode=null;
        ListNode returnNode=null;
        while (currNode!=null)
        {
            if (currNode.key==key)
            {
                returnNode=new ListNode(currNode.key);
                returnNode.data=currNode.data;
                if (root!=currNode)
                {
                    delete(key);
                    currNode.next=root;
                    root=currNode;
                    calculateData();
                }
                return returnNode;
            }
            else
            {
                currNode=currNode.next;
            }
            //System.out.println("Hoh?");
        }
        //System.out.println("Can't find node");
        return null;
        //Your code goes here

    }


}