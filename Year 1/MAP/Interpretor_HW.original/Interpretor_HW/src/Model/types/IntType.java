package Model.types;

import Model.value.IValue;
import Model.value.IntValue;

public class IntType implements IType{
    @Override
    public IValue defaultValue() {return new IntValue(0);}

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof IntType) {
            return true;
        }
        else return false;
    }
    @Override
    public IType deepCopy() {return new IntType();}

    @Override
    public String toString(){ return "int";}

}
