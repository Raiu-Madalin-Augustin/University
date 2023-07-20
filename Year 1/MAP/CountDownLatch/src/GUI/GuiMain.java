package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class GuiMain extends Application {

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage)throws Exception{
        FXMLLoader mainLoader = new FXMLLoader();

        mainLoader.setLocation(getClass().getResource("runForm.fxml"));

        Parent mainWindow = mainLoader.load();

        runFormController controller = mainLoader.getController();
        System.out.println("here");
        controller.setListView();

        primaryStage.setTitle("Select Program");
        primaryStage.setScene(new Scene(mainWindow));
        primaryStage.show();


    }
}
