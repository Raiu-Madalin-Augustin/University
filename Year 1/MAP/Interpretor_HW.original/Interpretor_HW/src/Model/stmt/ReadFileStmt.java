package Model.stmt;

import Model.Exceptions.*;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    private Exp exp;
    private String var_name;

    public ReadFileStmt(Exp exp, String var_name){
        this.exp= exp;
        this.var_name=var_name;
    }

    @Override
    public String toString(){
        return "readFile("+this.exp.toString()+", "+this.var_name+")";

    }
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws  MyException, DictionaryException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }


    @Override
    public PrgState execute(PrgState state) throws StatementException, DivByZeroException, ExpressionException, DictionaryException, MyException, TypeException, UndeclaredVariableException {
        IDict<String, IValue> symTable = state.getSymTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        Heap heap=state.getHeap();
        if (symTable.isDefined(var_name)) {
            IValue value = symTable.lookup(var_name);
            if (value.getType().equals(new IntType())) {
                value = exp.eval(symTable,heap);
                if (value.getType().equals(new StringType())) {
                    StringValue fileDesc = (StringValue) value;
                    if (fileTable.isDefined(fileDesc)) {
                        BufferedReader fileReader = fileTable.lookup(fileDesc);
                        IntValue readValue;
                        try {
                            String readStr = fileReader.readLine();
                            if (readStr == null)
                                readValue = new IntValue();
                            else
                                readValue = new IntValue(Integer.parseInt(readStr));
                            symTable.update(var_name, readValue);
                            return null;
                        } catch (IOException e) {
                            throw new StatementException("file cant  be read");

                        }
                    } else
                        throw new StatementException("file given is not open");
                } else
                    throw new StatementException("Expresion doesnt result in a string");
            } else
                throw new StatementException("variable is not an integer");
        }
        else
            throw new StatementException("variable not declared");
    }


    @Override
    public IStmt deepCopy(){return new OpenRFileStmt(this.exp.deepCopy());};
}
