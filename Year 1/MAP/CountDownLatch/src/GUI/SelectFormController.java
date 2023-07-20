package GUI;
import Controller.Controller;
import Model.PrgState;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;
import View.Programs;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.OptionalInt;
import java.util.stream.IntStream;
public class SelectFormController implements Initializable {
    public SelectFormController() {
    }

    private Controller controller;

    private PrgState selectedProgram;

    @FXML
    private TableView<HashMap.Entry<Integer, String>> heapTableView = new TableView<>();
    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, Integer> heapAddressColumn = new TableColumn<>();
    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, String> heapValueColumn = new TableColumn<>();

    @FXML
    private ListView<String> outputListView = new ListView<>();

    @FXML
    private ListView<String> fileListView = new ListView<>();

    @FXML
    private ListView<Integer> programStatesView = new ListView<>();

    @FXML
    private TableView<Map.Entry<String, String>> symTableView = new TableView<>();
    @FXML
    private TableColumn<Map.Entry<String, String>, String> symVarNameColumn = new TableColumn<>();
    @FXML
    private TableColumn<Map.Entry<String, String>, String> symValueColumn = new TableColumn<>();

    @FXML
    private ListView<String> exeStackView = new ListView<>();

    @FXML
    private TextField nrProgramStatesField = new TextField("");


    @FXML
    private TableView<Map.Entry<Integer, Integer>> latchTableView=new TableView<>();

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> valueLatchTableColumn=new TableColumn<>();

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> locationLatchTableColumn=new TableColumn<>();


    public void setController(Controller controller) {
        this.controller = controller;
        selectedProgram = controller.getRepository().getPrgStateList().get(0);

        loadData();

    }

    @FXML
    public void setSelectedProgram(){
        if(programStatesView.getSelectionModel().getSelectedIndex()>=0&& programStatesView.getSelectionModel().getSelectedIndex()<=this.controller.getRepository().getPrgStateList().size()){
                selectedProgram=controller.getRepository().getPrgStateList().get(programStatesView.getSelectionModel().getSelectedIndex());
                loadData();
     }
    }

    private void loadData(){
        this.programStatesView.getItems().setAll(controller.getRepository().getPrgStateList().stream().map(PrgState::getCurrentId).distinct().collect(Collectors.toList()));

        if(selectedProgram!=null)
        {
            outputListView.getItems().setAll(selectedProgram.getOutput().getList().stream().map(Object::toString).collect(Collectors.toList()));

            fileListView.getItems().setAll(String.valueOf(selectedProgram.getFileTable().getDict().keySet()));

            List<String> executionStackList=selectedProgram.getExeStack().getStack().stream().map(IStmt::toString).collect(Collectors.toList());
            Collections.reverse(executionStackList);
            exeStackView.getItems().setAll(executionStackList);

            Heap heapTable=selectedProgram.getHeap();
            List<Map.Entry<Integer,String>> heapTableList=new ArrayList<>();

            for(Map.Entry<Integer,IValue> element:heapTable.getDict().entrySet()){
                Map.Entry<Integer,String>el=new AbstractMap.SimpleEntry<Integer,String>(element.getKey(),element.getValue().toString());
                heapTableList.add(el);
            }
            heapTableView.setItems(FXCollections.observableList(heapTableList));
            heapTableView.refresh();

            heapAddressColumn.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getKey()).asObject());
            heapValueColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));

            IDict<String, IValue> symbolTable=this.selectedProgram.getSymTable();
            List<Map.Entry<String, String>> symbolTableList=new ArrayList<>();
            for(Map.Entry<String, IValue> element:symbolTable.getDict().entrySet()){
                Map.Entry<String, String> el=new AbstractMap.SimpleEntry<String, String>(element.getKey(),element.getValue().toString());
                symbolTableList.add(el);
            }
            symTableView.setItems(FXCollections.observableList(symbolTableList));
            symTableView.refresh();

            symVarNameColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()));
            symValueColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));

            long a=this.controller.getRepository().getPrgStateList().stream().map(PrgState::getCurrentId).distinct().count();

            nrProgramStatesField.setText(String.valueOf(a));

            ILatchTable latchTable = selectedProgram.getLatchTable();
            List<Map.Entry<Integer, Integer>> latchList = new ArrayList<>();
            for(Map.Entry<Integer, Integer> entry : latchTable.getLatchTable().entrySet())
                latchList.add(entry);

            latchTableView.setItems(FXCollections.observableList(latchList));
            latchTableView.refresh();

            locationLatchTableColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
            valueLatchTableColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());


        }
    }

    @FXML
    public void onRunOneStepButtonPressed(){
        if(controller==null)
        {
             Alert alert=new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("program was not selected");
             alert.setContentText("please select a program to execute");
             alert.showAndWait();
             return;
        }

        if(selectedProgram.getExeStack().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Program is done!");
            alert.setContentText("Please select a new program to execute");
            alert.showAndWait();
            return;
        }
        controller.executeOneStep();
        loadData();;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){}
}




