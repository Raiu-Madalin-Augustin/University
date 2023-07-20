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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStmt implements IStmt{
    public Exp exp;
    public OpenRFileStmt(Exp exp) {this.exp=exp;}

    @Override
    public String toString() {return "openRFile("+this.exp.toString()+")";}

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws  MyException, DictionaryException {
        IType typeExp=this.exp.typecheck(typeEnv);
        if(typeExp.equals(new StringType())){
            return typeEnv;
        }else
            throw new MyException("the file name is not of type string");
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, DivByZeroException, ExpressionException, MyException, TypeException, UndeclaredVariableException, DictionaryException {
        IDict<String, IValue> symtbl=state.getSymTable();
        IDict<StringValue, BufferedReader> FileTable= state.getFileTable();
        Heap heap=state.getHeap();
        IValue value=exp.eval(symtbl,heap);
        if(value.getType().equals(new StringType())){
            StringValue strValue=(StringValue) value;
            if(!FileTable.isDefined(strValue))
            {
                try{
                    BufferedReader file=new BufferedReader(new FileReader(strValue.getValue()));
                    FileTable.add(strValue,file);
                    return null;
                } catch (FileNotFoundException | DictionaryException e) {
                    throw new StatementException(e.getMessage());
                }
            }else
            {
                throw new StatementException("the file is already declared");

            }
        }else
        {
            throw new StatementException("the file name is not of type string");
        }
    }
    @Override
    public IStmt deepCopy(){return new OpenRFileStmt(exp.deepCopy());}
}
