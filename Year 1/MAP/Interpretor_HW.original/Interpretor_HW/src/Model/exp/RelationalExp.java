package Model.exp;

import Model.Exceptions.*;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.types.RefType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

import java.lang.reflect.Type;

public class RelationalExp implements  Exp{
    private String operator;
    private Exp exp1;
    private Exp exp2;


    public RelationalExp(String operator,Exp exp1,Exp exp2){
        this.operator=operator;
        this.exp1=exp1;
        this.exp2=exp2;
    }
    public IType typecheck(IDict<String,IType> typeEnv) throws MyException, DictionaryException {
        IType typ1 = exp1.typecheck(typeEnv);
        IType typ2 = exp2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType()))
                return new BoolType();
            else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
    }
    @Override
    public IValue eval(IDict<String,IValue>tbl, Heap heap) throws ExpressionException, DivByZeroException, MyException, TypeException, UndeclaredVariableException, DictionaryException {
        IValue v1,v2;
        v1=exp1.eval(tbl,heap);
        if(v1.getType().equals(new IntType())){
            v2=exp2.eval(tbl,heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1=(IntValue) v1,i2=(IntValue) v2;
                int op1=i1.getValue(), op2=i2.getValue();

                return switch (this.operator){
                    case "<=" -> new BoolValue(op1 <=op2);
                    case "<" -> new BoolValue(op1 <op2);
                    case ">=" -> new BoolValue(op1 >=op2);
                    case ">" -> new BoolValue(op1 >op2);
                    case "==" -> new BoolValue(op1 ==op2);
                    case "!=" -> new BoolValue(op1 !=op2);
                    default ->  throw new ExpressionException("Invalid operation!");
                };
            }
            else
                throw new ExpressionException("right operator not integer");

        }
        else
            throw new ExpressionException("left operator not integer");

    }
    @Override
    public String toString(){ return this.exp1.toString()+" "+this.operator +" "+this.exp2.toString();}

    @Override
    public Exp deepCopy(){
        return new RelationalExp(this.operator,this.exp1.deepCopy(),this.exp2.deepCopy());

    }
}
