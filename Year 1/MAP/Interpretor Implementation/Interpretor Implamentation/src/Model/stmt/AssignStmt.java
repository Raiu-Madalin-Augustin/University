package Model.stmt;

import Model.PrgState;
import Model.exp.Exp;

public class AssignStmt implements IStmt{

    String id;
    Exp expression;

    public AssignStmt(String id, Exp exp){
        this.id = id;
    }

    @Override
    public String toString(){
        return this.id + "=" + this.expression.toString();
    }

    //public PrgState execute(){

    //}
}
