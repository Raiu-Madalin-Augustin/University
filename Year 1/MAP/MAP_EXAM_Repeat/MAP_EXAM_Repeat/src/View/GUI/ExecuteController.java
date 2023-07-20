package View.GUI;

import Controller.Controller;
import Exceptions.MyException;
import Model.PrgState;
import Model.Statements.IStmt;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;

public class ExecuteController {
    private final Controller controller;

    public ExecuteController(Controller controller_) {
        controller = controller_;
    }

    @FXML
    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    private ListView<Integer> idsListView;

    @FXML
    private final ObservableList<Integer> observableList = FXCollections.observableArrayList();

    @FXML
    private TextArea exeStackTextArea;

    @FXML
    private TextArea symTableTextArea;

    @FXML
    private TextArea fileTableTextArea;

    @FXML
    private TextArea heapTextArea;

    @FXML
    private TextArea outTextArea;

    @FXML
    private Button oneStepButton;

    @FXML
    private Button changeIDButton;

    @FXML
    public void initialize() {
        try {
            observableList.setAll(controller.getIDs());
            idsListView.setItems(observableList);
            controller.startOneStep();
            exeStackTextArea.setText(controller.getExeStack(0));
        }
        catch (MyException me) {
            controller.endOneStep();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: Execution Stack Empty!");
            alert.show();
        }
    }

    @FXML
    void onChangeIDButtonAction(ActionEvent event) {
        int selectedID = idsListView.getSelectionModel().getSelectedIndex();
        if(selectedID == -1) {
            selectedID = 0;
        }
        reset(selectedID);
    }

    @FXML
    void onOneStepButtonAction(ActionEvent event) {
        try {
            controller.oneStep();
            if (!idsListView.getItems().isEmpty()) {
                idsListView.getSelectionModel().select(0);
            }
            reset(idsListView.getSelectionModel().getSelectedIndex());
        } catch (MyException | IOException | InterruptedException e) {
            controller.endOneStep();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: Execution Stack Empty!");
            alert.show();
        }
    }

    public void reset(int id) {
        try {
            observableList.setAll(controller.getIDs());
            idsListView.setItems(observableList);
            outTextArea.setText(controller.getOut(id));
            symTableTextArea.setText(controller.getSymTable(id));
            fileTableTextArea.setText(controller.getFileTable(id));
            heapTextArea.setText(controller.getHeap(id));
            exeStackTextArea.setText(controller.getExeStack(id));
        }
        catch (MyException me) {
            controller.endOneStep();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: Execution Stack Empty!");
            alert.show();
        }

    }

}
