package propertiesAndBindings;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class PerformanceChangeListener extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DoubleProperty number1 = new SimpleDoubleProperty(1);
        DoubleProperty number2 = new SimpleDoubleProperty(2);
        DoubleProperty number3 = new SimpleDoubleProperty(3);
        NumberBinding calculated = Bindings.add(number1,
                Bindings.multiply(number2, number3));
        Slider slider = new Slider(0, 100, 1);
        number1.bind(slider.valueProperty());

        ChangeListener<Number> changelistener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    // ignorieren wir hier ausnahmsweise
                }
                System.out.println("Wert geändert von " + oldValue + " auf " + newValue);
            }
        };

        InvalidationListener invalidationListener = new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    // ignorieren wir hier ausnahmsweise
                }
                //System.out.println("calculated: " + calculated);
                System.out.println("calculated: " + calculated.getValue());
            }
        };

//        calculated.addListener(changelistener);
        calculated.addListener(invalidationListener);

        Scene scene = new Scene(slider, 400, 100);
        primaryStage.setTitle("ChangeListener");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
