package Model.stmt;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public class NopStmt implements IStmt{
    public PrgState execute(PrgState state)  { return state;}
     @Override
    public String toString() { return "No operation";}

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
}
