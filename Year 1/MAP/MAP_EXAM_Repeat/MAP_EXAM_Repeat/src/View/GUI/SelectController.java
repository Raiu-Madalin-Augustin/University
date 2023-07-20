package View.GUI;

import Controller.Controller;
import Exceptions.MyException;
import Model.ADTs.*;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.FileRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SelectController {

    private final ArrayList<Controller> controllers = new ArrayList<>();

    @FXML
    private ListView<IStmt> listViewExamples;

    @FXML
    private Button executeButton;

    @FXML
    void onExecuteButtonAction(ActionEvent event) throws IOException, MyException {
        int selectedID = listViewExamples.getSelectionModel().getSelectedIndex();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("execute.fxml"));
        fxmlLoader.setController(new ExecuteController(controllers.get(selectedID)));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("Execution");
        stage.setScene(new Scene(root, 850, 750));
        stage.showAndWait();
    }

    @FXML
    private final ObservableList<IStmt> observableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws MyException {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                                new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))))))))));

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocateStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocateStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new HeapReadExp(new HeapReadExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5)))))))));

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocateStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v"))),
                                new CompStmt(new HeapWriteStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new HeapReadExp(new VarExp("v")),
                                                new ValueExp(new IntValue(5))))))));

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocateStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
                                        new CompStmt(new HeapAllocateStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new HeapReadExp(new HeapReadExp(new VarExp("a")))))))));

        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",
                                        new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new HeapAllocateStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new HeapReadExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new HeapReadExp(new VarExp("a")))))))));
        IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("x", new IntType()),
                        new CompStmt(new VarDeclStmt("y", new IntType()),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),
                                        new CompStmt(new RepeatUntilStmt(new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1))))
                                        )), new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1)))
                                        )), new RelationalExp("==", new VarExp("v"), new ValueExp(new IntValue(3)))),
        new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(1))),
                new CompStmt(new NopStmt(), new CompStmt(new AssignStmt("y", new ValueExp(new IntValue(3))),
                        new CompStmt(new NopStmt(), new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))))
        ))))));

        observableList.add(ex1);
        observableList.add(ex2);
        observableList.add(ex3);
        observableList.add(ex4);
        observableList.add(ex5);
        observableList.add(ex6);
        observableList.add(ex7);
        observableList.add(ex8);
        observableList.add(ex9);
        observableList.add(ex10);
        observableList.add(ex11);

        ex1.typeCheck(new Dict<>());
        PrgState program1 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex1, new FileTable(), new Heap());
        FileRepository repo1 = new FileRepository(program1, "log1.txt");
        Controller controller1 = new Controller(repo1);

        ex2.typeCheck(new Dict<>());
        PrgState program2 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex2, new FileTable(), new Heap());
        FileRepository repo2 = new FileRepository(program2, "log2.txt");
        Controller controller2 = new Controller(repo2);

        ex3.typeCheck(new Dict<>());
        PrgState program3 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex3, new FileTable(), new Heap());
        FileRepository repo3 = new FileRepository(program3, "log3.txt");
        Controller controller3 = new Controller(repo3);

        ex4.typeCheck(new Dict<>());
        PrgState program4 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex4, new FileTable(), new Heap());
        FileRepository repo4 = new FileRepository(program4, "log4.txt");
        Controller controller4 = new Controller(repo4);

        ex5.typeCheck(new Dict<>());
        PrgState program5 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex5, new FileTable(), new Heap());
        FileRepository repo5 = new FileRepository(program5, "log5.txt");
        Controller controller5 = new Controller(repo5);

        ex6.typeCheck(new Dict<>());
        PrgState program6 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex6, new FileTable(), new Heap());
        FileRepository repo6 = new FileRepository(program6, "log6.txt");
        Controller controller6 = new Controller(repo6);

        ex7.typeCheck(new Dict<>());
        PrgState program7 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex7, new FileTable(), new Heap());
        FileRepository repo7 = new FileRepository(program7, "log7.txt");
        Controller controller7 = new Controller(repo7);

        ex8.typeCheck(new Dict<>());
        PrgState program8 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex8, new FileTable(), new Heap());
        FileRepository repo8 = new FileRepository(program8, "log8.txt");
        Controller controller8 = new Controller(repo8);

        ex9.typeCheck(new Dict<>());
        PrgState program9 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex9, new FileTable(), new Heap());
        FileRepository repo9 = new FileRepository(program9, "log9.txt");
        Controller controller9 = new Controller(repo9);

        ex10.typeCheck(new Dict<>());
        PrgState program10 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex10, new FileTable(), new Heap());
        FileRepository repo10 = new FileRepository(program10, "log10.txt");
        Controller controller10 = new Controller(repo10);

        ex11.typeCheck(new Dict<>());
        PrgState program11 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex11, new FileTable(), new Heap());
        FileRepository repo11 = new FileRepository(program11, "log11.txt");
        Controller controller11 = new Controller(repo11);

        controllers.add(controller1);
        controllers.add(controller2);
        controllers.add(controller3);
        controllers.add(controller4);
        controllers.add(controller5);
        controllers.add(controller6);
        controllers.add(controller7);
        controllers.add(controller8);
        controllers.add(controller9);
        controllers.add(controller10);
        controllers.add(controller11);

        listViewExamples.setItems(observableList);

    }
}
