package Model.Expressions;

import Exceptions.MyException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Types.IType;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class NotExp implements IExp {
    private final IExp exp;

    public NotExp(IExp exp_) {
        exp = exp_;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap heap) throws MyException {
        BoolValue boolValue = (BoolValue) exp.eval(symTable, heap);
        if(!boolValue.getVal()) {
            return new BoolValue(true);
        }
        else {
            return new BoolValue(false);
        }
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return exp.typeCheck(typeEnv);
    }

    @Override
    public String toString() {
        return "!(" + exp.toString() + ")";
    }

    @Override
    public IExp deepCopy() {
        return new NotExp(exp.deepCopy());
    }
}
