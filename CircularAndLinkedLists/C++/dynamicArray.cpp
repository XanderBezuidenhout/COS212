#ifndef ASSIGNMENT_1_C___DYNAMICARRAY_CPP
#define ASSIGNMENT_1_C___DYNAMICARRAY_CPP
//Provide the implementation for the DynamicArray class in this file.
#include "dynamicArray.h"

template<class T>
ostream& operator<<(ostream& os,DynamicArray<T>& thisArray)
{
        
        return thisArray.print(os);
    
}

template<class T>
ostream& DynamicArray<T>::print(ostream& os)
{
    if (this->size==0)
    {
        os<<"[]";
        return os;
    }
    else
    {
        int count=0;
        os<<"[";
        while (count<this->size)
        {
            if (this->elements[count]==nullptr)
            {
                os<<"*";
            }
            else
            {
                os<<*elements[count];
            } 
            if (count+1<this->size)
            {
                os<<",";
            }
            count++;
            
        }
        os<<"]";
    }
    return os;
}

template<class T>
DynamicArray<T>::DynamicArray(int s)
{
    this->size=s;
    this->numElements=0;
    this->elements=new T*[s];
    for (int I = 0; I < s; I++)
    {
        elements[I] = nullptr; 
    }
}

template<class T>
DynamicArray<T>::DynamicArray(const DynamicArray<T>& other)
{
    this->operator=(other);
}

template<class T>
DynamicArray<T>& DynamicArray<T>::clone()
{
    DynamicArray<T> * newList=new DynamicArray<T>(*this);
    return *newList;
}

template<class T>
DynamicArray<T>& DynamicArray<T>::operator=(const DynamicArray<T>& other)
{
    this->clear();
    this->elements=new T*[other.size];
    this->size=other.size;
    this->numElements=other.numElements;
    for (int I = 0; I < other.size; I++)
    {
        if (other.elements[I]!=nullptr)
        {
            elements[I] = new T(*other.elements[I]); 
        }
        else
        {
            elements[I]=nullptr;
        }
        
    }
    return *this;
    
}

template<class T>
DynamicArray<T>::~DynamicArray()
{
    
}

template<class T>
void DynamicArray<T>::insert(int index, T element)
{
    /*if (index>=size)
    {
        this->resize(index+1);
    }
    if (elements[index]==nullptr)
    {
        elements[index]=new T(element);
    }
    else
    {
        this->resize(size+1);
        for (int count=size-1;count>=index;count--)
        {
           /* if (count!=index)
            {
                elements[count]=elements[count-1];   //allocating pointer to a pointer could pose problems
                elements[count-1]=nullptr;
            }
            else
            {
                elements[count]=nullptr; //when index is reached
            }*//*
            elements[count]=nullptr;
            if (count!=index)
            {    
                if (elements[count-1]!=nullptr)
                {
                    elements[count]=new T(*elements[count-1]);
                }
            }
        }
        elements[index]=new T(element);
    }
    this->numElements++;*/
    //cout<<*this<<endl;
    if (index >= size)
    {
        resize(index+1);
        elements[index] = new T(element);
        numElements++;
        return;
    }

    if (elements[index] == NULL)
    {
        elements[index] = new T(element);
        numElements++;
        return;
    }

    if (elements[index] != NULL)
    {
        if (elements[size -1] != NULL)
        {
            resize(size+1);
        }
        
        for (int I = size - 1; I >= index;I--)
        {
            elements[I] = elements[I-1];
        }  

        elements[index] = new T(element);
        numElements++;
        return;



    }
}

template<class T>
T DynamicArray<T>::remove(int index)
{
    if (elements[index]==nullptr)
    {
        throw RemoveException("Index is empty already");
    }
    T value=*elements[index];
    this->numElements--;
    for (int count=index;count<this->size;count++)
    {
        if (count<size-1)
        {
            elements[count]=elements[count+1];
        }
        else
        {
            elements[count]=nullptr;
        }
    }
    //this->resize(size-1);
    return value;
}

template<class T>
bool DynamicArray<T>::isEmpty()
{
    return (numElements==0);
}

template<class T>
void DynamicArray<T>::clear()
{
    for (int count = 0; count < size; count++)
    {
        if (elements[count] != nullptr)
        {
            delete elements[count];
            elements[count] = nullptr;
        }
    }  
    this->numElements=0;
}

template <class T>
void DynamicArray<T>::resize(int howMuch)
{
    T** newElements=new T*[howMuch];
    for (int count=0;count<howMuch;count++)
    {
        newElements[count]=nullptr;
    }
    for (int count=0;count<howMuch;count++)
    {
        if (count<this->size&&elements[count]!=nullptr)
        {
            newElements[count]=new T(*elements[count]);
        }
    }
    elements =newElements;
    this->size=howMuch;
}


#endif