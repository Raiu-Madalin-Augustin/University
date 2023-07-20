package Model.exp;
import Model.Exceptions.*;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class ArithExp implements Exp {
    private char op;
    private Exp e1,e2;
    public ArithExp(Exp left, Exp right, char operator){
        this.e1=left;
        this.e2=right;
        this.op=operator;
    }
    public IType typecheck(IDict<String, IType> typeEnv) throws MyException, DictionaryException {
        IType typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {

            if (typ2.equals(new IntType()))
            {
                return new IntType();
            }
            else
                throw new MyException("second operand is not an integer");
        }
        else throw new MyException("first operand is not an integer");
    }

    public IValue eval(IDict<String , IValue> symTable, Heap heap) throws ExpressionException, DivByZeroException, MyException, TypeException, UndeclaredVariableException, DictionaryException {
        IValue left, right;
        left = e1.eval(symTable,heap);

        if (left.getType().equals(new IntType())) {
            right = e2.eval(symTable,heap);
            if (right.getType().equals(new IntType())) {
                IntValue v1 = (IntValue) left, v2 = (IntValue) right;
                int no1 = v1.getValue(), no2 = v2.getValue();

                switch (op) {
                    case '+':
                        return new IntValue(no1 + no2);
                    case '-':
                        return new IntValue(no1-no2);
                    case '*':
                        return new IntValue(no1*no2);
                    case '/':
                        if(no2==0)
                            throw new DivByZeroException("div by 0");
                        else
                            return new IntValue(no1/no2);
                    default:
                            throw new ExpressionException("invalid");
                }
            }
            else
                throw new ExpressionException("Second operand not an integer");

        }
            else
                throw new ExpressionException("First operand not integer");
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(e1.deepCopy(),e2.deepCopy(),op);}


    public char getOp() {return this.op;}
    public Exp getFirst() {return this.e1;}
    public Exp getSecond(){ return this.e2;}
    @Override
    public String toString() { return e1.toString() + " "+ op+" "+e2.toString(); }
}
