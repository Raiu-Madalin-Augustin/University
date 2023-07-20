package Model.stmt;

import Model.Exceptions.*;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class HeapWriteStmt implements  IStmt{
    String var_name;
    Exp expression;

    public HeapWriteStmt(String name, Exp e)
    {
        var_name=name;
        expression=e;
    }

    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException, DictionaryException, MyException {
        IType typevar = typeEnv.lookup(var_name);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("WRITE stmt: right hand side and left hand side have different types ");
    }

    @Override
    public PrgState execute(PrgState state ) throws MyException, DictionaryException, UndeclaredVariableException, TypeException, DivByZeroException, ExpressionException {
        IDict<String, IValue>symTable=state.getSymTable();
        Heap heap=state.getHeap();
        if(!(symTable.isDefined(var_name))){
            throw new UndeclaredVariableException("variable not defined");
        }
        IValue value=symTable.lookup(var_name);
        if(!(value.getType() instanceof RefType)){
            throw new TypeException("variable is not type ref");
        }
        RefValue ref_value=(RefValue) value;
        int address=ref_value.getAddress();
        if(!(heap.isDefined(address))){
            throw new UndeclaredVariableException("the adress is not defined in the heap");
        }
        IValue exp_value=expression.eval(symTable,heap);
        IType type=ref_value.getType();
        RefType refType =(RefType) type;
        if(!(refType.getInner().equals(exp_value.getType()))){
            throw new TypeException("the variable and expression evaluation have diff types");
        }
        heap.update(address,exp_value);
        return null;
    }
    @Override
    public String toString() { return "wH(" +var_name+", "+ expression.toString()+")";}

    @Override
    public IStmt deepCopy(){return null;}

}
