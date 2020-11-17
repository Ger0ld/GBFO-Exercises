package layoutcontainer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class Controller {

    private final String[] fileSizes = {"kB", "mB", "gB", "tB"};
    @FXML
    private TextField widthTF, heightTF;
    @FXML
    private Label sizeLabel;
    @FXML
    private ComboBox<DPI_SIZE> dpiCB;

    @FXML
    private void initialize() {
        dpiCB.setItems(FXCollections.observableArrayList(DPI_SIZE.values()));
        dpiCB.getSelectionModel().selectFirst();
    }

    @FXML
    private void calculateSize() {
        try {
            long width = Long.parseLong(widthTF.getText()), height = Long.parseLong(heightTF.getText());
            int size = dpiCB.getSelectionModel().getSelectedItem().size;
            long result = Math.multiplyExact(Math.multiplyExact(width, height), size);//width * height * size
            sizeLabel.setText(convert(result));
        } catch (NumberFormatException e) {
            sizeLabel.setText("Fehler in der Eingabe!");
        } catch (ArithmeticException e) {
            sizeLabel.setText("Das Ergebnis ist zu groß!");
        }
    }

    private String convert(long size) {
        if (size < 8) {
            return size + " Bit";
        } else if (size < 8000) {
            return size / 8.0 + " Byte";
        }
        double result = size / 8000.0;
        int index = 0;
        while (index < fileSizes.length - 1 && result >= 1000) {
            result /= 1000.0;
            index++;
        }
        DecimalFormatSymbols format = new DecimalFormatSymbols();
        format.setDecimalSeparator('.');
        return new DecimalFormat("#.###", format).format(result) + " " + fileSizes[index];
    }

    private enum DPI_SIZE {
        ONE(1), EIGHT(8), TWENTY_FOUR(24);
        private final int size;

        DPI_SIZE(int size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return size + " Bit";
        }
    }
}
