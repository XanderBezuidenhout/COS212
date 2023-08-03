#ifndef ASSIGNMENT_1_C___LINKEDLIST_H
#define ASSIGNMENT_1_C___LINKEDLIST_H


#include "linearStructure.h"
#include "node.h"

template<class T>
class LinkedList;

template<class T>
ostream &operator<<(ostream &, LinkedList<T> &);

template<class T>
class LinkedList : public LinearStructure<T> {
public:
    /*The overloaded stream operator for the List class.  If
    a List object is printed and contains the elements a,c,b and m, with
    element 'a' at index 0 and element 'm' at index 3 (first to last), the
    output MUST be in the following format:
    [a,c,b,m] with no additional white space.  You will have to replace
    this operator with a suitable equivalent in your Java implementation.
    */
    friend ostream &operator<<<T>(ostream &, LinkedList<T> &);

    /*
    The constructor initializes an empty list.
    */
    LinkedList();

    //Returns a clone of this object.
    virtual LinkedList<T> &clone();

    /*
    The copy constructor.
    */
    LinkedList(const LinkedList<T> &other);

    /*
    The overloaded assignment operator.  You will have to replace this
    operator with an appropriate equivalent in your Java code
    */
    LinkedList<T> &operator=(const LinkedList<T> &other);

    /*
    The destructor.
    */
    virtual ~LinkedList();

    /*
    Inserts an element at the given index.  The following holds
    for this function:

    1.) It is valid to insert at index 0 of an empty list.

    2.) It is valid to insert at the index returned by size().  Simply
        append the element to the back of the list.

    3.) Only indices between 0 and size() are valid.
    */
    virtual void insert(int index, T element);

    /*
    Removes and returns the element at the index passed in
    as a parameter.   If an invalid delete is attempted
    throw the string "empty structure".
    */
    virtual T remove(int index);

    /*
    Returns true if the list is empty, and false
    otherwise.
    */
    virtual bool isEmpty();

    /*
    Removes all of the nodes from the list.  After this function has
    been called on a LinkedList object, the list must be empty.
    */
    virtual void clear();

    /*
    Returns the head, not the element at the head.
    */
    Node<T> *getLeader();

protected:
    ostream &print(ostream &os);

private:
    //Returns the number of nodes in the list.
    int size();

    //The first node in the list
    Node<T> *head;
};


#endif //ASSIGNMENT_1_C___LINKEDLIST_H
