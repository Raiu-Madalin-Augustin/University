package Model.stmt;

import Model.Exceptions.*;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.exp.NotExp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class RepeatUntilStmt implements IStmt{
    IStmt stmt;
    Exp exp;

    public RepeatUntilStmt(IStmt stmt1,Exp exp1)
    {
        exp=exp1;
        stmt=stmt1;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, DivByZeroException, TypeException, UndeclaredVariableException, ExpressionException, DictionaryException {
        IDict<String, IValue>symTable=state.getSymTable();
        Heap heap =state.getHeap();
        IStack<IStmt> stack =state.getExeStack();
        Exp notExp=new NotExp(exp);
        IValue notCondition=notExp.eval(symTable,heap);
        BoolValue boolCondition= (BoolValue) notCondition;

        if(boolCondition.getValue()){
            stack.push(new CompStmt(stmt,new WhileStmt(notExp,stmt)));
        }
        return null;
    }
   @Override
   public IDict<String, IType> typecheck(IDict<String,IType>typeEnv) throws MyException, DictionaryException, TypeException {
        IType typeExp=exp.typecheck(typeEnv);
        if(typeExp.equals(new BoolType())){
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
        {
            throw new TypeException("Error:not boolean");
        }
   }
   @Override
    public String toString(){return "repeat{" + stmt.toString()+ "} until(" +exp.toString()+";)";}
    @Override
    public IStmt deepCopy(){return new RepeatUntilStmt(stmt.deepCopy(),exp.deepCopy());}
}
