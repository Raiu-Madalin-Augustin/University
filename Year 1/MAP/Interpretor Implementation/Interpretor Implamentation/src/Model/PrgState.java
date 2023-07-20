package Model;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.IStack;
import Model.adt.List;
import Model.stmt.IStmt;
import Model.value.IValue;

public class PrgState {

    IStack<IStmt> exeStack;
    IDict<String, Integer> symTable;
    IList<Integer> out;
    IStmt originalProgram; //optional field, but good to have


    public List<IValue> getOutput() {
        // can't find other return without changing implementation
        return new List<IValue>();
    }

    public IDict<String, Integer> getSymTable() {
        return this.symTable;
    }

    public void setOutput(List<IValue> output) {
    }
}