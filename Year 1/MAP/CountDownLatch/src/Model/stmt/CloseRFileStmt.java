package Model.stmt;

import Model.Exceptions.*;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    Exp exp;
    public CloseRFileStmt(Exp exp){this.exp=exp;}

    @Override
    public IDict<String, IType> typecheck(IDict<String,IType>typeEnv) throws MyException, DictionaryException {
    IType typeExp=this.exp.typecheck(typeEnv);
    if(typeExp.equals(new StringType()))
    {
        return typeEnv;
    }
    else
    {
        throw new MyException("type is not string");
    }

    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, DivByZeroException, ExpressionException, DictionaryException, IOException, MyException, TypeException, UndeclaredVariableException {
        IDict<String, IValue> symTable=state.getSymTable();
        IDict<StringValue, BufferedReader> fileTable=state.getFileTable();
        Heap heap=state.getHeap();
        IValue value= exp.eval(symTable,heap);
        if(value.getType().equals(new StringType())){
            StringValue strValue=(StringValue)value;
            if(fileTable.isDefined(strValue)){
                BufferedReader reader=fileTable.lookup(strValue);
                try{
                    reader.close();
                    fileTable.remove(strValue);

                }
                catch (IOException ioe) {
                    throw new StatementException("cannot close file");
                }
                return null;
            }
            else
                throw new StatementException("this file is not declared");


        }
        else
            throw new StatementException("type should be string");
    }

    @Override
    public String toString(){return "closeRFile("+exp.toString()+")";}



    @Override
    public IStmt deepCopy() {return new CloseRFileStmt(exp.deepCopy());}

}
