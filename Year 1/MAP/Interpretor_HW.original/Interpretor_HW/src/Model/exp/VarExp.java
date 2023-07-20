package Model.exp;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.MyException;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.types.IType;
import Model.value.IValue;

public class VarExp implements Exp{
    String id;
    public VarExp(String id){this.id= id;}

    public IType typecheck(IDict<String,IType> typeEnv) throws MyException, DictionaryException {
        return typeEnv.lookup(id);
    }

    public IValue eval(IDict<String, IValue> symTable, Heap heap) throws ExpressionException{
        try{
            return  symTable.lookup(id);
        }catch(DictionaryException exception){

            throw new ExpressionException(exception.getMessage());
        }
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    @Override
    public String toString() { return id;}
}
