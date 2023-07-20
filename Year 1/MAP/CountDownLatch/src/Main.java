import Controller.Controller;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;
import Model.Exceptions.TypeException;
import Model.PrgState;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.types.RefType;
import Model.value.BoolValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.Repo;
import View.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner myObj=new Scanner(System.in);
//        System.out.println("Enter File path:");
//        String path= myObj.nextLine();
//
//        Repo repo = new Repo(path);
//        Controller contr = new Controller(repo);
//        IConsole view = new ConsoleUi(repo,contr);
//        view.run();


        // ex 1:  int v; v = 2; Print(v)
        try {
            IDict<StringValue, BufferedReader> filetable1 = new Dict<StringValue, BufferedReader>();
            IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))),
                            new PrintStmt(new VarExp("v"))));
            IDict<String,IType> typeenv=new Dict<String,IType>();
            ex1.typecheck(typeenv);
            // ex 2: a=2+3*5;b=a+1;Print(b)
            IDict<StringValue, BufferedReader> filetable2 = new Dict<StringValue, BufferedReader>();
            IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                    new CompStmt(new AssignStmt("a", new ArithExp(new ValueExpression(new IntValue(2)), new ArithExp(new ValueExpression(new IntValue(3)),
                            new ValueExpression(new IntValue(5)), '*'), '+')), new CompStmt(
                            new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExpression(new IntValue(1)), '+')),
                            new PrintStmt(new VarExp("b"))))));

            ex2.typecheck(new Dict<String,IType>());

            // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
            IDict<StringValue, BufferedReader> filetable3 = new Dict<StringValue, BufferedReader>();
            IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v",
                    new IntType()), new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
                    new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExpression(new IntValue(2))),
                            new AssignStmt("v", new ValueExpression(new IntValue(3)))), new PrintStmt(new
                            VarExp("v"))))));

            ex3.typecheck(new Dict<String,IType>());
            //ex4 string varf;varf="test.in";openRFile(varf);int varc;readFile(varf,varc);print(varc); readFile(varf,varc);print(varc)closeRFile(varf)
            IDict<StringValue, BufferedReader> filetable4 = new Dict<StringValue, BufferedReader>();
            Exp filename4 = new ValueExpression(new StringValue("ex4.in"));
            IStmt ex4 = new CompStmt(new OpenRFileStmt(filename4),
                    new CompStmt(new VarDeclStmt("v", new IntType()),
                            new CompStmt(new ReadFileStmt(filename4, "v"),
                                    new CompStmt(new PrintStmt(new VarExp("v")),
                                            new CompStmt(new IfStmt(new RelationalExp(">=", new VarExp("v"), new ValueExpression(new IntValue(2))),
                                                    new CompStmt(new ReadFileStmt(filename4, "v"), new PrintStmt(new VarExp("v"))),
                                                    new PrintStmt(new ValueExpression(new IntValue(-1)))), new CloseRFileStmt(filename4))))));

            ex4.typecheck(new Dict<String,IType>());

            //ex5 Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
            IDict<StringValue, BufferedReader> filetable5 = new Dict<StringValue, BufferedReader>();
            IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new HeapAllocateStmt("v", new ValueExpression(new IntValue(20))),
                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
                                            new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));

            ex5.typecheck(new Dict<String,IType>());

            //ex6 Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
            IDict<StringValue, BufferedReader> filetable6 = new Dict<StringValue, BufferedReader>();
            IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new HeapAllocateStmt("v", new ValueExpression(new IntValue(20))),
                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
                                            new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v"))),
                                                    new PrintStmt(new ArithExp(new HeapReadExp(new HeapReadExp(new VarExp("a"))),
                                                            new ValueExpression(new IntValue(5)), '+')))))));
            ex6.typecheck(new Dict<String,IType>());

            //ex7x  Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
            IDict<StringValue, BufferedReader> filetable7 = new Dict<StringValue, BufferedReader>();
            IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new HeapAllocateStmt("v", new ValueExpression(new IntValue(20))),
                            new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v"))),
                                    new CompStmt(new HeapWriteStmt("v", new ValueExpression(new IntValue(30))),
                                            new PrintStmt(new ArithExp(new HeapReadExp(new VarExp("v")),
                                                    new ValueExpression(new IntValue(5)), '+'))))));

            ex7.typecheck(new Dict<String,IType>());

            //ex8 Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
            IDict<StringValue, BufferedReader> filetable8 = new Dict<StringValue, BufferedReader>();
            IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new HeapAllocateStmt("v", new ValueExpression(new IntValue(20))),
                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
                                            new CompStmt(new HeapAllocateStmt("v", new ValueExpression(new IntValue(30))),
                                                    new PrintStmt(new HeapReadExp(new HeapReadExp(new VarExp("a")))))))));

            ex8.typecheck(new Dict<String,IType>());

            //ex9 int v; v=4; (while (v>0) print(v);v=v-1);print(v)
            IDict<StringValue, BufferedReader> filetable9 = new Dict<StringValue, BufferedReader>();
            IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(4))),
                            new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExpression(new IntValue(0))),
                                    new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",
                                            new ArithExp( new VarExp("v"), new ValueExpression(new IntValue(1)),'-')))),
                                    new PrintStmt(new VarExp("v")))));

            ex9.typecheck(new Dict<String,IType>());

          /*
            int v; Ref(int) a; v = 10; new(a, 22);
            fork(heapWrite(a, 30); v = 32; print(v); print(heapRead(a)));
            print(v); print(heapRead(a));
         */
            IDict<StringValue, BufferedReader> filetable10 = new Dict<>();
            IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                            new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(10))),
                                    new CompStmt(new HeapAllocateStmt("a", new ValueExpression(new IntValue(22))),
                                            new CompStmt(new ForkStmt(new CompStmt(new HeapWriteStmt("a", new ValueExpression(new IntValue(30))),
                                                    new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(32))),
                                                            new CompStmt(new PrintStmt(new VarExp("v")),
                                                                    new PrintStmt(new HeapReadExp(new VarExp("a"))))))),
                                                    new CompStmt(new PrintStmt(new VarExp("v")),
                                                            new PrintStmt(new HeapReadExp(new VarExp("a")))))))));
            ex10.typecheck(new Dict<String,IType>());


