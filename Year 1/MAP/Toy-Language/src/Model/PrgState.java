package Model;

import Model.Exceptions.EmptyExecutionStackException;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {

    private IStack<IStmt> exeStack;
    private IDict<String, IValue> symTable;
    private IList<IValue> out_list;
    private IStmt originalProgram;
    private IDict<StringValue,BufferedReader> fileTable;
    private Heap heap;

    private int id;
    private static int current_generated_id=1;

    public PrgState(IStack<IStmt> stack,IDict<String,IValue>dict,IList<IValue>list,IStmt statement,IDict<StringValue,BufferedReader> fileTable,Heap heap){
        this.exeStack=stack;
        this.symTable=dict;
        this.out_list=list;
        this.originalProgram =statement.deepCopy();
        this.exeStack.push(statement);
        this.fileTable=fileTable;
        this.heap=heap;
        this.id=getID();

    }
    public IDict<String, IValue> getSymTableDeepCopy() {
        return symTable.deepCopy();
    }
    public Heap getHeap(){return this.heap;}

    public IDict<StringValue,BufferedReader> getFileTable(){return this.fileTable;}

    public IList<IValue>getOutput(){
        return out_list;
    }

    public IDict<String,IValue> getSymTable() { return symTable;}

    public IStack<IStmt> getExeStack(){return exeStack;}
    public IStmt getInitialStatement() {
        return originalProgram;
    }
    @Override
    public String toString() {
        return "Id: " +
                id + "\n" +
                "ExeStack:\n" +
                exeStack.toString() +
                "SymTable:\n" +
                symTable.toString() +
                "Out:\n" +
                out_list.toString() +
                "FileTable:\n" +
                fileTable.toString2() +
                "Heap:\n" +
                heap.toString() +
                "--------------------------------------------------------------------------------------" +
                "------------------------------------------------------------------------------------\n";


    }
    public String toString2(){
        return exeStack.toString();
    }
    public Boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }
    public PrgState oneStepExecution()throws Exception, EmptyExecutionStackException{
        if(exeStack.isEmpty())
            throw new EmptyExecutionStackException("Empty execution stack error!");
        IStmt current_statement=null;
        try{
            current_statement=exeStack.pop();
            return current_statement.execute(this);
        }catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }
    public int getCurrentId(){
        return id;
    }
    private static synchronized int getID(){return current_generated_id++;}

}
