package Model.exp;

import Model.Exceptions.*;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class NotExp implements Exp{
    private final Exp exp;

    public NotExp(Exp exp1){exp =exp1;}

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap heap) throws MyException, DivByZeroException, TypeException, UndeclaredVariableException, ExpressionException, DictionaryException {
        BoolValue boolValue=(BoolValue) exp.eval(symTable,heap);
        if(!boolValue.getValue()){
            return new BoolValue(true);

        }
        else
        {
            return new BoolValue(false);
        }

    }
    @Override
    public IType typecheck(IDict<String,IType> typeEnv) throws MyException, DictionaryException {
        return exp.typecheck(typeEnv);
    }
    @Override
    public String toString(){return "!("+ exp.toString()+ ")";}
    @Override
    public Exp deepCopy(){return new NotExp(exp.deepCopy());}
}
