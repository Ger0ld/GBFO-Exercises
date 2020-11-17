package propertiesAndBindings;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LazyEvaluation {

    public static void main(String[] args) {

        //Zugriffe
        final StringProperty textProp = new SimpleStringProperty("WERT1");
        System.out.println("textProp:" + textProp);
        System.out.println("getValue():" + textProp.getValue());

        //Bindings
        final BooleanBinding binding = textProp.isEqualToIgnoreCase("wert1");
        System.out.println("binding:" + binding);
        System.out.println("getValue():" + binding.getValue());
        System.out.println("binding:" + binding);

        //Berechnungen
        final IntegerProperty intProp1 = new SimpleIntegerProperty(10);
        final IntegerProperty intProp2 = new SimpleIntegerProperty(2);
        System.out.println("intProp:" + intProp1);
        System.out.println("subtract():" + intProp1.add(40).subtract(intProp2));
        System.out.println("multiply():" + intProp1.multiply(intProp2.getValue()));
    }
}
