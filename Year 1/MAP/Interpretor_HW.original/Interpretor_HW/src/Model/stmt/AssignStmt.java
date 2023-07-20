package Model.stmt;

import Model.Exceptions.*;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;

public class AssignStmt  implements  IStmt {
    private String id;
    private Exp expression;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.expression = exp;
    }
    public IDict<String,IType> typecheck(IDict<String,IType> typeEnv) throws MyException, DictionaryException {
        IType typevar = typeEnv.lookup(id);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return this.id + '=' + this.expression.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, expression.deepCopy());
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeap();
        if (symTable.isDefined(id)) {
            IValue val = expression.eval(symTable, heap);
            IType typeId = (symTable.lookup(id)).getType();
            if ((val.getType()).equals(typeId)) {
                symTable.update(id, val);
            } else throw new Exception("ERROR: Declared type of variable" + id +
                    "and type of the assigned expression do not match!\n");
        } else throw new UndeclaredVariableException("ERROR: The used variable " + id + " was not declared before!\n");
        return null;

    }
}
