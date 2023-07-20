package Model.value;

import Model.types.IType;
import Model.types.IntType;

public class IntValue implements IValue{
    int value;
    public IntValue() {this.value =0;}
    public IntValue(int i){this.value =i;}

    @Override
    public boolean equals(Object o){
        if(o instanceof IntValue) {
            if(value == ((IntValue) o).getValue()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() { return  String.valueOf(value);}

    @Override
    public IType getType() { return new IntType();
    }

    public int getValue() { return this.value;}

    @Override
    public IValue deepCopy() { return new IntValue(this.value);}

}
