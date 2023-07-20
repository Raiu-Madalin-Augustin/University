package Model.stmt;

import Model.Exceptions.*;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class WhileStmt implements IStmt{
    Exp expression;
    IStmt thenStmt;


    public WhileStmt(Exp exp,IStmt st){
        expression=exp;
        thenStmt=st;


    }
    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException, DictionaryException, TypeException {
        IType typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenStmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else
            throw new MyException("The condition of WHILE has not the type bool");
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, DivByZeroException, TypeException, UndeclaredVariableException, ExpressionException, DictionaryException {
        IDict<String, IValue> symTable=state.getSymTable();
        Heap heap=state.getHeap();
        IStack<IStmt> stack=state.getExeStack();
        IValue condition=expression.eval(symTable,heap);
        if(!condition.getType().equals(new BoolType())){
            throw new TypeException("the provided condition is not a bool");
        }
        BoolValue boolCondition=(BoolValue) condition;

        if(boolCondition.getValue()){
            stack.push(new WhileStmt(expression,thenStmt));
            stack.push(thenStmt);
        }

        return null;
    }
    @Override
    public String toString(){
        return "(while(" +expression.toString()+") "+thenStmt.toString()+");";
    }
    @Override
    public IStmt deepCopy(){return new WhileStmt(expression.deepCopy(),thenStmt.deepCopy());}

}
