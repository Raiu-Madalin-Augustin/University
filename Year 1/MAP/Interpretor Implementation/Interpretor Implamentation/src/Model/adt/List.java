package Model.adt;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class List<T> implements IList<T> {
    Stack<T> list;

    @Override
    public void add(T v) { }

    @Override
    public T pop() {return list.pop();}

    public T getFirstElement() {return this.list.peek();}

    @Override
    public boolean empty() {
        return this.list.isEmpty();
    }

    @Override
    public void clear(){
        this.list.clear();
    }

}
