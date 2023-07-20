package Model.value;

import Model.types.IType;
import Model.types.RefType;

public class RefValue implements IValue{
    int adress;
    IType locationType;

    public RefValue(int adress,IType type){
        this.adress=adress;
        this.locationType=type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass())
            return false;
        RefValue o_value = (RefValue) o;
        return o_value.adress == this.adress;
    }
    public void setAddress(int a){this.adress=a;
    }
    @Override
    public String toString(){return "("+adress+", "+locationType+")";}

    public int getAddress() {return adress;}
    @Override
    public IType getType() { return new RefType(locationType);}

    @Override
    public IValue deepCopy() {
        return new RefValue(adress,locationType.deepCopy());
    }

}
