package Model.stmt;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;
import Model.Exceptions.TypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.exp.RelationalExp;
import Model.types.IType;

public class SwitchStmt implements IStmt{
    private Exp exp;
    private Exp exp1;
    private Exp exp2;
    private IStmt stmt1;
    private IStmt stmt2;
    private IStmt stmt3;

    public SwitchStmt(Exp e,Exp e1,Exp e2,IStmt s1,IStmt s2,IStmt s3){
        exp=e;
        exp1=e1;
        exp2=e2;
        stmt1=s1;
        stmt2=s2;
        stmt3=s3;
    }

    @Override
    public PrgState execute(PrgState state)  {
        IStmt newSwitch=new IfStmt(new RelationalExp("==",exp,exp1),stmt1,
                new IfStmt(new RelationalExp("==",exp,exp2),stmt2,stmt3));
        state.getExeStack().push(newSwitch);

        return null;
    }
    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException, DictionaryException, TypeException {
        IType typexp=exp.typecheck(typeEnv);
        IType typexp1=exp1.typecheck(typeEnv);
        IType typexp2=exp2.typecheck(typeEnv);

        if(typexp.equals(typexp1) && typexp.equals(typexp2)){
            stmt1.typecheck(typeEnv.deepCopy());
            stmt2.typecheck(typeEnv.deepCopy());
            stmt3.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The expression types don't match");
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp.deepCopy(),exp1.deepCopy(),exp2.deepCopy(),stmt1.deepCopy(),stmt2.deepCopy(),stmt3.deepCopy());
    }

    @Override
    public String toString() {
        return "switch("+exp.toString()+") (case "+exp1.toString()+": "+stmt1.toString()+")(case "+exp2.toString()+": "+stmt2.toString()+")(default: "+stmt3.toString()+")";
    }
}
