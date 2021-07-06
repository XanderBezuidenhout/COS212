//Provide the implementation for the CircularList class in this file.
#ifndef ASSIGNMENT_1_C___CIRCULARLIST_CPP
#define ASSIGNMENT_1_C___CIRCULARLIST_CPP
#include "circularList.h"
#include <string>
#include <sstream>
using namespace std;

template<class T>
ostream &operator<<(ostream & os, CircularList<T> & list)
{
    /*if (list.tail==nullptr)
    {
        os<<'['<<']';
        return;
    }
    else if (list.size()==1)
    {
        os<<'['<<list.tail->element<<']';
        return;
    }
    else
    {
        string output="";
        string temp;
        stringstream ss;
        Node <T> * currNode;
        currNode=list.tail->next;
        do
        {
            temp.clear();
            ss.str(temp);
            ss<<currNode->element;
            output.append(temp);
            if (currNode!=list.tail)
            {
                output.append(",");
            }
            currNode=currNode->next;
        }while (currNode!=list.tail->next);

        os<<'['<<output<<']';
        return;*/
        os << "[";
    if (list.tail)
    {
        Node<T> *Node = list.tail->next;
        do
        {
            os << Node->element;
            if (Node->next!=list.tail->next)
            {
                os <<",";
            }
            Node = Node->next;
        }while (Node!=list.tail->next);
    }
    os <<"]";
    return os;
    
}

template<class T>
CircularList<T>::CircularList()
{
    this->tail=nullptr;
}

template<class T>
CircularList<T>::CircularList(const CircularList<T> &other)
{
    int othersize=0;
    Node<T> * currNode;
    if (other.tail==nullptr)
    {
        othersize= 0;
    }
    else if (other.tail->next==nullptr)
    {
        othersize= 1;
    }
    else
    {
        currNode=other.tail->next;
        do
        {
            othersize++;
            currNode=currNode->next;
        } while (currNode!=other.tail->next);

    }


    if (othersize==0)
    {
        this->tail=nullptr;
        return;
    }
    else if (othersize==1)
    {
        this->tail=new Node<T>(other.tail->element);
        this->tail->next=this->tail;
        return;
    }
    else
    {
        this->tail=new Node<T>(other.tail->element);
        Node<T> * currNode;
        Node<T> * otherNode;
        currNode=this->tail;
        otherNode=other.tail->next;
        do 
        {
            currNode=currNode->next;
            currNode=new Node<T>(otherNode->element);
            otherNode=otherNode->next;
        }
        while (otherNode!=nullptr&&otherNode!=other.tail->next);
        this->tail=currNode;
        this->tail->next=currNode->next;
    }
    
}

template<class T>
CircularList<T>& CircularList<T>::operator=(const CircularList<T> &other)
{
    this->clear();
    if (other.size()==0)
    {
        this->tail==nullptr;
    }
    else if (other.size()==1)
    {
        this->tail=new Node<T>(other.tail->element);
        this->tail->next=this->tail;
    }
    else
    {
        this->tail=new Node<T>(other.tail->element);
        Node<T> * currNode;
        Node<T> * otherNode;
        currNode=this->tail;
        otherNode=other.tail->next;
        do 
        {
            currNode=currNode->next;
            currNode=new Node<T>(otherNode->element);
            otherNode=otherNode->next;
        }
        while (otherNode!=nullptr&&otherNode!=other.tail->next);
        this->tail=currNode;
        this->tail->next=currNode->next;
    }
}

template<class T>
CircularList<T>& CircularList<T>::clone()
{
    CircularList<T> * list= new CircularList<T>(*this);
    /*
    if (this->size()==0)
    {
        list.tail==nullptr;
    }
    else if (this->size()==1)
    {
        list.tail=new Node<T>(this->tail->element);
        list.tail->next=list.tail;
    }
    else
    {
        list.tail=new Node<T>(this->tail->element);
        Node<T> * currNode;
        Node<T> * otherNode;
        currNode=list.tail;
        otherNode=this->tail->next;
        do 
        {
            currNode=currNode->next;
            currNode=new Node<T>(otherNode->element);
            otherNode=otherNode->next;
        }
        while (otherNode!=nullptr&&otherNode!=this->tail->next);
        list.tail=currNode;
        list.tail->next=currNode->next;
    }*/
    return *list;
}
template<class T>
CircularList<T>::~CircularList()
{
    
}

