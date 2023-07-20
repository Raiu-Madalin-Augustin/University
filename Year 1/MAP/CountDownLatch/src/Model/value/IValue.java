package Model.value;

import Model.types.IType;

public interface IValue {
    IType getType();
    boolean equals(Object another);
    IValue deepCopy();
}
