package Model.adt;

import Model.Exceptions.InvalidIndexException;
import Model.Exceptions.ListException;

import java.util.ArrayList;

public interface IList <T>{
    T get(int index) throws ListException, InvalidIndexException;
    void set(int index,T element)throws ListException, InvalidIndexException;
    void add(T v);
    T remove(int index) throws ListException, InvalidIndexException;
    String toString();
    boolean isEmpty();
    void clear();
    int size();

    ArrayList<T> getList();
}