template<class T>
void CircularList<T>::insert(int index, T element)
{
    Node<T>* newNode;
    Node<T>* currNode;
    int count=0;
    newNode= new Node<T>(element);
    if (this->size()==0)
    {
        this->tail=newNode;
        this->tail->next=this->tail;
    }
    else if (index==0)
    {
        newNode->next=this->tail->next;
        this->tail->next=newNode;
    }
    else if (index=this->size())
    {
        newNode->next=this->tail->next;
        this->tail->next=newNode;
        this->tail=newNode;
    }
    else if (index>this->size())
    {
        return;
    }
    else
    {
        currNode=this->tail->next;
        
        while (count<this->size())
        {
            if (count==index-1)
            {
                newNode->next=currNode->next;
                currNode->next=newNode;
            }
            currNode=currNode->next;
            count++;
        }
        
    }
    /*currNode=this->tail->next;
    do
    {
        if (currNode==nullptr)
        {
            cout<<"Caught nullptr"<<endl;
        }
        else
        {
            cout<<currNode->element<<endl;
            currNode=currNode->next;
        }
        if (currNode==this->tail->next)
        {
            count++;
        }
    }while (count!=2);
    cout<<"exited loop"<<endl;*/
}

template<class T>
T CircularList<T>::remove(int index)
{
    T returnelement=NULL;
    int count=0;
    if (this->tail==nullptr) //empty list
    {
        returnelement=NULL;
    }
    else if (index==0)//first element
    {
        
        if (this->size()==1)
        {
            returnelement=this->tail->element;
            this->tail=nullptr;
        }
        else if (this->size()==2)
        {
           returnelement=this->tail->next->element; 
           this->tail->next=this->tail;
        }
        else
        {
           returnelement=this->tail->next->element; 
           this->tail->next=this->tail->next->next; 
        }
        
    }
    else if (this->size()==index+1) //last element
    {
        Node<T> * currNode=this->tail->next;
        while (currNode->next!=this->tail)
        {
            currNode=currNode->next;
        }
        returnelement=this->tail->element;
        currNode->next=this->tail->next;
        this->tail=currNode;
    }
    else
    {
        Node<T> * currNode=this->tail->next;
        Node<T> * delNode=nullptr;
        int count=0;
        while (currNode!=this->tail)
        {
            if (count==index)
            {
                returnelement=currNode->element;
                if (delNode!=nullptr)
                {
                    delNode->next=currNode->next;
                }
                break;
            }
            delNode=currNode;
            currNode=currNode->next;
            count++;
        }
        /*while (count<this->size())
        {
            if (count==index-1)
            { 
                returnelement=currNode->next->element;
                currNode->next=currNode->next->next;
            }
            currNode=currNode->next;
            count++;
        }*/
        
    }
    return returnelement;
}

template<class T>
bool CircularList<T>::isEmpty()
{
    return (this->tail==nullptr);
}

template<class T>
void CircularList<T>::clear()
{
    while (!(this->isEmpty()))
    {
        this->remove(0);
    }
}

template<class T>
Node<T>* CircularList<T>::getLeader()
{
    return this->tail;
}

template<class T>
ostream& CircularList<T>::print(ostream &os)
{   os<<"[";
    if (this->tail)
    {
        Node<T> *Node = this->tail->next;
        do
        {
            os << Node->element;
            if (Node->next!=this->tail->next)
            {
                os <<",";
            }
            Node = Node->next;
        }while (Node!=this->tail->next);
    }
    os <<"]";
    return os;
}

template<class T>
int CircularList<T>::size()
{
    int size=0;
    Node<T> * currNode;
    if (this->tail==nullptr)
    {
        return 0;
    }
    else if (this->tail->next==nullptr)
    {
        return 1;
    }
    else
    {
        currNode=this->tail->next;
        do
        {
            size++;
            currNode=currNode->next;
        } while (currNode!=this->tail->next);
        return size;

    }
}

#endif