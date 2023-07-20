package Model.stmt;

import Model.Exceptions.*;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IList;
import Model.exp.Exp;
import Model.PrgState;
import Model.adt.ADTList;
import Model.types.IType;
import Model.value.IValue;

public class PrintStmt implements IStmt{
    Exp expression;

    public PrintStmt(Exp exp) { this.expression =exp;}

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException, DictionaryException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, UndeclaredVariableException {
        IList<IValue> output =state.getOutput();
        Heap heap=state.getHeap();
       try {
           output.add(expression.eval(state.getSymTable(),heap));

       }catch (ExpressionException | DivByZeroException exception) {

           throw new StatementException(exception.getMessage());
       } catch (MyException | TypeException | UndeclaredVariableException | DictionaryException e) {
           throw new UndeclaredVariableException(e.getMessage());
       }
        return null;
    }
    @Override
    public String toString() {
        return String.format("print(%s)", expression.toString());
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(expression.deepCopy());
    }


}
