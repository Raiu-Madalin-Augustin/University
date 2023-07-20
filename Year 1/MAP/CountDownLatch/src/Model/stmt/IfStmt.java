package Model.stmt;

import Model.Exceptions.*;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class IfStmt implements IStmt{
    private Exp expression;
    private IStmt then_st;
    private IStmt else_st;

    public IfStmt(Exp expression, IStmt then,IStmt else_s){
        this.expression=expression;
        this.then_st=then;
        this.else_st=else_s;
    }

    @Override
    public IDict<String,IType> typecheck(IDict<String, IType> typeEnv) throws MyException, DictionaryException, TypeException {
        IType typexp=expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            then_st.typecheck(typeEnv.deepCopy());
            else_st.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }


    @Override
    public PrgState execute(PrgState state )throws StatementException {
        IStack<IStmt> execution_stack = state.getExeStack();
        Heap heap = state.getHeap();
        try {
            IValue condition = expression.eval(state.getSymTable(),heap);
            if (condition.getType().equals(new BoolType())) {
                boolean c = ((BoolValue) condition).getValue();
                if (c)
                    execution_stack.push(then_st);
                else
                    execution_stack.push(else_st);
            } else
                throw new StatementException("Conditional expression must evaluate to a boolean value");

        }catch (ExpressionException| DivByZeroException exception)
        {
            throw new StatementException(exception.getMessage());
        } catch (MyException | TypeException | UndeclaredVariableException | DictionaryException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public String toString(){
        return String.format("IF (%s) THEN (%s) ELSE (%s)",expression.toString(),then_st.toString(),else_st.toString());
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression.deepCopy(),then_st.deepCopy(),else_st.deepCopy());
    }

}
