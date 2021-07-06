public class LinkedListNode<T>
{
    public T data;
    public LinkedListNode<T> next;
    public LinkedListNode()
    {
        
    }
   
    public LinkedListNode(T newData)
    {
        data=newData;
       
    }

/**
 * 
 * @return Object in data 
 */
    public T giveObject()
    {
        return (T) data;
    }

    
    
}
