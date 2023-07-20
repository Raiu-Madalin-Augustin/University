package Model.stmt;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;
import Model.Exceptions.StatementException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.types.IType;

public class CompStmt implements  IStmt{
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt first,IStmt second){
        this.first =first;
        this.second= second;
    }

    public IDict<String,IType> typecheck(IDict<String,IType> typeEnv) throws MyException, MyException, DictionaryException {
        //MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        // MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        // return typEnv2;
        return second.typecheck(first.typecheck(typeEnv));
    }


    @Override
    public String toString( ){ return first.toString()+ ";"+second.toString();}

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(),second.deepCopy());
    }

    @Override
    public PrgState execute(PrgState state)  {
        IStack<IStmt> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

}
