
public class LinkedList<T>
{
    LinkedListNode<T> head=null;
    public int size=0;
    public LinkedList()
    {

    }


    public void add(T data)
    {
        LinkedListNode<T> newNode=new LinkedListNode<T>(data);
        LinkedListNode<T> currNode=head;
        if (this.head==null)
        {
            head=newNode;
        }
        else
        {
            while (currNode.next!=null)
            {
                currNode=currNode.next;
            }
            currNode.next=newNode;
        }
        size++;
    }

    public T poll()
    {
        T returnval=null;
        if (head==null)
        {
            return null;
        }
        returnval=head.data;
        head=head.next;
        size--;
        return returnval;
    }


    /**
     * Removes node at index 
     * @param index 
     */

/**
 * fetches the object from linked list
 * @param index to fetch data object
 * @return object
 */
    public T get(int index)
    {
        if (index<0||index>=size)
        {
            return null;
        }
        LinkedListNode<T> currNode=head;

        int count=0;
        while (count!=index)
        {
            currNode=currNode.next;
            count++;
        }
        return currNode.giveObject();
    }

}
