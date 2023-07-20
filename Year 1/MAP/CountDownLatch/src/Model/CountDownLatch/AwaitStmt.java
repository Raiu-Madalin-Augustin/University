package Model.CountDownLatch;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;
import Model.Exceptions.StatementException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.ILatchTable;
import Model.stmt.IStmt;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class AwaitStmt implements IStmt {
    private String var;

    public AwaitStmt(String v){var=v;}

    @Override
    public PrgState execute(PrgState state) throws StatementException, DictionaryException {

        IDict<String, IValue> table=state.getSymTable();
        ILatchTable latch = state.getLatchTable();

        if(!table.isDefined(var))throw new StatementException("Variable not defined");

        IntValue fi = (IntValue)table.lookup(var);
        int foundIndex=fi.getValue();

        if(!latch.containsKey(foundIndex)) throw new StatementException("Index not in LatchTable");
        if(latch.get(foundIndex)!=0)
            state.getExeStack().push(this);

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
        return "await("+var+")";
    }
}
