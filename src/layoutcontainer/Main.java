package layoutcontainer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Filesize Calculator");

        Pane root = FXMLLoader.load(getClass().getResource("layoutcontainer.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
