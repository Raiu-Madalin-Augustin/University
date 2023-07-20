package Model.stmt;


import Model.PrgState;
import Model.adt.List;
import Model.exp.Exp;
import Model.value.IValue;

public class PrintStmt implements IStmt{

    Exp expression;

    public PrintStmt(Exp exp){
        this.expression = exp;
    }

    PrgState execute(PrgState state){
        List<IValue> output = state.getOutput();
        output.add(expression.eval(state.getSymTable()));
        state.setOutput(output);
        return state;
    }
}
