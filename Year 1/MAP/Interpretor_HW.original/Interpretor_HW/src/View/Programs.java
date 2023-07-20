package View;

import Model.PrgState;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.IRepo;


import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Programs {
    public static void addPrograms(IRepo repository)
    {

        // ex 1:  int v; v = 2; Print(v)
        IDict<StringValue, BufferedReader> filetable1=new Dict<StringValue,BufferedReader>();
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExpression(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        // ex 2: a=2+3*5;b=a+1;Print(b)
        IDict<StringValue, BufferedReader> filetable2=new Dict<StringValue,BufferedReader>();
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp(new ValueExpression(new IntValue(2)),new ArithExp(new ValueExpression(new IntValue(3)),
                        new ValueExpression(new IntValue(5)),'*'),'+')),  new CompStmt(
                        new AssignStmt("b",new ArithExp(new VarExp("a"), new ValueExpression(new IntValue(1)),'+')),
                        new PrintStmt(new VarExp("b"))))));

        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IDict<StringValue, BufferedReader> filetable3=new Dict<StringValue,BufferedReader>();
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExpression(new IntValue(2))),
                        new AssignStmt("v", new ValueExpression(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));

        //ex4 string varf;varf="test.in";openRFile(varf);int varc;readFile(varf,varc);print(varc); readFile(varf,varc);print(varc)closeRFile(varf)
        IDict<StringValue, BufferedReader> filetable4=new Dict<StringValue,BufferedReader>();
        Exp filename4= new ValueExpression(new StringValue("ex4.in"));
        IStmt ex4=new CompStmt(new OpenRFileStmt(filename4),
                new CompStmt(new VarDeclStmt("v",new IntType()),
                        new CompStmt(new ReadFileStmt(filename4,"v"),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new CompStmt(new IfStmt(new RelationalExp(">=",new VarExp("v"),new ValueExpression(new IntValue(2))),
                                                new CompStmt(new ReadFileStmt(filename4,"v"),new PrintStmt(new VarExp("v"))),
                                                new PrintStmt(new ValueExpression(new IntValue(-1)))),new CloseRFileStmt(filename4))))));



        List<PrgState> states = new ArrayList<>();
        PrgState p1=new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(),ex1,filetable1,new Heap());
        PrgState p2=new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(),ex2,filetable2,new Heap());
        PrgState p3=new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(),ex3,filetable3,new Heap());
        PrgState p4=new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(),ex4,filetable4,new Heap());
        states.add(p1);
        states.add(p2);
        states.add(p3);
        states.add(p4);
        repository.setProgramStateList(states);
    }
}
