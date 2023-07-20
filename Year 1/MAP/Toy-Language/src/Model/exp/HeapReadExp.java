package Model.exp;

import Model.Exceptions.*;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class HeapReadExp implements Exp{
    Exp expression;
    public HeapReadExp(Exp exp){expression=exp;}

    @Override
    public IValue eval(IDict<String,IValue>symTable, Heap heap) throws MyException, TypeException, DictionaryException, UndeclaredVariableException, DivByZeroException, ExpressionException {
        IValue value=expression.eval(symTable,heap);
        if(!(value.getType() instanceof RefType)){
            throw new TypeException("the value should be of type Ref");
        }
        RefValue ref_value=(RefValue) value;
        int adress= ref_value.getAddress();
        if(!(heap.isDefined(adress))){
            throw new UndeclaredVariableException("variable not declared");

        }
        return heap.lookup(adress);
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws MyException, DictionaryException {
        IType typ=expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString(){
        return "rH("+expression.toString()+")";
    }
    @Override
    public Exp deepCopy() {return null;}

}
