#ifndef ASSIGNMENT_1_C___DYNAMICARRAY_H
#define ASSIGNMENT_1_C___DYNAMICARRAY_H


#include "linearStructure.h"

template<class T>
class DynamicArray;

template<class T>
ostream& operator<<(ostream&,DynamicArray<T>&);

template<class T>
class DynamicArray : public LinearStructure<T>
{
public:
    /*The overloaded stream operator for the DynamicArray class.  If
    a the array object is printed and contains the elements a,c,b and m,
    with element 'a' at index 0 and element 'm' at index 3 (first to last),
    the
    output MUST be in the following format:
    [a,c,b,m]
    with no additional white space.

    It is possible that some of the elements might be null.  If this is the
    case then the null elements should be indicated with asterisks.
    If the array contains the elements a and m,
    with element 'a' at index 0 and element 'm' at index 3 (first to last),
    the
    output MUST be in the following format:
    [a,*,*,m]
    */
    friend ostream& operator<< <T>(ostream&,DynamicArray<T>&);

    /*
    The constructor accepts the initial size of the array.
    All elements in the array are initialized to null.
    */
    DynamicArray(int s);

    /*
    The copy constructor.
    */
    DynamicArray(const DynamicArray<T>& other);

    //Returns a clone of this object.
    virtual DynamicArray<T>& clone();

    /*
    The overloaded assignment operator.
    */
    DynamicArray<T>& operator=(const DynamicArray<T>& other);

    /*
    The destructor.
    */
    virtual ~DynamicArray();

    /*
    Inserts an element at the given index in the array.  If
    the index is larger than the size of the array, grow
    the array to accomodate the index.
    */
    virtual void insert(int index, T element);

    /*
    Removes and returns the element at the index passed in
    as a parameter.  All elements from the removed index onwards
    are shifted one position forward.  If an element is null,
    throw an object of RemoveException with an appropriate message.
    */
    virtual T remove(int index);

    /*
    Returns true if the array contains no elements and
    false otherwise.
    */
    virtual bool isEmpty();

    /*
    Removes all of the elements from the array.  After this function has
    been called on a DynamicArray object, the the array must be empty (i.e. all elements in the array must be null).  The array's current
    size remains unchanged.
    */
    virtual void clear();

protected:
    ostream& print(ostream& os);


private:
    /*
    Use this function to resize the array.
    */
    void resize(int howMuch);

    /*
    An array of pointers to objects of type T.
    The elements should be stored in this array.
    */
    T** elements;

    /*
    The current size of the array.  It should be initialized
    appropriately in the constructor.
    */
    int size;

    /*
    The number of elements currently contained in the array.
    */
    int numElements;
};


#endif //ASSIGNMENT_1_C___DYNAMICARRAY_H
