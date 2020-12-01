package diagram;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class DiagramController {

    @FXML
    private PieChart pieChart;

    private final ObservableList<PieChart.Data> data = FXCollections.observableArrayList(new PieChart.Data("Jan", 10), new PieChart.Data("Feb", 20), new PieChart.Data("Mar", 50));

    @FXML
    private void initialize() {
        pieChart.setData(data);
    }

}
