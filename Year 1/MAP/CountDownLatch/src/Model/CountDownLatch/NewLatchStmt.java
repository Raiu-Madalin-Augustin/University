package Model.CountDownLatch;


import Model.Exceptions.*;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.ILatchTable;
import Model.exp.Exp;
import Model.stmt.IStmt;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt {
    private String var;
    private Exp exp;
    private static Lock lock=new ReentrantLock();

    public NewLatchStmt(String v,Exp e){var=v;exp=e;}

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        lock.lock();
        try{
            IDict<String, IValue> table=state.getSymTable();
            Heap heap=state.getHeap();
            ILatchTable latch = state.getLatchTable();

            IntValue nr = (IntValue)(exp.eval(table, heap));
            int number = nr.getValue();

            int freeLoc= latch.getFreeAddress();
            latch.put(freeLoc,number);

            if(table.isDefined(var))
                table.update(var,new IntValue(freeLoc));
            else
                table.add(var,new IntValue(freeLoc));

        } catch(MyException e){
            throw new StatementException(e.getMessage());
        } catch (DivByZeroException | TypeException | UndeclaredVariableException | ExpressionException | DictionaryException e) {
            e.printStackTrace();
        }
        lock.unlock();
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws DictionaryException, MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            if(exp.typecheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else throw new MyException("Exp is not int");
        else throw new MyException("Var is not int");
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "newLatch("+var+","+exp.toString()+")";
    }
}
