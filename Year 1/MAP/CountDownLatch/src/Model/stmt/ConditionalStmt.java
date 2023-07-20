package Model.stmt;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.exp.VarExp;
import Model.types.BoolType;
import Model.types.IType;

public class ConditionalStmt implements IStmt{
        private String v;
        private Exp exp1;
        private Exp exp2;
        private Exp exp3;


        public ConditionalStmt(String v1,Exp e1,Exp e2,Exp e3){
                v=v1;
                exp1=e1;
                exp2=e2;
                exp3=e3;

        }

        @Override
        public PrgState execute(PrgState state) {
                IStmt newIf=new IfStmt(exp1,new AssignStmt(v,exp2),new AssignStmt(v,exp3));
                state.getExeStack().push(newIf);
               return null;
        }
        @Override
        public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException, DictionaryException {
                IType typev=new VarExp(v).typecheck(typeEnv);
                IType typexp1=exp1.typecheck(typeEnv);
                IType typexp2=exp2.typecheck(typeEnv);
                IType typexp3=exp3.typecheck(typeEnv);

                if(typexp1.equals(new BoolType()) && typexp2.equals(typev) && typexp3.equals(typev)) return typeEnv;
                else throw new MyException("The conditional assignment is invalid");
        }

        @Override
        public IStmt deepCopy() {
                return new ConditionalStmt(v,exp1.deepCopy(),exp2.deepCopy(),exp3.deepCopy());
        }

        @Override
        public String toString(){
                return v+"=("+exp1.toString()+")?"+exp2.toString()+":"+exp3.toString();
        }
}
