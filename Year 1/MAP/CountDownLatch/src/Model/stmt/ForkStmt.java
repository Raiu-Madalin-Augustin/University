package Model.stmt;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;
import Model.Exceptions.StatementException;
import Model.Exceptions.TypeException;
import Model.PrgState;
import Model.adt.ADTStack;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.types.IType;

public class ForkStmt implements IStmt{
    private  IStmt statement;
    public ForkStmt(IStmt statement){this.statement=statement;}

    @Override
    public IDict<String, IType> typecheck(IDict<String,IType>typeEnv) throws MyException, DictionaryException, TypeException {

        return statement.typecheck(typeEnv.deepCopy());
    }

    @Override
    public PrgState execute(PrgState state)throws StatementException{
        return new PrgState(new ADTStack<>(), state.getSymTableDeepCopy(), state.getOutput(), statement,
                state.getFileTable(), state.getHeap(),state.getLatchTable());
         }

    @Override
    public IStmt deepCopy(){return new ForkStmt(statement.deepCopy());}

    @Override
    public String toString(){ return String.format("fork(%s)",statement.toString());}

}
