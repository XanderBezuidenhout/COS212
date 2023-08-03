#ifndef ASSIGNMENT_1_C___LINEARSTRUCTURE_H
#define ASSIGNMENT_1_C___LINEARSTRUCTURE_H

#include <iostream>
#include "RemoveException.h"

using namespace std;

template<class T>
class LinearStructure;

template<class T>
ostream &operator<<(ostream &, LinearStructure<T> &);

template<class T>
class LinearStructure {
public:
    /*
    Returns a string with the contents of the getString function placed
    between square brackets.
    */
    friend ostream &operator<<<T>(ostream &os, LinearStructure<T> &l);

    virtual ~LinearStructure() {};

    virtual LinearStructure<T> &clone() = 0;

    /*
    Inserts an element at the given index.
    See subclasses for more details.
    */
    virtual void insert(int index, T element) = 0;

    /*
    Removes and returns an element from the index passed
    as a parameter.  See subclasses for more details.
    */
    virtual T remove(int index) = 0;

    /*
    Returns true if the list is empty, and false
    otherwise.
    */
    virtual bool isEmpty() = 0;

    /*
    Empties out the structure.  See subclasses for more details.
    */
    virtual void clear() = 0;

protected:

    /*
    Stream operators are not members of a class and are
    therefore not inherited and cannot be overridden as
    subclass members.  See the implementation of this
    class' stream operator below.  The print function
    in each subclass should be implemented as you would
    an overloaded stream operator.
    */
    virtual ostream &print(ostream &os) = 0;

};

template<class T>
ostream &operator<<(ostream &os, LinearStructure<T> &l) {
    l.print(os);
    return os;
}

#endif //ASSIGNMENT_1_C___LINEARSTRUCTURE_H
