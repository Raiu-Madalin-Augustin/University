package Model.adt;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dict<T1,T2> implements IDict<T1,T2> {
    Map<T1, T2> dictionary;

    public Dict() {
        dictionary = new HashMap<T1,T2>();
    }


    @Override
    public void add(T1 v1, T2 v2) {

    }

    @Override
    public void update(T1 v1, T2 v2) {

    }

    @Override
    public T2 lookup(T1 id) {
        return null;
    }

    @Override
    public boolean isDefined(String id) {
        return false;
    }
}
