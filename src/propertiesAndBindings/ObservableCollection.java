package propertiesAndBindings;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.function.Predicate;


public class ObservableCollection extends Application {

    private final ObservableList<String> names = FXCollections.observableArrayList();
    @FXML
    private ListView<String> nameLV;
    @FXML
    private TextField nameTF;

    @FXML
    private void initialize() {
        FilteredList<String> filteredNames = new FilteredList<>(names);
        nameTF.textProperty().addListener((observable, oldValue, newValue) -> {
            final Predicate<String> caseInsensitiveContains = entry -> entry.toUpperCase().contains(newValue.toUpperCase());
            filteredNames.setPredicate(caseInsensitiveContains);
        });
        nameLV.setItems(filteredNames);

        //double click to select name
        nameLV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() >= 2) {
                String selection = nameLV.getSelectionModel().getSelectedItem();
                if (selection != null)
                    nameTF.setText(selection);
            }
        });
    }

    @FXML
    private void removeName() {
        names.remove(nameTF.getText());
        nameTF.clear();
    }

    @FXML
    private void addName() {
        names.add(nameTF.getText());
        nameTF.clear();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ListViewExample");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("ObservableCollection.fxml"))));
        primaryStage.show();
    }
}
