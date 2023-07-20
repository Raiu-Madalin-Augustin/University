package GUI;

import Controller.Controller;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;
import Model.Exceptions.RepositoryException;
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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class runFormController implements Initializable {
    public void ProgramListController() {
    }

    private List<Controller> program = new ArrayList<>();

    @FXML
    private ListView<String> programListView = new ListView<>();

    public void setListView() {
        try {
            IDict<StringValue, BufferedReader> filetable1 = new Dict<StringValue, BufferedReader>();
            IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))),
                            new PrintStmt(new VarExp("v"))));

            IDict<String, IType> typeenv = new Dict<String, IType>();
            ex1.typecheck(typeenv);

            List<PrgState> states = new ArrayList<>();
            PrgState p1 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex1, filetable1, new Heap());
            Repo repo1 = new Repo(p1, "log1.txt");
            Controller c1 = new Controller(repo1);
            program.add(c1);

        } catch (MyException | DictionaryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("TypeCheck error");
            alert.setContentText("Example 1"+e.getMessage());

            alert.showAndWait();

        }

        try {
            // ex 2: a=2+3*5;b=a+1;Print(b)
            IDict<StringValue, BufferedReader> filetable2 = new Dict<StringValue, BufferedReader>();
            IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                    new CompStmt(new AssignStmt("a", new ArithExp(new ValueExpression(new IntValue(2)), new ArithExp(new ValueExpression(new IntValue(3)),
                            new ValueExpression(new IntValue(5)), '*'), '+')), new CompStmt(
                            new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExpression(new IntValue(1)), '+')),
                            new PrintStmt(new VarExp("b"))))));

            ex2.typecheck(new Dict<String, IType>());


            PrgState p2 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex2, filetable2, new Heap());
            Repo repo2 = new Repo(p2, "log2.txt");
            Controller c2 = new Controller(repo2);
            program.add(c2);

        }  catch (MyException | DictionaryException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("error");
        alert.setHeaderText("TypeCheck error");
        alert.setContentText("Example 2"+e.getMessage());

        alert.showAndWait();

    }

        try {
            // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
            IDict<StringValue, BufferedReader> filetable3 = new Dict<StringValue, BufferedReader>();
            IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v",
                    new IntType()), new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
                    new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExpression(new IntValue(2))),
                            new AssignStmt("v", new ValueExpression(new IntValue(3)))), new PrintStmt(new
                            VarExp("v"))))));

            ex3.typecheck(new Dict<String, IType>());
            PrgState p3 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex3, filetable3, new Heap());
            Repo repo3 = new Repo(p3, "log3.txt");
            Controller c3 = new Controller(repo3);
            program.add(c3);

        }  catch (MyException | DictionaryException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("error");
        alert.setHeaderText("TypeCheck error");
        alert.setContentText("Example 3"+e.getMessage());

        alert.showAndWait();

    }

        try{
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

            ex4.typecheck(new Dict<String, IType>());

            PrgState p4 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex4, filetable4, new Heap());
            Repo repo4 = new Repo(p4, "log4.txt");
            Controller c4 = new Controller(repo4);
            program.add(c4);

        }  catch (MyException | DictionaryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("TypeCheck error");
            alert.setContentText("Example 4"+e.getMessage());

            alert.showAndWait();

        }
        try{
            //ex5 Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
            IDict<StringValue, BufferedReader> filetable5 = new Dict<StringValue, BufferedReader>();
            IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new HeapAllocateStmt("v", new ValueExpression(new IntValue(20))),
                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
                                            new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));

            ex5.typecheck(new Dict<String, IType>());

            PrgState p5 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex5, filetable5, new Heap());
            Repo repo5 = new Repo(p5, "log5.txt");
            Controller c5 = new Controller(repo5);

            program.add(c5);

        }  catch (MyException | DictionaryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("TypeCheck error");
            alert.setContentText("Example 5"+e.getMessage());

            alert.showAndWait();

        }
        try{
            //ex6 Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
            IDict<StringValue, BufferedReader> filetable6 = new Dict<StringValue, BufferedReader>();
            IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new HeapAllocateStmt("v", new ValueExpression(new IntValue(20))),
                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
                                            new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v"))),
                                                    new PrintStmt(new ArithExp(new HeapReadExp(new HeapReadExp(new VarExp("a"))),
                                                            new ValueExpression(new IntValue(5)), '+')))))));
            ex6.typecheck(new Dict<String, IType>());
            PrgState p6 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex6, filetable6, new Heap());
            Repo repo6 = new Repo(p6, "log6.txt");
            Controller c6 = new Controller(repo6);
            program.add(c6);

        }  catch (MyException | DictionaryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("TypeCheck error");
            alert.setContentText("Example 6"+e.getMessage());

            alert.showAndWait();

        }
        try{
            //ex7x  Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
            IDict<StringValue, BufferedReader> filetable7 = new Dict<StringValue, BufferedReader>();
            IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new HeapAllocateStmt("v", new ValueExpression(new IntValue(20))),
                            new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v"))),
                                    new CompStmt(new HeapWriteStmt("v", new ValueExpression(new IntValue(30))),
                                            new PrintStmt(new ArithExp(new HeapReadExp(new VarExp("v")),
                                                    new ValueExpression(new IntValue(5)), '+'))))));

            ex7.typecheck(new Dict<String, IType>());
            PrgState p7 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex7, filetable7, new Heap());
            Repo repo7 = new Repo(p7, "log7.txt");
            Controller c7 = new Controller(repo7);
            program.add(c7);

        }  catch (MyException | DictionaryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("TypeCheck error");
            alert.setContentText("Example 7"+e.getMessage());

            alert.showAndWait();

        }
        try{
            //ex8 Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
            IDict<StringValue, BufferedReader> filetable8 = new Dict<StringValue, BufferedReader>();
            IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new HeapAllocateStmt("v", new ValueExpression(new IntValue(20))),
                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
                                            new CompStmt(new HeapAllocateStmt("v", new ValueExpression(new IntValue(30))),
                                                    new PrintStmt(new HeapReadExp(new HeapReadExp(new VarExp("a")))))))));

            ex8.typecheck(new Dict<String, IType>());

            PrgState p8 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex8, filetable8, new Heap());
            Repo repo8 = new Repo(p8, "log8.txt");
            Controller c8 = new Controller(repo8);
            program.add(c8);

        }  catch (MyException | DictionaryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("TypeCheck error");
            alert.setContentText("Example 8"+e.getMessage());

            alert.showAndWait();

        }
        try{
            //ex9 int v; v=4; (while (v>0) print(v);v=v-1);print(v)
            IDict<StringValue, BufferedReader> filetable9 = new Dict<StringValue, BufferedReader>();
            IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(4))),
                            new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExpression(new IntValue(0))),
                                    new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",
                                            new ArithExp(new VarExp("v"), new ValueExpression(new IntValue(1)), '-'))),
                                    new PrintStmt(new VarExp("v")))));

            ex9.typecheck(new Dict<String, IType>());

            PrgState p9 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex9, filetable9, new Heap());
            Repo repo9 = new Repo(p9, "log9.txt");
            Controller c9 = new Controller(repo9);
            program.add(c9);

        }  catch (MyException | DictionaryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("TypeCheck error");
            alert.setContentText("Example 9"+e.getMessage());

            alert.showAndWait();

        }
        try{
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
            ex10.typecheck(new Dict<String, IType>());

            PrgState p10 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex10, filetable10, new Heap());
            Repo repo10 = new Repo(p10, "log10.txt");
            Controller c10 = new Controller(repo10);
            program.add(c10);


        }  catch (MyException | DictionaryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("TypeCheck error");
            alert.setContentText("Example 10"+e.getMessage());

            alert.showAndWait();

        }



        programListView.setItems(FXCollections.observableArrayList(
                program.stream().map(Controller::getExecution_logs).collect(Collectors.toList())
        ));



    }

    @FXML
    private void onRunButtonPressed(){
        if(programListView.getSelectionModel().getSelectedItem()!=null){
            try{
                FXMLLoader loader=new FXMLLoader(getClass().getResource("SelectForm.fxml"));
                Parent interpreterView=loader.load();
                SelectFormController controller=loader.getController();
                controller.setController(program.get(programListView.getSelectionModel().getSelectedIndex()));
                Stage stage= new Stage();
                stage.setTitle("Main Window");
                stage.setScene(new Scene(interpreterView));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }
}
