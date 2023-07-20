package Model.adt;
import Model.Exceptions.DictionaryException;

import java.util.*;

public class Dict <T1, T2 > implements  IDict<T1,T2>{

    protected HashMap<T1, T2> dictionary;

    public Dict(){ dictionary =new HashMap<T1,T2>();}

    @Override
    public void update(T1 v1,T2 v2){
        dictionary.replace(v1,v2);
    }
    @Override
    public void add (T1 v1, T2 v2) throws DictionaryException{
        if(get(v1)!=null)
            throw new DictionaryException("The key is mapped already");
        try{

            dictionary.put(v1,v2);
        }catch (Exception exception)
        {
            throw  new DictionaryException(exception.getMessage());
        }

    }

    @Override
    public T2 get(T1 key) throws DictionaryException {
        try{
            return dictionary.get(key);
        }catch(Exception exception){
            throw new DictionaryException(exception.getMessage());
        }
    }
    @Override
    public T2 lookup(T1 id) throws  DictionaryException{
        try {
            if (!dictionary.containsKey(id))
                return null;
            return dictionary.get(id);
        } catch (Exception exception) {
            throw new DictionaryException(exception.getMessage());
        }
    }




    @Override
    public T2 replace(T1 key, T2 v) throws DictionaryException{
        if(get(key)==null)
            throw new DictionaryException("The key was not found");
        try{
            return dictionary.replace(key,v);
        }catch (Exception exception){
            throw new DictionaryException(exception.getMessage());
        }
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (T1 key   : dictionary.keySet()) {
            T2 elem = dictionary.get(key);
            str.append(key.toString()).append(" --> ").append(elem.toString());
            str.append("\n");
        }
        return str.toString();
    }
    @Override
    public String toString2(){
      StringBuilder str = new StringBuilder();
        for(T1 key: dictionary.keySet()) {
            str.append(key.toString()).append("\n");
        }
        return str.toString();
    }

    @Override
    public boolean isDefined(T1 id) {

        return dictionary.containsKey(id);}

    @Override
    public void remove(T1 id){dictionary.remove(id);}
    @Override
    public Map<T1, T2> getContent() { return dictionary;}
    @Override
    public void setContent(HashMap<T1,T2> other){dictionary =  other;}
    @Override
    public IDict<T1,T2> deepCopy(){
        IDict<T1,T2> new_dict=new Dict<T1,T2>();
        for(Map.Entry<T1,T2> entry:dictionary.entrySet()){
            try {
                new_dict.add(entry.getKey(),entry.getValue());

            } catch (DictionaryException exception) {
                new_dict=null;
            }

        }
        return new_dict;
    }
    @Override
    public HashMap<T1,T2> getDict(){
        return dictionary;
    }
}