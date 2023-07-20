package Model.CountDownLatch;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;
import Model.Exceptions.StatementException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.ILatchTable;
import Model.exp.ValueExpression;
import Model.stmt.IStmt;
import Model.stmt.PrintStmt;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStmt implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public CountDownStmt(String v){var=v;}

    @Override
    public PrgState execute(PrgState state) throws StatementException, DictionaryException {
        lock.lock();

        IDict<String, IValue> table=state.getSymTable();
        ILatchTable latch = state.getLatchTable();

        if(!table.isDefined(var))throw new StatementException("Variable not defined");

        IntValue fi = (IntValue)table.lookup(var);
        int foundIndex=fi.getValue();

        if(!latch.containsKey(foundIndex))throw new StatementException("Index not in LatchTable");
        if(latch.get(foundIndex)>=0) {
            latch.update(foundIndex, latch.get(foundIndex) - 1);
            state.getExeStack().push(new PrintStmt(new ValueExpression(new IntValue(state.getCurrentId()))));
        }
        lock.unlock();;
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws  DictionaryException, MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else throw new MyException("Var is not int");
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "countDown("+var+")";
    }
}