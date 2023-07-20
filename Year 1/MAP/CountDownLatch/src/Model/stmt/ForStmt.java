package Model.stmt;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.exp.RelationalExp;
import Model.exp.VarExp;
import Model.types.IType;
import Model.types.IntType;

public class ForStmt implements  IStmt{
    private String v;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;
    private IStmt stmt;

    public ForStmt(String val,Exp e1,Exp e2,Exp e3, IStmt s ){
        v=val;
        exp1=e1;
        exp2=e2;
        exp3=e3;
        stmt=s;
    }

    @Override
    public PrgState execute(PrgState state) {
        var exeStack = state.getExeStack();
        RelationalExp vLowerExp2 = new RelationalExp("<", new VarExp(v), exp2);
        AssignStmt vEqualExp1 = new AssignStmt(v, exp1);
        AssignStmt vEqualExp3 = new AssignStmt(v, exp3);
        WhileStmt whilevexp2 = new WhileStmt(vLowerExp2, new CompStmt(stmt, vEqualExp3));
        IStmt equivalentStmt = new CompStmt(vEqualExp1, whilevexp2);
        exeStack.push(equivalentStmt);
        return null;
    }
    @Override
    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws DictionaryException, MyException {
//        typeEnv.add(v,new IntType());
//        IType typv=typeEnv.lookup(v);
//        IType typexp1=exp1.typecheck(typeEnv);
//        IType typexp2=exp2.typecheck(typeEnv);
//        IType typexp3=exp3.typecheck(typeEnv);
//
//        if(typv.equals(new IntType())&&typexp1.equals(new IntType()) && typexp2.equals(new IntType()) && typexp3.equals(new IntType())) return typeEnv;
//        else throw new MyException("The for is invalid");

        return typeEnv;
    }
    @Override
    public String toString(){
        return "for(" +
                v + "=" + exp1 + ";" +
                v + "<" + exp2 + ";" +
                v + "=" + exp3 + ") " +
                stmt;    }
    @Override
    public IStmt deepCopy(){return new ForStmt(v,exp1.deepCopy(),exp2.deepCopy(),exp3.deepCopy(),stmt.deepCopy());}
}
