package Model.exp;

import Model.Exceptions.*;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class LogicalExp implements Exp {
    private Exp left;
    private Exp right;
    private String operator;

    LogicalExp(Exp left, Exp right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;

    }

    public IType typecheck(IDict<String, IType> typeEnv) throws MyException, DictionaryException {
        IType typ1, typ2;
        typ1 = left.typecheck(typeEnv);
        typ2 = right.typecheck(typeEnv);

        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new MyException("Second operand is not a bool");
        } else
            throw new MyException("first operand is not a bool");
    }

    @Override
    public IValue eval(IDict<String,IValue> symTable, Heap heap) throws ExpressionException, DivByZeroException, MyException, TypeException, UndeclaredVariableException, DictionaryException {
        IValue l,r;
        l=left.eval(symTable,heap);
        if(l.getType().equals(new BoolType())){
            r=right.eval(symTable,heap);
            if(r.getType().equals(new BoolType())){
                BoolValue v1=(BoolValue) l,v2=(BoolValue) r;
                boolean b1=v1.getValue(),b2=v2.getValue();

                switch (operator){
                    case "&&":
                        return new BoolValue(b1 && b2);
                    case "||":
                        return new BoolValue(b1 || b2);
                    default:
                        throw new ExpressionException("Invalid operator");
                }
            }else
                throw new ExpressionException("second operand is not boolean");

        }else
            throw new ExpressionException("first operand is not boolean");
    }

    @Override
    public String toString(){return left.toString()+' '+ operator+' '+right.toString();}

    @Override
    public Exp deepCopy() {
        return new LogicalExp(left.deepCopy(),right.deepCopy(),operator);
    }
}
