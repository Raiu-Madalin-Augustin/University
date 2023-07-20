package GUI;

import Controller.Controller;
import Model.CountDownLatch.AwaitStmt;
import Model.CountDownLatch.CountDownStmt;
import Model.CountDownLatch.NewLatchStmt;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;
import Model.Exceptions.RepositoryException;
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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class runFormController implements Initializable {
    public void ProgramListController() {
    }
    public static IStmt makeIt(IStmt... stmtList){
        if(stmtList.length==1)return stmtList[0];
        return new CompStmt(stmtList[0],makeIt(Arrays.copyOfRange(stmtList,1,stmtList.length)));
    }

    private List<Controller> program = new ArrayList<>();

    @FXML
    private ListView<String> programListView = new ListView<>();

    public void setListView() {
    try {
        IDict<StringValue, BufferedReader> filetable1 = new Dict<>();

        IStmt ex1 = makeIt(new VarDeclStmt("v1", new RefType(new IntType())), new VarDeclStmt("v2", new RefType(new IntType())),
                new VarDeclStmt("v3", new RefType(new IntType())), new VarDeclStmt("cnt", new IntType()),
                new HeapAllocateStmt("v1", new ValueExpression(new IntValue(2))), new HeapAllocateStmt("v2", new ValueExpression(new IntValue(3))),
                new HeapAllocateStmt("v3", new ValueExpression(new IntValue(4))), new NewLatchStmt("cnt", new HeapReadExp(new VarExp("v2"))),
                new ForkStmt(makeIt(new HeapWriteStmt("v1", new ArithExp(new HeapReadExp(new VarExp("v1")), new ValueExpression(new IntValue(10)), '*')),
                        new PrintStmt(new HeapReadExp(new VarExp("v1"))), new CountDownStmt("cnt"),
                        new ForkStmt(makeIt(new HeapWriteStmt("v2", new ArithExp(new HeapReadExp(new VarExp("v2")), new ValueExpression(new IntValue(10)), '*')),
                                new PrintStmt(new HeapReadExp(new VarExp("v2"))), new CountDownStmt("cnt"),
                                new ForkStmt(makeIt(new HeapWriteStmt("v3", new ArithExp(new HeapReadExp(new VarExp("v3")), new ValueExpression(new IntValue(10)), '*')),
                                        new PrintStmt(new HeapReadExp(new VarExp("v3"))), new CountDownStmt("cnt"))))))),
                new AwaitStmt("cnt"), new PrintStmt(new ValueExpression(new IntValue(100))), new CountDownStmt("cnt"), new PrintStmt(new ValueExpression(new IntValue(100))));

        ex1.typecheck(new Dict<String,IType>());
        PrgState p1 = new PrgState(new ADTStack<>(), new Dict<>(), new ADTList<>(), ex1, filetable1, new Heap(), new myLatchTable());
        Repo repo1 = new Repo(p1, "log1.txt");
        Controller c1 = new Controller(repo1);
        program.add(c1);
    }catch (MyException|DictionaryException|TypeException e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("error");
        alert.setHeaderText("TypeCheck error");
        alert.setContentText("Example 14"+e.getMessage());

        alert.showAndWait();
    }

        try{


            IDict<StringValue, BufferedReader> filetable2 = new Dict<>();


            IStmt ex2 = makeIt(new VarDeclStmt("a", new RefType(new IntType())),
                    new VarDeclStmt("b", new RefType(new IntType())),
                    new VarDeclStmt("v", new IntType()),
                    new HeapAllocateStmt("a", new ValueExpression(new IntValue(0))),
                    new HeapAllocateStmt("b", new ValueExpression(new IntValue(0))),
                    new HeapWriteStmt("a", new ValueExpression(new IntValue(1))),
                    new HeapWriteStmt("b", new ValueExpression(new IntValue(2))),
                    new ConditionalStmt("v", new RelationalExp("<", new HeapReadExp(new VarExp("a")), new HeapReadExp(new VarExp("b"))), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                    new PrintStmt(new VarExp("v")),
                    new ConditionalStmt("v", new RelationalExp(">", new ArithExp( new HeapReadExp(new VarExp("b")),
                            new ValueExpression(new IntValue(2)),'-'),
                            new HeapReadExp(new VarExp("a"))),
                            new ValueExpression(new IntValue(100)),
                            new ValueExpression(new IntValue(200))),
                    new PrintStmt(new VarExp("v")));
            ex2.typecheck(new Dict<String,IType>());
            PrgState p2=new PrgState(new ADTStack<>(),new Dict<>(),new ADTList<>(),ex2,filetable2,new Heap(),new myLatchTable());
            Repo repo2=new Repo(p2,"log2.txt");
            Controller c2=new Controller(repo2);
            program.add(c2);
        }catch (MyException|DictionaryException|TypeException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("TypeCheck error");
            alert.setContentText("Example 14"+e.getMessage());

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
