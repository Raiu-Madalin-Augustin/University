package Model.adt;

import Model.Exceptions.InvalidIndexException;
import Model.Exceptions.ListException;

import java.util.ArrayList;
import java.util.List;
public class ADTList <T> implements  IList<T>{
    private ArrayList<T> myList;

    public ADTList(){ myList= new ArrayList<T>();}
    @Override
    public void add(T v){myList.add(v);}

    @Override
    public T get(int index) throws ListException, InvalidIndexException {
        if(index < 0 || index >= myList.size())
            throw new InvalidIndexException("Index out of bounds!");
        try{
            return myList.get(index);
        }catch (Exception exception){
            throw new ListException(exception.getMessage());
        }
    }
    @Override
    public void set(int index, T element) throws  ListException,InvalidIndexException{
        if(index < 0 || index >= myList.size())
            throw new InvalidIndexException("Index out of bounds");
        try{
            myList.set(index, element);

        }catch (Exception exception){
            throw new ListException(exception.getMessage());
        }
    }

    @Override
    public boolean isEmpty() { return myList.isEmpty();}

    @Override
    public void clear() { this.myList.clear();}

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int index = 0; index < myList.size() - 1; index++) {
            str.append((myList.get(index)).toString());
            str.append("\n");
        }
        if (!myList.isEmpty()) {
            str.append(myList.get(myList.size() - 1));
            str.append("\n");
        }
        return str.toString();
    }

    @Override
    public T remove(int index)throws ListException, InvalidIndexException {
        if (index < 0 || index >=myList.size())
            throw new InvalidIndexException("Index out of bounds");
        try {
            return myList.remove(index);

        } catch (Exception exception) {
            throw new ListException(exception.getMessage());
        }
    }
    @Override
    public int size() { return myList.size();};
    @Override
    public ArrayList<T> getList(){
        return myList;
    }
}






