package Model.value;

import Model.types.IType;
import Model.types.StringType;

public class StringValue implements  IValue{
    private String val;
    @Override
    public boolean equals(Object another){
        if(another instanceof StringValue) {
            if(val.equals(((StringValue) another).getValue())) {
                return true;
            }
        }
        return false;
    }
    public StringValue(String v){
        val=v;
    }
    public String getValue(){return val;}
    @Override
    public String toString(){return val;}
    @Override
    public IType getType() {return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(val);
    }
}
