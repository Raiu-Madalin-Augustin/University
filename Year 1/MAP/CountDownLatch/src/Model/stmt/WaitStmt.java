package Model.stmt;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.ValueExpression;
import Model.types.IType;
import Model.value.IntValue;

public class WaitStmt implements IStmt{
    private int nr;

    public WaitStmt(int n){nr=n;}

    @Override
    public PrgState execute(PrgState state)  {
        if(nr!=0) state.getExeStack().push(new CompStmt(new PrintStmt(new ValueExpression(new IntValue(nr))),new WaitStmt(nr-1)));
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new WaitStmt(nr);
    }

    @Override
    public String toString() {
        return "wait("+nr+")";
    }
}
