package Model.adt;

import Model.Exceptions.EmptyStackException;

import java.util.Stack;

public class ADTStack<T> implements IStack<T> {
    java.util.Stack<T> stack;

    public ADTStack() {
        stack = new java.util.Stack<T>();
    }

    @Override
    public T pop() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException("Empty stack!");
        return stack.pop();
    }

    @Override
    public T top() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException("Empty stack!");
        return stack.peek();
    }

    @Override
    public void push(T element) {
        stack.push(element);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        java.util.Stack<T> stackStr = new java.util.Stack<>();
        stackStr.addAll(stack);
        while (stackStr.size() > 1) {
            str.append((stackStr.pop()).toString());
            str.append("\n");
        }
        if (stackStr.size() > 0) {
            str.append(stackStr.pop());
            str.append("\n");
        }
        return str.toString();
    }
    public Stack<T> getStack(){
        return stack;
    }
}