//            List<PrgState> states = new ArrayList<>();
//            PrgState p1 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex1, filetable1, new Heap(),new mySemaphoreTable());
//            Repo repo1 = new Repo(p1, "log1.txt");
//            Controller c1 = new Controller(repo1);
//
//            PrgState p2 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex2, filetable2, new Heap(),new mySemaphoreTable());
//            Repo repo2 = new Repo(p2, "log2.txt");
//            Controller c2 = new Controller(repo2);
//
//            PrgState p3 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex3, filetable3, new Heap(),new mySemaphoreTable());
//            Repo repo3 = new Repo(p3, "log3.txt");
//            Controller c3 = new Controller(repo3);
//
//            PrgState p4 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex4, filetable4, new Heap(),new mySemaphoreTable());
//            Repo repo4 = new Repo(p4, "log4.txt");
//            Controller c4 = new Controller(repo4);
//
//            PrgState p5 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex5, filetable5, new Heap(),new mySemaphoreTable());
//            Repo repo5 = new Repo(p5, "log5.txt");
//            Controller c5 = new Controller(repo5);
//
//            PrgState p6 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex6, filetable6, new Heap(),new mySemaphoreTable());
//            Repo repo6 = new Repo(p6, "log6.txt");
//            Controller c6 = new Controller(repo6);
//
//            PrgState p7 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex7, filetable7, new Heap(),new mySemaphoreTable());
//            Repo repo7 = new Repo(p7, "log7.txt");
//            Controller c7 = new Controller(repo7);
//
//            PrgState p8 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex8, filetable8, new Heap(),new mySemaphoreTable());
//            Repo repo8 = new Repo(p8, "log8.txt");
//            Controller c8 = new Controller(repo8);
//
//            PrgState p9 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex9, filetable9, new Heap(),new mySemaphoreTable());
//            Repo repo9 = new Repo(p9, "log9.txt");
//            Controller c9 = new Controller(repo9);
//
//            PrgState p10 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex10, filetable10, new Heap(),new mySemaphoreTable());
//            Repo repo10 = new Repo(p10, "log10.txt");
//            Controller c10 = new Controller(repo10);


//            TextMenu menu = new TextMenu();
//            menu.addCommand(new ExitCommand("0", "exit"));
//            menu.addCommand(new RunExample("1", ex1.toString(), c1));
//            menu.addCommand(new RunExample("2", ex2.toString(), c2));
//            menu.addCommand(new RunExample("3", ex3.toString(), c3));
//            menu.addCommand(new RunExample("4", ex4.toString(), c4));
//            menu.addCommand(new RunExample("5", ex5.toString(), c5));
//            menu.addCommand(new RunExample("6", ex6.toString(), c6));
//            menu.addCommand(new RunExample("7", ex7.toString(), c7));
//            menu.addCommand(new RunExample("8", ex8.toString(), c8));
//            menu.addCommand(new RunExample("9", ex9.toString(), c9));
//            menu.addCommand(new RunExample("10", ex10.toString(), c10));
//            menu.show();
        }catch (MyException | DictionaryException | TypeException e){
            System.out.println(e.getMessage());
        }
    }

}
