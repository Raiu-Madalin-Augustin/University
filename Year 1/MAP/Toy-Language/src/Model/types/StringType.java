package Model.types;

import Model.value.IValue;
import Model.value.StringValue;

public class StringType  implements  IType{
    @Override
    public  boolean equals(Object another)
    {
        if(another instanceof StringType)
            return true;
        return false;
    }
    @Override
    public String toString(){ return "string";}
    @Override
    public IValue defaultValue(){
        IValue defVal=new StringValue("");
        return defVal;
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }
}
