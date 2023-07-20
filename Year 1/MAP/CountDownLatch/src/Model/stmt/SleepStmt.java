package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public class SleepStmt implements IStmt{
    private int nr;
    public SleepStmt(int n){ nr =n;}

    @Override
    public PrgState execute(PrgState state) {
        if( nr!=0) state.getExeStack().push(new SleepStmt(nr-1));
        return null;
    }


    @Override
    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv){
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(nr);
    }

    @Override
    public String toString(){
        return "sleep("+nr+")";

    }
}
