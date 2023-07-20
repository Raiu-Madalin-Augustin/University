package Model.adt;

import Model.Exceptions.DictionaryException;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public interface IDict <T1,T2>{
    void add(T1 v1, T2 v2) throws DictionaryException;
    T2 lookup(T1 id)  throws DictionaryException;
    String toString();
    String toString2();

    T2 get(T1 key)  throws DictionaryException;
    T2 replace(T1 key, T2 v)  throws DictionaryException;
    void setContent(HashMap<T1,T2> other);
    Map<T1,T2> getContent();
    boolean isDefined(T1 strValue);
    void update(T1 v1, T2 v2);
    void remove(T1 strValue);

    IDict<T1,T2> deepCopy();
    HashMap<T1,T2> getDict();
}
