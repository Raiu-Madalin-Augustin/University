package View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("select.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("MAP_A7");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
