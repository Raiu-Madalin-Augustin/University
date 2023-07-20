package Model.stmt;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;
import Model.Exceptions.StatementException;
import Model.Exceptions.UndeclaredVariableException;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;
import Model.value.IValue;

public class VarDeclStmt implements  IStmt{
    String name;
    IType type;
    public VarDeclStmt(String name, IType type){
        this.name= name;
        this.type =type;
    }
    @Override
    public IDict<String,IType> typecheck(IDict<String,IType> typeEnv) throws MyException, DictionaryException {

        typeEnv.add(name,type);
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, UndeclaredVariableException, DictionaryException {
        IDict<String, IValue> symTable = state.getSymTable();
        if(symTable.isDefined(name)) {
            throw new UndeclaredVariableException("ERROR: This variable is already declared!\n");
        }
        symTable.add(name, type.defaultValue());
        return null;
    }
    @Override
    public String toString(){ return type.toString() + ' ' +name;}

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name,type.deepCopy());
    }

}
