package View;
import Model.exp.ArithExp;
import Model.exp.VarExp;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IntValue;
import Repo.Repo;
import Controller.Controller;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.Dict;
import Model.adt.IList;
import Model.adt.List;

import javax.management.ValueExp;


public class Main {

    static Repo myRepository = new Repo();
    static Controller myController = new Controller(myRepository);

    public static void main(String[] args) {
        /*
        IStmt originalProgram = new IfStmt(new ConstExp(10),
                new CompStmt(new AssignStmt("v", new ConstExp(5)),
                        new PrintStmt(new ArithExp('/',
                                new VarExp("v"), new ConstExp(3)))),
                new PrintStmt(new ConstExp(100)));
        MyIStack<IStmt> exeStack = new MyStack<IStmt>();
        //exeStack.push(originalProgram);
        IDict<String, Integer> symTable = new Dict<String, Integer>();
        IList<Integer> out = new List<Integer>();
        PrgState myPrgState = new PrgState(exeStack, symTable, out, originalProgram);
        myController.addProgram(myPrgState);
        myController.allStep();
*/
 // ex 1:  int v; v = 2; Print(v)
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),  new CompStmt(
                                new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));

        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                                new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                VarExp("v"))))));

    }
}
