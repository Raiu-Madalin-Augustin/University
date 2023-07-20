package Model.Statements;

import Exceptions.MyException;
import Exceptions.TypeException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.Expressions.IExp;
import Model.Expressions.NotExp;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Values.BoolValue;
import Model.Values.IValue;

import java.io.IOException;

public class RepeatUntilStmt implements IStmt {
    IStmt stmt;
    IExp exp;

    public RepeatUntilStmt(IStmt stmt_,IExp exp_) {
        exp = exp_;
        stmt = stmt_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeapTable();
        IStack<IStmt> stack = state.getStack();
        IExp notExp = new NotExp(exp);
        IValue notCondition = notExp.eval(symTable, heap);
        BoolValue boolCondition = (BoolValue) notCondition;

        if(boolCondition.getVal()) {
            stack.push(new CompStmt(stmt, new WhileStmt(notExp, stmt)));
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typeCheck(typeEnv);

        if(typeExp.equals(new BoolType())) {
            stmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else {
            throw new TypeException("ERROR: The provided condition is not a boolean!\n");
        }
    }

    @Override
    public String toString() {
        return "repeat{" + stmt.toString() + "} until (" + exp.toString() +";)";
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatUntilStmt(stmt.deepCopy(), exp.deepCopy());
    }
}
