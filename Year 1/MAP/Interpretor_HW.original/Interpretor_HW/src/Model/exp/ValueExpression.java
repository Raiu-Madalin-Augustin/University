package Model.exp;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.MyException;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.types.IType;
import Model.value.IValue;

public class ValueExpression implements Exp{
    private IValue number;
    public ValueExpression(IValue value)
    {
        this.number=value;
    }

    public IType typecheck(IDict<String,IType> typeEnv) throws MyException{
        return number.getType();
    }


    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap heap)throws ExpressionException
    {return number;}

    @Override
    public Exp deepCopy() {
        return new ValueExpression(number.deepCopy());
    }

    @Override
    public String toString() {return number.toString();}
}
