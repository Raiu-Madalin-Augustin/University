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
    IStmt elseStmt;

    public WhileStmt(Exp exp,IStmt st, IStmt el){
        expression=exp;
        thenStmt=st;
        elseStmt=el;

    }

    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException, DictionaryException {
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
            stack.push(new WhileStmt(expression,thenStmt,elseStmt));
            stack.push(thenStmt);
        }
        else
            stack.push(elseStmt);
        return null;
    }
    @Override
    public String toString(){
        return "(while(" +expression.toString()+") "+thenStmt.toString()+");" +elseStmt.toString();
    }
    @Override
    public IStmt deepCopy(){return new WhileStmt(expression.deepCopy(),thenStmt.deepCopy(),elseStmt.deepCopy());}

}
