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

public class HeapAllocateStmt implements IStmt{
    String var_name;
    Exp expression;

    public HeapAllocateStmt(String name,Exp exp){
        var_name=name;
        expression=exp;
    }

    @Override
    public IDict<String,IType> typecheck(IDict<String,IType> typeEnv) throws MyException, DictionaryException {
        IType typevar=(IType) typeEnv.lookup(var_name);
        IType typexp=expression.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("new stmt: right hand side and left hand side have different types");
    }



    @Override
    public PrgState execute(PrgState state) throws MyException, DictionaryException, TypeException, UndeclaredVariableException, DivByZeroException, ExpressionException {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeap();
        if(!symTable.isDefined(var_name)) {
            throw new UndeclaredVariableException("ERROR: The variable is not declared!\n");
        }
        IValue value = symTable.lookup(var_name);
        if(!(value.getType() instanceof RefType)) {
            throw new TypeException("ERROR: The variable does not have the type ref!\n");
        }
        RefValue ref_value = (RefValue) value;
        IType type = ref_value.getType();
        RefType ref_type = (RefType) type;
        IValue exp_val = expression.eval(symTable, heap);
        if(!(ref_type.getInner().equals(exp_val.getType()))) {
            throw new TypeException("ERROR: The variable and expression evaluation have different types!\n");
        }
        int allocated_address = heap.getFree();
        heap.add(allocated_address, exp_val.deepCopy());
        ref_value.setAddress(allocated_address);
        symTable.update(var_name, ref_value);
        return null;
    }
    @Override
    public String toString(){
        return "new("+var_name+ ", "+expression.toString()+")";}

    @Override
    public IStmt deepCopy(){return new HeapAllocateStmt(var_name,expression.deepCopy());}

}
