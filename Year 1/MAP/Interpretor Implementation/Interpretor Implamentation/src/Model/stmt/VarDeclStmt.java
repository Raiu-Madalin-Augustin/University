package Model.stmt;


import Model.types.IType;

public class VarDeclStmt implements IStmt{
    String name;
    IType type;

    public VarDeclStmt(String name, IType type){
        this.name = name;
        this.type = type;
    }


}
