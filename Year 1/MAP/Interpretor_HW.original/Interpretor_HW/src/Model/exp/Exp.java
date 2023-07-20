package Model.exp;

import Model.Exceptions.*;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.types.IType;
import Model.value.IValue;

public interface Exp {
    IValue eval(IDict<String, IValue> symtable, Heap heap) throws ExpressionException, DivByZeroException, TypeException, DictionaryException, UndeclaredVariableException, MyException;
    IType typecheck(IDict<String,IType> typeEnv) throws MyException, DictionaryException;
    Exp deepCopy();

}
