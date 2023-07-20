package Model.types;

import Model.value.BoolValue;
import Model.value.IValue;

public class BoolType implements IType{
    @Override
    public IValue defaultValue() { return new BoolValue(false);}

    @Override
    public boolean equals(Object o){
        if(o instanceof BoolType) {
            return true;
        }
        else return false;
    }

    @Override
    public IType deepCopy() { return new BoolType();}
    @Override
    public String toString(){return "bool";}
}
