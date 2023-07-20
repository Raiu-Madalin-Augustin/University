package Model.stmt;

import Model.Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

import java.io.IOException;

public interface IStmt {
    PrgState execute(PrgState state) throws Exception;
    String toString();
    IDict<String,IType> typecheck(IDict<String,IType> typeEnv) throws MyException, DictionaryException;
    IStmt deepCopy();



}
