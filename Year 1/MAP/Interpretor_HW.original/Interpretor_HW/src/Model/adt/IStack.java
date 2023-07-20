package Model.adt;

import Model.Exceptions.EmptyStackException;

import java.util.Stack;

public interface IStack <T>{
    T pop() throws EmptyStackException;
    T top() throws EmptyStackException;
    void push(T v);
    boolean isEmpty();
    String toString();
    Stack<T> getStack();
}
