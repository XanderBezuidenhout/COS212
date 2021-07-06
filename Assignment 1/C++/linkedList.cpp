#ifndef ASSIGNMENT_1_C___LINKEDLIST_CPP
#define ASSIGNMENT_1_C___LINKEDLIST_CPP

#include "linkedList.h"

template<class T>
ostream& operator<<(ostream & os, LinkedList<T> & LL)
{
    Node<T> * currNode;
    currNode=LL.head;
    if (LL.head==nullptr)
    {
        os<<"[]";
        return os;
    }
    os<<"[";
    while (currNode!=nullptr)
    {
        os<<currNode->element;
        if (currNode->next==nullptr)
        {
            os<<"]";
        }
        else
        {
            os<<",";
        }
        currNode=currNode->next;
    }
    return os;

}
template <class T>
ostream& LinkedList<T>::print(ostream &os)
{
    Node<T> * currNode;
    currNode=this->head;
    if (this->head==nullptr)
    {
        os<<"[]";
        return os;
    }
    os<<"[";
    while (currNode!=nullptr)
    {
        os<<currNode->element;
        if (currNode->next==nullptr)
        {
            os<<"]";
        }
        else
        {
            os<<",";
        }
        currNode=currNode->next;
    }
    return os;
}

template<class T>
LinkedList<T>::LinkedList()
{
    this->head=nullptr;
}

template<class T>
LinkedList<T>::LinkedList(const LinkedList<T> &other)
{
    this->operator=(other);
}

template<class T>
LinkedList<T>::~LinkedList()
{
    this->clear();
}

template<class T>
LinkedList<T>& LinkedList<T>::clone()
{
    LinkedList<T> * LL= new LinkedList<T>(*this);
    /*int Lsize=0;
    Node<T> * currNode;
    currNode=this->head;
    LL.head=this->head;
    while (currNode!=nullptr)
    {
        LL.insert(Lsize,currNode->element);
        currNode=currNode->next;
        Lsize++;
    }*/
    
    return *LL;
}


template<class T>
LinkedList<T>& LinkedList<T>::operator=(const LinkedList<T> &other)
{
    this->clear();
    if (other.head==nullptr)
    {
        this->head==nullptr;
    }
    else if (other.head->next==nullptr)
    {
        this->head=new Node<T>(other.head->element);
        this->head->next=nullptr;
    }
    else
    {
        Node<T> * otherNode;
        Node<T> * leftNode;
        otherNode=other.head;
        leftNode=new Node<T>(other.head->element);
        this->head=leftNode;
        otherNode=otherNode->next;
        while (otherNode!=nullptr)
        {
            leftNode->next=new Node<T>(otherNode->element);
            otherNode=otherNode->next;
            leftNode=leftNode->next;
        }
        leftNode->next=nullptr;
        
    }
    return (*this);
}

template<class T>
void LinkedList<T>::insert(int index, T element)
{
  
    Node<T> * currNode;
    Node<T> * prevNode;
    Node<T> * newNode;
    newNode=new Node<T>(element);
    currNode=this->head;
    if (this->head==nullptr)
    {
        
        this->head=newNode;
        newNode->next=nullptr;
        return;
    }   
    else if (index==0)
    {
        newNode->next=this->head;
        this->head=newNode;
        //cout<<"inserted into first index"<<endl;
        return;
    }
    else if (index>this->size())
    {
        return;
    }
    else
    {
        int counter=0;
        while (currNode!=nullptr)
        {
            if (counter==index)
            {
                prevNode->next=newNode;
                newNode->next=currNode;
                return;
            }
            else
            {
                prevNode=currNode;
                currNode=currNode->next;
                counter++;
            }
            
        }
        prevNode->next=newNode;
        newNode->next=nullptr;
        return;
    }
   // cout<<*this<<endl;

}

template<class T>
T LinkedList<T>::remove(int index)
{
    int size=this->size();
    T returnelement;
    if (this->head==nullptr||index>=size)
    {
        throw "empty structure";
    }
    Node<T> * currNode;
    Node<T> * prevNode;
    currNode=this->head;
    prevNode=nullptr;
    if (index==0)
    {
        returnelement=this->head->element;
        this->head=this->head->next;
    }
    else
    {
        for (int count=0;count<index;count++)
        {
            prevNode=currNode;
            currNode=currNode->next;
        }
        returnelement=currNode->element;
        prevNode->next=currNode->next;
    }
    return (returnelement);
    
}

template<class T>
bool LinkedList<T>::isEmpty()
{
    if (this->head==nullptr)
    {
        //cout<<"Whoah looks empty"<<endl;
    }
    return (this->head==nullptr);
}

template<class T>
void LinkedList<T>::clear()
{
    while (this->head!=nullptr)
    {
        this->remove(0);
    }
}

template<class T>
Node<T>* LinkedList<T>::getLeader()
{
    return (this->head);
}

template<class T>
int LinkedList<T>::size()
{
    if (this->head==nullptr)
    {
        return 0;
    }
    Node<T> * countNode;
    countNode=this->head;
    int count=0;
    while (countNode!=nullptr)
    {
        count++;
        countNode=countNode->next;
    }
    return count;
}


#endif